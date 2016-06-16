package net.droidblaster.jxreader;

import org.json.JSONObject;

/**
 * Created by erangas on 6/16/16.
 */
public class ErrorReporter {

    public JSONObject createError(String error) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Error", error);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return jsonObject;
        }
    }
}