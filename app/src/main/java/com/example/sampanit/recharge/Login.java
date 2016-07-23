package com.example.sampanit.recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private static EditText etOPCode, etLoginUserName, etPassword;
    private static String baseUrl = "";
    private static Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etOPCode = (EditText) findViewById(R.id.etOPCode);
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
                            final ProgressDialog progressInit = new ProgressDialog(Login.this);
                            progressInit.setTitle("Login");
                            progressInit.setMessage("Authenticating user...");
                            progressInit.show();

                            final Thread initThread = new Thread() {
                                @Override
                                public void run()
                                {
                                    try
                                    {
                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        HttpClient client = new DefaultHttpClient();
                                        HttpPost post = new HttpPost("http://199.33.127.59:4040/getbaseurl");

                                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                                        nameValuePairs.add(new BasicNameValuePair("code", etOPCode.getText().toString()));

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
                                            int responseCode = (int)resultEvent.get("responseCode");
                                            if(responseCode == 2000){
                                                baseUrl = (String) resultEvent.get("result");
                                                if( baseUrl == null || baseUrl.equals(""))
                                                {
                                                    progressInit.dismiss();
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            Toast.makeText(getBaseContext(), "Invalid Code.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                                //progressInit.dismiss();
                                                try
                                                {
                                                    //final ProgressDialog progressLogin = new ProgressDialog(Login.this);
                                                    //progressLogin.setTitle("Login");
                                                    //progressLogin.setMessage("Authenticating user...");
                                                    //progressLogin.show();

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
                                                                HttpPost post = new HttpPost(baseUrl+"androidapp/auth/login");

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
                                                                        intent.putExtra("BASE_URL", baseUrl);
                                                                        intent.putExtra("USER_INFO", jsonResultEvent.get("user_info").toString());
                                                                        intent.putExtra("CURRENT_BALANCE", jsonResultEvent.get("current_balance").toString());
                                                                        intent.putExtra("SESSION_ID", jsonResultEvent.get("session_id").toString());
                                                                        //getting service id list
                                                                        JSONArray serviceIdList = jsonResultEvent.getJSONArray("service_id_list");
                                                                        int[] serviceList = new int[serviceIdList.length()];
                                                                        for (int i = 0; i < serviceIdList.length(); i++)
                                                                        {
                                                                            int serviceId = (int)serviceIdList.get(i);
                                                                            serviceList[i] = serviceId;
                                                                        }
                                                                        /*int[] service_list = {
                                                                                Constants.SERVICE_TYPE_ID_BKASH_CASHIN,
                                                                                Constants.SERVICE_TYPE_ID_DBBL_CASHIN,
                                                                                Constants.SERVICE_TYPE_ID_MCASH_CASHIN,
                                                                                Constants.SERVICE_TYPE_ID_UCASH_CASHIN,
                                                                                Constants.SERVICE_TYPE_ID_TOPUP_GP,
                                                                                Constants.SERVICE_TYPE_ID_TOPUP_ROBI,
                                                                                Constants.SERVICE_TYPE_ID_TOPUP_BANGLALINK,
                                                                                Constants.SERVICE_TYPE_ID_TOPUP_AIRTEL,
                                                                                Constants.SERVICE_TYPE_ID_TOPUP_TELETALK,
                                                                        };*/
                                                                        intent.putExtra("service_list", serviceList);
                                                                        startActivity(intent);
                                                                        progressInit.dismiss();
                                                                    }
                                                                    else
                                                                    {
                                                                        progressInit.dismiss();
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
                                                                    progressInit.dismiss();
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
                                                                progressInit.dismiss();
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
                                            else
                                            {
                                                progressInit.dismiss();
                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        Toast.makeText(getBaseContext(), "Invalid Code.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                        else
                                        {
                                            progressInit.dismiss();
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                    catch(Exception ex)
                                    {
                                        progressInit.dismiss();
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            };
                            initThread.start();
                        }
                        catch (Exception ex){
                            Toast.makeText(Login.this, "System error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
