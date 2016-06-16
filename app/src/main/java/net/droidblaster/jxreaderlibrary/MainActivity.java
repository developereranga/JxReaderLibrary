package net.droidblaster.jxreaderlibrary;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import net.droidblaster.jxreader.JxReader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context;
    JxReader jxReader = new JxReader();

    TextView tResponse;
    Button bPost, bGet, bJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        tResponse = (TextView) findViewById(R.id.txt_response);
        bGet = (Button) findViewById(R.id.btnGet);
        bPost = (Button) findViewById(R.id.btnPost);
        bJson = (Button) findViewById(R.id.btnJsonPost);

        bJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject tempObj = new JSONObject();
                try {

                    tempObj.put("name", "Eranga");
                 } catch (Exception e) {

                }
                new MakeHttpRequest(jxReader.JSON_POST, "http://droidblaster.net/jxreader/jsonPost.php", tempObj, null).execute();
            }
        });
        bGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name","Eranga"));
                new MakeHttpRequest(jxReader.GET, "http://droidblaster.net/jxreader/get.php", null, params).execute();

            }
        });
        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> params=new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name","Eranga"));
                new MakeHttpRequest(jxReader.POST, "http://droidblaster.net/jxreader/post.php", null, params).execute();
            }
        });


    }

    private class MakeHttpRequest extends AsyncTask<Void, Void, JSONObject> {
        int method;
        String url;
        JSONObject jsonObject;
        List<NameValuePair> params;
        ProgressDialog pDialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.pDialog.setCancelable(false);
            this.pDialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            tResponse.setText(jsonObject.toString());
            this.pDialog.dismiss();

        }

        public MakeHttpRequest(int method, String url, JSONObject jsonObject, List<NameValuePair> params) {
            this.method = method;
            this.url = url;
            this.jsonObject = jsonObject;
            this.params = params;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject response = null;

            try {
                return jxReader.ReadJson(this.method, this.url, this.params, this.jsonObject);
            } catch (Exception e) {
                return new ErrorReporter().createError(e.getLocalizedMessage());
            }

        }
    }

}
