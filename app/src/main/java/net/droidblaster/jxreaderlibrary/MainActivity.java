package net.droidblaster.jxreaderlibrary;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.droidblaster.libjxr.JsonOut;
import net.droidblaster.libjxr.JxReader;
import net.droidblaster.libjxr.extra.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView tResponse;
    Button bPost, bGet, bJson, bimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tResponse = (TextView) findViewById(R.id.txt_response);
        bGet = (Button) findViewById(R.id.btnGet);
        bPost = (Button) findViewById(R.id.btnPost);
        bJson = (Button) findViewById(R.id.btnJsonPost);
        bimg = (Button) findViewById(R.id.btnImage);
        final ImageView img = (ImageView) findViewById(R.id.img);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        final Drawable myIcon = getResources().getDrawable(R.mipmap.ic_launcher);
        final ImageLoader loader = new ImageLoader(this);
        bimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.LoadImage("https://dz2cdn2.dzone.com/storage/partner-logo/1844525-1837613-images.png", img, R.mipmap.ic_launcher);
            }
        });
        bJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject tempObj = new JSONObject();
                try {
                    tempObj.put("name", "Eranga");
                } catch (Exception e) {
                }


                jxReader.setListener(new JxReader.JxReaderListener() {
                    @Override
                    public void onHttpRequesting() {
                        progressDialog.show();
                    }

                    @Override
                    public void onResponseReceived(JsonOut result) {
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
                    public void onResponseReceived(JsonOut result) {
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
                    public void onResponseReceived(JsonOut result) {

                        tResponse.setText(result.getResponse().toString());
                        progressDialog.dismiss();
                    }
                });
                jxReader.execute();
            }
        });


    }


}
