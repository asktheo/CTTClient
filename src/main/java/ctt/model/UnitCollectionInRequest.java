package ctt.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by theo on 24-06-2017.
 * Json serializable class for holding a list of gps units for the parameters for a {@link ctt.model.CTTRequest} request
 */
public class UnitCollectionInRequest {

    @SerializedName("units")
    @Expose
    private List<UnitInRequest> units = null;

    public List<UnitInRequest> getUnits() {
        return units;
    }

    public void setUnits(List<UnitInRequest> units) {
        this.units = units;
    }

}
