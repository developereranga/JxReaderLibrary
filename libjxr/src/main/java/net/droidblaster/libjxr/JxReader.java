package net.droidblaster.libjxr;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by erangas on 6/17/16.
 */
public class JxReader extends AsyncTask<Void, Void, JsonOut> {
    final public int POST = 0;
    final public int GET = 1;
    final public int JSON_POST = 2;
    int method;
    String url;
    JSONObject jsonObject;
    List<NameValuePair> params;

    public JxReader() {
    }

    public JxReader(int method, String url, JSONObject jsonObject, List<NameValuePair> params) {
        this.method = method;
        this.url = url;
        this.jsonObject = jsonObject;
        this.params = params;
    }

    public interface JxReaderListener {
        void onHttpRequesting();

        void onResponseRecived(JsonOut result);
    }

    private JxReaderListener jxrListener;


    final public void setListener(JxReaderListener listener) {
        jxrListener = listener;
    }

    @Override
    final protected JsonOut doInBackground(Void... progress) {
        JsonOut response = new JsonOut();

        try {
            response.setResponse(new Engine().ReadJson(this.method, this.url, this.params, this.jsonObject));
            response.setError(false);
            return response;
        } catch (Exception e) {
            response.setResponse(new ErrorReporter().createError(e.getLocalizedMessage()));
            response.setError(true);
            return response;
        }
    }

    final protected void onPreExecute() {

        if (jxrListener != null)
            jxrListener.onHttpRequesting();
    }

    @Override
    final protected void onPostExecute(JsonOut result) {

        if (jxrListener != null)
            jxrListener.onResponseRecived(result);
    }
}
