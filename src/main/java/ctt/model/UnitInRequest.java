package ctt.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/** Created by theo on 25-06-2017.
 * A list of instances of this class is a member of {@link ctt.model.UnitCollectionInRequest }
 */
public class UnitInRequest {

    @SerializedName("endDt")
    @Expose
    private String endDt;
    @SerializedName("startDt")
    @Expose
    private String startDt;
    @SerializedName("unitId")
    @Expose
    private String unitId;

    /**
     * No args constructor for use in serialization
     *
     */
    private UnitInRequest() {
    }

    /**
     *
     * @param endDt :
     *             last observation (datetime) wanted from the unit
     * @param startDt :
     *                first observation (datetime) wanted from the unit
     * @param unitId :
     *               Identifier for the gps unit
     */
    public UnitInRequest(String endDt, String startDt, String unitId) {
        super();
        this.endDt = endDt;
        this.startDt = startDt;
        this.unitId = unitId;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

}
