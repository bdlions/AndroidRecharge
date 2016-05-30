package com.example.sampanit.recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private static EditText etLoginUserName, etPassword;

    private static Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etLoginUserName = (EditText) findViewById(R.id.etLoginUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        onClickButtonLoginListener();

    }
    public void onClickButtonLoginListener(){
        buttonLogin = (Button)findViewById(R.id.bLogin);
        buttonLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            final ProgressDialog progress = new ProgressDialog(Login.this);
                            progress.setTitle("Login");
                            progress.setMessage("Authenticating user...");
                            progress.show();

                            final Thread loginThread = new Thread() {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        HttpClient client = new DefaultHttpClient();
                                        //HttpPost post = new HttpPost("http://50.18.235.96:3030/processqrcode");
                                        //HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/welcome/app_test");
                                        HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/androidapp/auth/login");

                                        List<NameValuePair> nameValuePairs = new ArrayList<>();

                                        nameValuePairs.add(new BasicNameValuePair("email", etLoginUserName.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("password", etPassword.getText().toString()));

                                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                                        HttpResponse response = client.execute(post);
                                        // Get the response
                                        BufferedReader rd = new BufferedReader
                                                (new InputStreamReader(response.getEntity().getContent()));
                                        String result = "";
                                        String line = "";
                                        while ((line = rd.readLine()) != null) {
                                            result += line;
                                        }
                                        if(result != null) {
                                            JSONObject resultEvent = new JSONObject(result.toString());
                                            int responseCode = (int)resultEvent.get("response_code");
                                            String message = (String) resultEvent.get("message");
                                            if(responseCode == 2000){
                                                JSONObject jsonResultEvent = (JSONObject) resultEvent.get("result_event");
                                                Intent intent = new Intent(getBaseContext(), RechargeMenu.class);
                                                intent.putExtra("USER_INFO", jsonResultEvent.get("user_info").toString());
                                                intent.putExtra("CURRENT_BALANCE", jsonResultEvent.get("current_balance").toString());
                                                startActivity(intent);
                                                progress.dismiss();
                                            }
                                            else
                                            {
                                                progress.dismiss();
                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Toast.makeText(getBaseContext(), "Authentication error.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                //Toast.makeText(null, message, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        else
                                        {
                                            progress.dismiss();
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            //Toast.makeText(null, "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    catch(Exception ex)
                                    {
                                        progress.dismiss();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            };
                            loginThread.start();
                        }
                        catch (Exception ex){
                            Toast.makeText(Login.this, "System error.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }
}
