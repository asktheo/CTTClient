package gis;

import core.BaseDAO;
import core.util.DatabaseEndpoint;
import ctt.model.Unit;
import gis.model.GPSObservationModel;
import lombok.extern.slf4j.Slf4j;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Slf4j
/**
 * An instance of the class is responsible for
 * reading from and writing to the GIS database holding gps observations
 * 
 * @author theo 2017-06-25
 *
 *
 */
public class GPSObservationDAO extends BaseDAO {
	private static Sql2o sql2o;
	private static GPSObservationDAO instance;

	static {
		DataSource datasource;
		try {
			datasource = DatabaseEndpoint.getDestinationInstance().getDataSource();
			sql2o = new Sql2o(datasource);
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get Singleton instance
	 * 
	 * @return
	 */
	protected static GPSObservationDAO getInstance() {
		if (instance == null) {
			instance = new GPSObservationDAO();
		}
		return instance;
	}

	/**
	 * deleteObservations
	 * TODO: implement
	 * @param unitId
	 *            : the source from which to delete from.
	 */
	public void deleteObservations(String unitId) {

	}

	/**
	 * 
	 * @param observations :
	 *                     to be stored in the database
	 * @return <code>0</code> if success
	 */
	public int insertObservations(List<GPSObservationModel> observations) {
		log.debug("Storing {} new observations", observations.size());
		String insertSQL = " INSERT INTO " + GISModel.GPS_OBSERVATION_TABLE
				+ "		(unitid, euringno, geom, dt, gps_date, gps_time,lat,lon,hdop,fix,cog,speed,alt,data_voltage,solar_charge)"
				+ " VALUES(:unitid,:euringno,st_geometryfromtext(:wkt,3857),:dt,:gps_date, :gps_time,:lat,:lon,:hdop,:fix,:cog,:speed,:alt,:data_voltage,:solar_charge)";

		try (Connection con = sql2o.beginTransaction()) {
			Query query = con.createQuery(insertSQL);
			for (GPSObservationModel o : observations) {
				query.addParameter("unitid", o.getUnitid())
						.addParameter("euringno", o.getEuringno())
						.addParameter("wkt", o.getWKT())
						.addParameter("dt", o.getGps_date() + "T" + o.getGps_time())
						.addParameter("gps_date", o.getGps_date())
						.addParameter("gps_time", o.getGps_time())
						.addParameter("lat", o.getLatitude())
						.addParameter("lon", o.getLongitude())
						.addParameter("hdop", o.getHdop())
						.addParameter("fix", o.getFix())
						.addParameter("cog", o.getCog())
						.addParameter("speed", o.getSpeed())
						.addParameter("alt", o.getAlt())
						.addParameter("data_voltage", o.getData_voltage())
						.addParameter("solar_charge", o.getSolar_charge())
						.addToBatch();
			}
			query.executeBatch();
			con.commit();
		} catch (Sql2oException sqlEx) {
			log.error("error during insert:{}",sqlEx.getMessage());
			sqlEx.printStackTrace();
			return -1;
		}
		return 0;
	}

	/**
	 * @return list of those {@link ctt.model.Unit}s and data of last gps observation if existing in the database
	 * @throws Exception
	 */
	public List<Unit> getStoredUnits() throws Exception{
		log.debug("get stored units");
		try {
			Connection con = sql2o.beginTransaction();
			String sql = "SELECT unitid, max(dt) as maxdate FROM " + GISModel.GPS_OBSERVATION_TABLE + " GROUP BY unitid";
			Query query = con.createQuery(sql);
			return query
					.addColumnMapping("unitid","unitId")
					.addColumnMapping("maxdate","lastData")
					.executeAndFetch(Unit.class);
		} catch (Sql2oException sqlEx) {
			log.error("error getting data for stored units");
			throw sqlEx;
		}
	}

}
