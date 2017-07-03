package ctt;

import com.google.gson.Gson;
import core.util.DatabaseEndpoint;
import ctt.model.UnitCollectionInRequest;
import ctt.model.UnitInRequest;
import gis.GISModel;
import gis.GPSObservationService;
import gis.model.GPSObservationModel;
import gis.util.GISUtil;
import ctt.util.GsonUtil;
import ctt.model.Unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by theo on 23-06-2017.
 * Class holding the main method
 */
public class CTTAgent {

    private static String FIRST_DATE_TIME = "2017-06-23T10:00:00";

    /**
     * Main method - Succesful execution depends on a number of environment variables
     * @param args
     *            should be none. Instead set up environment variables
     *            <code>CTT_API_TOKEN</code> : API token from Cellular Tracking technologies
     *            <code>CTT_API_ENDPOINT</code> : api endpoint as stated by Cellular Tracking technologies
     *            <code>DEST_DB_URL</code> jdbc connection url for mysql or postgresql: jdbc:postgresql://localhost:5432
     *            <code>DEST_DB_USERNAME</code> should be schema or database owner
     *            <code>DEST_DB_PASSWORD</code>  password of the local database user
     *            <code>DEST_DB_NAME</code> database name
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //get environment variables as local member variables;
        String apiToken = System.getenv("CTT_API_TOKEN");
        String apiUrl = System.getenv("CTT_API_ENDPOINT");
        String destDbUrl=System.getenv("DEST_DB_URL");
        String destDbUsername=System.getenv("DEST_DB_USERNAME");
        String destDbPassword=System.getenv("DEST_DB_PASSWORD");
        String destDbName=System.getenv("DEST_DB_NAME");
        //setup database
        // create the destination database definitions
        DatabaseEndpoint.getDestinationInstance().setup(destDbUrl, destDbUsername, destDbPassword, destDbName);

        CTTRestClient client = CTTRestClient.getInstance();
        client.setApiEndpoint(apiUrl);
        client.setApiToken(apiToken);
        /*
          1.	request your unit list with last data / connection times
         */
        List<Unit> activeUnits = client.getActiveUnits();
        //build parameters for the export data request
        GPSObservationService service = new GPSObservationService();
        List<Unit> storedUnits = service.getStoredUnits();
        List<UnitInRequest> requestUnits = new ArrayList<UnitInRequest>();
        for (Unit u : activeUnits) {
            List<Unit> matches = storedUnits.stream()
                    .filter(o -> o.equals(u))
                    .collect(Collectors.toList());
            //create current time variable
            String currentDateTimeString = LocalDateTime.now().toString();
            if (!matches.isEmpty()) {
                Unit stored = matches.get(0);
                //transform to java.time objects to compare
                LocalDateTime storedDateTime = LocalDateTime.parse(stored.getLastData());
                String dateTimeWOOffset = u.getLastData().substring(0, u.getLastData().indexOf("+"));
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeWOOffset);

                /*
                 * 2.	compare those times with the dates stored on the client's database
                 */
                if (dateTime.isAfter(storedDateTime)) {
                    /*
                     * 3.	request data with the "data-export" action requesting data for units that have new data using the startDt as the last date from the client's database and an endDt of the current date/time                    String lastDate = stored.getLastData();
                     */
                    String lastDate = stored.getLastData();
                    requestUnits.add(new UnitInRequest(currentDateTimeString, lastDate, u.getUnitId()));
                }
            }
            else {
                requestUnits.add(new UnitInRequest(currentDateTimeString,FIRST_DATE_TIME, u.getUnitId()));
            }
        }
        UnitCollectionInRequest gpsUnitsParams = new UnitCollectionInRequest();
        if(!requestUnits.isEmpty()) {
            gpsUnitsParams.setUnits(requestUnits);
            String responseText = client.getDataExport(gpsUnitsParams);
            //CSV file handling
            String json = GsonUtil.CSVResponse2Json(responseText);
            Gson gson = new Gson();
            List<GPSObservationModel> newData = Arrays.asList(gson.fromJson(json, GPSObservationModel[].class));
            GISUtil gisUtil = GISUtil.getInstance();
            gisUtil.initTransformer(GISModel.SpatialRefSys.WGS84_Global_Positioning, GISModel.SpatialRefSys.WGS84_WebMercator);
            //check that data are within bounds
            newData = newData.stream().filter(gisUtil.withinBounds).collect(Collectors.toList());
            for (GPSObservationModel d : newData) {
                service.modifyObservation(d);
            }
            service.insertObservations(newData);
        }
    }



}