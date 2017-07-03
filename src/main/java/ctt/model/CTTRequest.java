package ctt.model;
/**
 * Created by theo on 25-06-2017.
 * Json serializable class for creating a request body
 */
public class CTTRequest {


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

    private String token;

    public void setAction(String action) {
        this.action = action;
    }

    private String action;

    public void setParameters(UnitCollectionInRequest parameters) {
        this.parameters = parameters;
    }

    private UnitCollectionInRequest parameters;

}
