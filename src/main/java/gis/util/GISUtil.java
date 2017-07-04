package gis.util;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTWriter;
import gis.GISModel.SpatialRefSys;
import gis.model.GPSObservationModel;

import java.util.function.Predicate;

public class GISUtil {
	


	private GeometryFactory geomFactory;
	private WKTWriter writer;
	private PrecisionModel prcsionModel;
	private CoordinateTransformer transformer;
	
	private static GISUtil instance;

	/**
	 * Constructor - initializes all by coordinate transformer which is dependent on Spatial Reference System (SRS)
	 */
	private GISUtil(){
		writer = new WKTWriter();
		geomFactory = new GeometryFactory();
		prcsionModel = new PrecisionModel(PrecisionModel.FIXED);
	}
	
	/**
	 * get Singleton instance
	 * @return
	 */
	public static GISUtil getInstance() {
		if (instance == null){
			instance = new GISUtil();
		}
		return instance;
	}	
	
	public GeometryFactory getGeomFactory() {
		return geomFactory;
	}

	public WKTWriter getWKTWriter() {
		return writer;
	}

	public PrecisionModel getPrecisionModel() {
		return prcsionModel;
	}

	/**
	 * create a {@link CoordinateTransformer} for the utility dependent on {@link SpatialRefSys}. Transformer is initialized in the constructór
	 * @param fromSRS
	 * @param toSRS
	 * @throws Exception
	 */
	public void initTransformer(SpatialRefSys fromSRS, SpatialRefSys toSRS) throws Exception{
		this.transformer = new CoordinateTransformer(fromSRS, toSRS);
	}	

	public CoordinateTransformer getTransformer() {
		return transformer;
	}

	public void setTransformer(CoordinateTransformer transformer) {
		this.transformer = transformer;
	}	
	
	public Predicate<GPSObservationModel> withinBounds = o -> (o.getLongitude() > 4.0 && o.getLongitude() < 17.0 && o.getLatitude() > 54.0 && o.getLatitude() < 58.0);
}
