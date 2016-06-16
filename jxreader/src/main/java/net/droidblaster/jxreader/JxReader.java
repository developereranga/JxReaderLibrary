package net.droidblaster.jxreader;

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
public class JxReader {
    final public int POST = 0;
    final public int GET = 1;
    final public int JSON_POST = 2;

    public JSONObject ReadJson(int method, String url, List<NameValuePair> params, JSONObject jsonObject) {
        JSONObject responsJson = null;
        switch (method) {
            case 0:
                try {
                    responsJson = new JSONObject(new JsonStringBuilder().Build(new Requester().requestPost(url, params)).toString());
                } catch (JSONException e) {
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
        public StringBuilder Build(InputStream inputStream) {
            StringBuilder stringBuilder = new StringBuilder();
            String row = null;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                while ((row = bufferedReader.readLine()) != null) {
                    stringBuilder.append(row + "\n");
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return stringBuilder;
            }
        }
    }

    private class Requester {

        private InputStream requestPost(String URL, List<NameValuePair> PARAMETERS) {
            InputStream inputStream = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(URL);
                httpPost.setEntity(new UrlEncodedFormEntity(PARAMETERS));
                HttpResponse response = httpClient.execute(httpPost);
                inputStream = response.getEntity().getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return inputStream;
            }
        }

        private InputStream requestGET(String URL, List<NameValuePair> PARAMETERS) {
            InputStream inputStream = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                if (PARAMETERS != null) {
                    String params = URLEncodedUtils.format(PARAMETERS, "utf-8");
                    URL += "?" + params;
                }
                HttpGet httpGet = new HttpGet(URL);
                HttpResponse response = httpClient.execute(httpGet);
                inputStream = response.getEntity().getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return inputStream;
            }


        }

        private String requestJsonPost(JSONObject jsonObject, String URL) {
            String readText = "";
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost request = new HttpPost(URL);
                StringEntity e = new StringEntity(jsonObject.toString(), "UTF-8");
                request.setEntity(e);
                request.setHeader("content-type", "application/json");
                HttpResponse response = httpClient.execute(request);
                readText = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                return new ErrorReporter().createError(e.getLocalizedMessage()).toString();
            } catch (Exception e) {
                return new ErrorReporter().createError(e.getLocalizedMessage()).toString();
            }
            return readText;
        }
    }



}
