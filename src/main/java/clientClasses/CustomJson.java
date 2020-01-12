package clientClasses;

import org.json.JSONException;
import org.json.JSONObject;

/*CLASS DESCRIPTION: This simple class is used to standardize the communication between servlet and client. The client
* always sends data in this form: A JSONObject with two elements/keys. One key is "function" which holds the name
* (as a string) of the function that the servlet is supposed to execute. The other key "data" is a JSONObject or
* JSONArray itself holding the required data to execute the function. The data is in a predefined format. We documented
* the detailed format of all JSONs for all functions in the document reference.txt */

public class CustomJson extends JSONObject {
    public  CustomJson(String function, JSONObject data){
        super();

        try {
            this.put("function", function);
            this.put("data", data);
        }catch(JSONException e){
            System.out.println("Error during custom json creation");
            e.printStackTrace();
        }
    }
}
