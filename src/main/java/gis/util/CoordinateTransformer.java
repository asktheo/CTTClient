package gis.util;

import gis.GISModel.SpatialRefSys;
import lombok.extern.slf4j.Slf4j;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

@Slf4j
public class CoordinateTransformer {
	private CoordinateReferenceSystem sourceCRS;
	private CoordinateReferenceSystem destCRS;
	private MathTransform transformer;

	/**
	 *
	 * @param srcSys
	 * @param dstSys
	 * @throws Exception
	 */
	public CoordinateTransformer(SpatialRefSys srcSys, SpatialRefSys dstSys) throws Exception{
			this.sourceCRS = this.decode(srcSys.toString());
			this.destCRS = this.decode(dstSys.toString());
			this.transformer = this.getTransform();
	}


	/** 
	 * convert
	 * wraps the built in transform which works in a more complex manner
	 * @param srcOrds
	 * @return dstOrds
	 * @throws TransformException
	 */
	public double[] convert(double[] srcOrds) throws TransformException {
		//odd implementation of transformer because no return variable and slave parameter for modification ..
		double[] dstOrds = {0.0,0.0};
		//preserve simple array since it might be modifed in subsequent transformation
		transformer.transform(srcOrds.clone(), 0, dstOrds, 0, 1);
		return dstOrds;
	}

	/**
	 * creates CRS by decoding epsg string. Called in constructor
	 * @param epsgCode
	 * @return
	 * @throws Exception
	 */
	private CoordinateReferenceSystem decode(String epsgCode) throws Exception{
		try{ 
			return CRS.decode(epsgCode);
		} catch (NoSuchAuthorityCodeException ex) {
			log.error("No such authority", ex.getMessage());
			throw ex;
		} catch (FactoryException ex) {
			log.error("Error creating CoordinateReferenceSystem", ex.getMessage());
			throw ex;
		}
	}

	/**
	 * called in constructor to create this.transformer
	 * @return MathTransform
	 * @throws Exception
	 */
	private MathTransform getTransform() throws Exception{

		return CRS.findMathTransform(sourceCRS, destCRS);

	}

}
