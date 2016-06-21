package net.droidblaster.libjxr;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by erangas on 6/16/16.
 */
public class Engine {


    protected JSONObject ReadJson(int method, String url, List<NameValuePair> params, JSONObject jsonObject) {
        JSONObject responsJson = null;
        switch (method) {
            case 0:
                try {
                    responsJson = new JSONObject(new JsonStringBuilder().Build(new Requester().requestPost(url, params)).toString());
                } catch (Exception e) {
                    responsJson = new ErrorReporter().createError(e.getLocalizedMessage());
                }
                break;
            case 1:
                try {
                    responsJson = new JSONObject(new JsonStringBuilder().Build(new Requester().requestGET(url, params)).toString());
                } catch (Exception e) {
                    responsJson = new ErrorReporter().createError(e.getLocalizedMessage());
                }
                break;
            case 2:
                try {
                    responsJson = new JSONObject(new Requester().requestJsonPost(jsonObject, url));
                } catch (Exception e) {
                    responsJson = new ErrorReporter().createError(e.getLocalizedMessage());
                }
                break;
            default:
                responsJson = new ErrorReporter().createError("Required method is not available");
                break;

        }
        return responsJson;
    }

    private class JsonStringBuilder {
        public StringBuilder Build(InputStream inputStream)throws Exception{
            StringBuilder stringBuilder = new StringBuilder();
            String row = null;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                while ((row = bufferedReader.readLine()) != null) {
                    stringBuilder.append(row + "\n");
                }
                inputStream.close();

                return stringBuilder;

        }
    }

    private class Requester {

        private InputStream requestPost(String URL, List<NameValuePair> PARAMETERS) throws Exception {
            InputStream inputStream = null;

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                httpPost.setEntity(new UrlEncodedFormEntity(PARAMETERS));
                HttpResponse response = httpClient.execute(httpPost);
                inputStream = response.getEntity().getContent();

                return inputStream;

        }

        private InputStream requestGET(String URL, List<NameValuePair> PARAMETERS) throws  Exception {
            InputStream inputStream = null;


                DefaultHttpClient httpClient = new DefaultHttpClient();
                if (PARAMETERS != null) {
                    String params = URLEncodedUtils.format(PARAMETERS, "utf-8");
                    URL += "?" + params;
                }
                HttpGet httpGet = new HttpGet(URL);
                HttpResponse response = httpClient.execute(httpGet);
                inputStream = response.getEntity().getContent();

                return inputStream;



        }

        private String requestJsonPost(JSONObject jsonObject, String URL) throws  Exception {
            String readText = "";

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost(URL);
                StringEntity e = new StringEntity(jsonObject.toString(), "UTF-8");
                request.setEntity(e);
                request.setHeader("content-type", "application/json");
                HttpResponse response = httpClient.execute(request);
                readText = EntityUtils.toString(response.getEntity());

            return readText;
        }
    }



}
