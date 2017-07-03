package gis.model;

import com.google.gson.annotations.SerializedName;
import core.model.BaseModel;

public class GPSObservationModel extends BaseModel {

	private int unitid;
	private String euringno;
	private String wkt;
	@SerializedName("gps_datetime")
	private String dt;
	//response in CSV forma
	private String serial; //89460800120041549716
	@SerializedName("GPS_date_YYYY-MM-DD")
	private String gps_date;

	@SerializedName("GPS_utc_HH:MM:SS")
	private String gps_time;


	private double lat; //55.770786
	private double lon; //12.442293,
	private double hdop; //1.8
	private int fix; //3
	private int cog; //49
	private double speed; //0.1
	private double alt; //41.4
	private double data_voltage; //4.07
	private double solar_charge; //6.05

	public String getEuringno() {
		return euringno;
	}

	public String getDt() {
		return dt;
	}

	public String getGps_date() { return gps_date; }

	public String getGps_time() { return gps_time; }

	public double getLongitude() {
		return lon;
	}

	public void setLongitude(double longitude) {
		this.lon = longitude;
	}

	public double getLatitude() {
		return lat;
	}

	public void setLatitude(double latitude) {
		this.lat = latitude;
	}

	public String getWKT() {
		return wkt;
	}

	public void setWKT(String shapeWkt) {
		this.wkt = shapeWkt;
	}

	public int getUnitid() {
		return unitid;
	}

	public void setUnitid(int unitid) {
		this.unitid = unitid;
	}

	public void setEuringno(String euringno) {
		this.euringno = euringno;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public double getHdop() {
		return hdop;
	}

	public void setHdop(double hdop) {
		this.hdop = hdop;
	}

	public int getFix() {
		return fix;
	}

	public void setFix(int fix) {
		this.fix = fix;
	}

	public int getCog() {
		return cog;
	}

	public void setCog(int cog) {
		this.cog = cog;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAlt() {
		return alt;
	}

	public void setAlt(double alt) {
		this.alt = alt;
	}

	public double getData_voltage() {
		return data_voltage;
	}

	public void setData_voltage(double data_voltage) {
		this.data_voltage = data_voltage;
	}

	public double getSolar_charge() {
		return solar_charge;
	}

	public void setSolar_charge(double solar_charge) {
		this.solar_charge = solar_charge;
	}

}


