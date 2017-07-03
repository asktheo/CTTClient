package gis;

import core.model.BaseModel;

/**
 * GISModel
 * @author theo
 * defines schemas and tables in the GIS database
 *
 */
public abstract class GISModel extends BaseModel {

	//gis tables
	
	public static final String EP_GIS = "partners";

	public static final String GPS_OBSERVATION_TABLE = EP_GIS + ".gps_observation";
	
	// enum definitions and helper classes
	public enum SpatialRefSys {
		WGS84_WebMercator ("EPSG:3857"),
		WGS84_Global_Positioning ("EPSG:4326"),
		UTM_Zone32_North ("EPSG:25832"),
		UTM_Zone33_North ("EPSG:25833"),
		ETRS89_LAEA_Europe ("EPSG:3035");
		
		private final String strVal;

		SpatialRefSys(String s) {
			this.strVal = s;
		}

		@Override
		public String toString() {
			return strVal;
		}
		//return the part without "EPSG:" as integer
		public Integer toIntValue(){
			return Integer.parseInt(strVal.substring(5));
		}
	}
	

	
}
