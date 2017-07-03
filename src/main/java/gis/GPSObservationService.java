package gis;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

import gis.model.GPSObservationModel;
import ctt.model.Unit;
import org.opengis.referencing.operation.TransformException;
import gis.util.GISUtil;

import java.util.List;

/**
 * 
 * @author theo The service instance is responsible for delegating data
 *         operations and controlling their Interacts with other services
 */
public class GPSObservationService {

	private static String GPS_EURINGNO = "02430";

	/**

	 * @author theo
	 *
	 */
	public List<Unit> getStoredUnits() throws Exception{
        GPSObservationDAO dao = GPSObservationDAO.getInstance();
		List<Unit> units = dao.getStoredUnits();

		return units;
	}

	/**
	 *
	 * @param observationForGIS
	 * @throws Exception
	 */
	public void addGeometries2Observation(GPSObservationModel observationForGIS)
			throws Exception {
		GISUtil util = GISUtil.getInstance();
		double[] srcOrdinates = { observationForGIS.getLatitude(), observationForGIS.getLongitude() };
		try {
			double[] dstOrdinates = util.getTransformer().convert(srcOrdinates);
			Point p = util.getGeomFactory().createPoint(new Coordinate(dstOrdinates[0], dstOrdinates[1]));
            observationForGIS.setWKT(util.getWKTWriter().write(p));

		} catch (TransformException ex) {
			throw ex;
		}
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
	 * do some modifications
	 * @param observation
	 * @throws Exception
	 */
	public void modifyObservation(GPSObservationModel observation) throws Exception {
		//create short unitId from 20 digit serial
		String serial = observation.getSerial();
		observation.setUnitid(Integer.parseInt(serial.substring(serial.length()-8)));
		/**
		 * TODO : make this lookup unit in table where unitid combined with euringno
		 */
		observation.setEuringno(GPS_EURINGNO);
		this.addGeometries2Observation(observation);
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
