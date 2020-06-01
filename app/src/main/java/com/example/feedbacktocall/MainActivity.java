package com.example.feedbacktocall;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CacheRequest;

import org.json.JSONArray;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    String BASE_URL ="https://jsonplaceholder.typicode.com/";

    Button btn_clickme,btn_save;
    EditText feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_clickme=findViewById(R.id.btn_clickme);

        btn_clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new  AlertDialog.Builder(MainActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.dialog_feedback,null);
                feed=mview.findViewById(R.id.et_feed);
                btn_save=mview.findViewById(R.id.btn_save);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if(!feed.getText().toString().isEmpty()){

                            insert_Data();

                        }
                        else {

                            Toast.makeText(MainActivity.this,"Please Fill feedBack ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mbuilder.setView(mview);
                AlertDialog dialog = mbuilder.create();
                dialog.show();
            }
        });


         //if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
           //    != PackageManager.PERMISSION_GRANTED){
           //ActivityCompat.requestPermissions(this,
             //     new String[]{Manifest.permission.READ_PHONE_STATE},1);

    }
//}


    public void insert_Data(){

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        AppConfig.insert api =adapter.create(AppConfig.insert.class);

        api.insertData(feed.getText().toString(),
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("Succes", "" + resp);

                            JSONObject jsonObject = new JSONObject(resp);
                            int success = jsonObject.getInt("Success");

                            if (success == 1) {
                                Toast.makeText(MainActivity.this, "Thankyou for feedback data save ", Toast.LENGTH_LONG).show();
                                ;

                            } else {
                                Toast.makeText(MainActivity.this, "Feedback not Inserted ", Toast.LENGTH_LONG).show();
                                ;

                            }

                        } catch (IOException e) {
                            Log.d("Exception", toString());

                        } catch (JSONException e) {
                            Log.d("jsonException", toString());

                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }

                }

        );

    }

}
