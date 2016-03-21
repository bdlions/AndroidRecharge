package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static Button button_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        onClickButtonContinueListener();
    }

    public void onClickButtonContinueListener(){
        button_continue = (Button)findViewById(R.id.bContinue);
        button_continue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Intent intentContinue = new Intent("com.example.sampanit.recharge.Login");
                        //startActivity(intentContinue);
                        try {


                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);

                            HttpClient client = new DefaultHttpClient();
                            //HttpPost post = new HttpPost("http://50.18.235.96:3030/processqrcode");
                            HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/welcome/app_test");


                            List<NameValuePair> nameValuePairs = new ArrayList<>();

                            nameValuePairs.add(new BasicNameValuePair("imei","XYZ-123-345"));
                            nameValuePairs.add(new BasicNameValuePair("mac","MAC"));
                            nameValuePairs.add(new BasicNameValuePair("code", "sdfsdf"));



                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                            HttpResponse response = client.execute(post);

                            //Toast.makeText(this.getApplicationContext(),"Executed", Toast.LENGTH_SHORT).show();
// Get the response
                            BufferedReader rd = new BufferedReader
                                    (new InputStreamReader(response.getEntity().getContent()));

                            String result = "";
                            String line = "";
                            while ((line = rd.readLine()) != null) {
                                //textView.append(line);
                                result += line;
                            }
                            System.out.println(result);
                            //Toast.makeText(this.getApplicationContext(), line, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception ex){
                            //Toast.makeText(this.getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(ex.getMessage());
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
