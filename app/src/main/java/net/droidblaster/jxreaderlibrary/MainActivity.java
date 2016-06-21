package net.droidblaster.jxreaderlibrary;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.droidblaster.libjxr.JsonOut;
import net.droidblaster.libjxr.JxReader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView tResponse;
    Button bPost, bGet, bJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tResponse = (TextView) findViewById(R.id.txt_response);
        bGet = (Button) findViewById(R.id.btnGet);
        bPost = (Button) findViewById(R.id.btnPost);
        bJson = (Button) findViewById(R.id.btnJsonPost);
        ImageView img=(ImageView)findViewById(R.id.img);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        bJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject tempObj = new JSONObject();
                try {
                    tempObj.put("name", "Eranga");
                } catch (Exception e) {
                }

                JxReader jxReader = new JxReader(new JxReader().JSON_POST, "http://droidblaster.net/jxreader/jsonPost.php", tempObj, null);
                jxReader.setListener(new JxReader.JxReaderListener() {
                    @Override
                    public void onHttpRequesting() {
                        progressDialog.show();
                    }

                    @Override
                    public void onResponseRecived(JsonOut result) {
                        tResponse.setText(result.getResponse().toString());
                        progressDialog.dismiss();
                    }
                });
                jxReader.execute();
            }
        });
        bGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", "Eranga"));
                JxReader jxReader = new JxReader(new JxReader().GET, "http://droidblaster.net/jxreader/get.php", null, params);
                jxReader.setListener(new JxReader.JxReaderListener() {
                    @Override
                    public void onHttpRequesting() {
                        progressDialog.show();
                    }

                    @Override
                    public void onResponseRecived(JsonOut result) {
                        tResponse.setText(result.getResponse().toString());
                        progressDialog.dismiss();
                    }
                });
                jxReader.execute();

            }
        });
        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", "Eranga"));
                JxReader jxReader = new JxReader(new JxReader().POST, "http://droidblaster.net/jxreader/post.php", null, params);
                jxReader.setListener(new JxReader.JxReaderListener() {
                    @Override
                    public void onHttpRequesting() {
                        progressDialog.show();
                    }

                    @Override
                    public void onResponseRecived(JsonOut result) {

                        tResponse.setText(result.getResponse().toString());
                        progressDialog.dismiss();
                    }
                });
                jxReader.execute();
            }
        });


    }




}
