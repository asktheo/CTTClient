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
	CoordinateReferenceSystem sourceCRS;
	CoordinateReferenceSystem destCRS;
	MathTransform transformer;
	
	
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
		double[] ords = srcOrds;
		double[] dstOrds = {0.0,0.0};
		// TODO Auto-generated method stub
		transformer.transform(ords, 0, dstOrds, 0, 1);	
		return dstOrds;
	}
	
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
	
	private MathTransform getTransform() throws Exception{
		
		try {
			return CRS.findMathTransform(sourceCRS, destCRS);
		} catch (FactoryException ex) {
			throw ex;
		}
	}

}
