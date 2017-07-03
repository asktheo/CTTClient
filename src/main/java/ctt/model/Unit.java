package ctt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by theo on 24-06-2017.
 * GPS Unit in list in response of the {@link ctt.model.CTTRequest#action} <code>get-units</code>
 * GPS Unit in list from {@link gis.GPSObservationDAO#getStoredUnits()} call
 */
public class Unit {

    @SerializedName("lastData")
    @Expose
    private String lastData;
    @SerializedName("lastBattery")
    @Expose
    private Double lastBattery;
    @SerializedName("lastConnection")
    @Expose
    private String lastConnection;
    @SerializedName("unitId")
    @Expose
    private String unitId;

    public String getLastData() {
        return lastData;
    }

    public void setLastData(String lastData) {
        this.lastData = lastData;
    }

    public Double getLastBattery() {
        return lastBattery;
    }

    public void setLastBattery(Double lastBattery) {
        this.lastBattery = lastBattery;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    public boolean equals(Object u){
        if(u instanceof  Unit){
            if(((Unit) u).getUnitId().equals(this.getUnitId())){
                return true;
            }
        }
        return false;
    }

}