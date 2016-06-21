package net.droidblaster.libjxr;

import org.json.JSONObject;

/**
 * Created by erangas on 6/16/16.
 */
public class ErrorReporter {
    final static String ERROR = "Error";

    public JSONObject createError(String error) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Error", error);
        } catch (Exception e) {
            jsonObject.put("Error", e.getLocalizedMessage());
        } finally {
            return jsonObject;
        }
    }
}