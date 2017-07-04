package ctt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by theo on 25-06-2017.
 * Json serializable class for creating a request body
 */
public class CTTRequest {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("action")
    @Expose
    private String action;

    @SerializedName("parameters")
    @Expose
    private UnitCollectionInRequest parameters;

    public enum Actions {
        /*
        * Available API methods
        */
        GET_UNITS ("get-units"),
        DATA_EXPORT ("data-export");

        private final String strVal;

        Actions(String a){
            this.strVal = a;
        }

        @Override
        public String toString() {
            return strVal;
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setParameters(UnitCollectionInRequest parameters) {
        this.parameters = parameters;
    }

}
