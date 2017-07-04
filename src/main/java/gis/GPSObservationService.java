package gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

import gis.model.GPSObservationModel;
import ctt.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.opengis.referencing.operation.TransformException;
import gis.util.GISUtil;

import java.util.List;

@Slf4j
/**
 * 
 * @author theo The service instance is responsible for delegating data
 *         operations and controlling their Interacts with other services
 */
public class GPSObservationService {

	/**

	 * @author theo
	 *
	 */
	public List<Unit> getStoredUnits() throws Exception{
        GPSObservationDAO dao = GPSObservationDAO.getInstance();

		return dao.getStoredUnits();
	}

	/**
	 *
	 * @param observationForGIS
	 * @throws Exception
	 */
	public boolean addGeometries2Observation(GPSObservationModel observationForGIS)
			throws Exception {
		GISUtil util = GISUtil.getInstance();
		double[] srcOrdinates = { observationForGIS.getLatitude(), observationForGIS.getLongitude() };
		try {
			double[] dstOrdinates = util.getTransformer().convert(srcOrdinates);
			Point p = util.getGeomFactory().createPoint(new Coordinate(dstOrdinates[0], dstOrdinates[1]));
            observationForGIS.setWKT(util.getWKTWriter().write(p));
		} catch (TransformException ex) {
			log.error("Error adding geometry to observation with coordinates {} {}", srcOrdinates[0], srcOrdinates[1]);
			return false;
		}
		return true;
	}

	/**
	 * insertObservations
	 * 
	 * @param observations
	 *            inserts observation to the destination database holding GIS
	 *            data.
	 */
	public int insertObservations(List<GPSObservationModel> observations) {
        GPSObservationDAO dao = GPSObservationDAO.getInstance();
		return dao.insertObservations(observations);
	}

	/**
	 * do some modifications on the observation
	 * @param observation
	 * @throws Exception
	 */
	public void modifyObservation(GPSObservationModel observation) throws Exception {
		//create short unitId from 20 digit serial
		String serial = observation.getSerial();
		observation.setUnitid(Integer.parseInt(serial.substring(serial.length()-8)));
		//add geometry (WKT)
		boolean success = this.addGeometries2Observation(observation);
		if(!success) {
			log.error("error adding WKT for unit {} at {}", observation.getUnitid(), observation.getGps_time());
		}
		else {
			log.info("WKT added to unit {} at {}", observation.getUnitid(), observation.getGps_time());
		}
	}

	/**
	 * extra info from the CTT Agent variables to add to each observation
	 * @param observation
	 * @param euringNo
	 * @throws Exception
	 */
	public void setEuringNo(GPSObservationModel observation, String euringNo) throws Exception {
		observation.setEuringno(euringNo);
	}

	/**
	 * deleteObservations
	 * 
	 * @param unitid
	 *            : the GPS' unit id
	 */
	public void deleteObservations(String unitid) {
        GPSObservationDAO dao = GPSObservationDAO.getInstance();
		dao.deleteObservations(unitid);
	}

}
