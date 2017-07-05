package ctt;

import com.google.gson.Gson;
import ctt.model.*;
import ctt.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by theo on 23-06-2017.
 * The instance wraps and executes the http requests to the CTT API
 */
@Slf4j
public class CTTRestClient {

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    private String apiEndpoint;
    private String apiToken;

    private static CTTRestClient ourInstance = new CTTRestClient();

    public static CTTRestClient getInstance() {
        return ourInstance;
    }

    private CTTRestClient() {
    }

    public List<Unit> getActiveUnits(){
        CTTRequest cttRequest = new CTTRequest();
        cttRequest.setAction(CTTRequest.Actions.GET_UNITS.toString());
        cttRequest.setToken(apiToken);
        Gson gson = new Gson();
        String body = gson.toJson(cttRequest);
        String responseJson = GsonUtil.http(apiEndpoint,body);
        log.info("received from API: {}",responseJson);
        if(responseJson != null) {
            UnitCollectionInResponse responseUnits = gson.fromJson(responseJson, UnitCollectionInResponse.class);
            return responseUnits.getUnits();
        }
        return null;
    }

    public String getDataExport(UnitCollectionInRequest params){
        CTTRequest cttRequest = new CTTRequest();
        cttRequest.setToken(apiToken);
        cttRequest.setAction(CTTRequest.Actions.DATA_EXPORT.toString());
        cttRequest.setParameters(params);
        Gson gson = new Gson();
        String body = gson.toJson(cttRequest);
        String responseText = GsonUtil.http(apiEndpoint,body);
        return responseText;
    }
}
