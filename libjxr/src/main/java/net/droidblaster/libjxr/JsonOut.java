package net.droidblaster.libjxr;

import org.json.JSONObject;

/**
 * Created by ErangaS on 2016-06-21.
 */
public class JsonOut {
    boolean isError;
    JSONObject response;

    public boolean hasError() {
        return isError;
    }

    protected void setError(boolean error) {
        isError = error;
    }

    public JSONObject getResponse() {
        return response;
    }

    protected void setResponse(JSONObject response) {
        this.response = response;
    }
}
