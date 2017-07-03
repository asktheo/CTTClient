package ctt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by theo on 24-06-2017.
 * Json Serializable class for holding a list of gps units {@link ctt.model.Unit} from a Json Response
 */
public class UnitCollectionInResponse {

    @SerializedName("units")
    @Expose
    private List<Unit> units = null;

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

}
