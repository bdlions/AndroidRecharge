package com.bdlions.recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class CreateReseller extends AppCompatActivity {
    private static Button buttonCreateReseller;
    private static String sessionId = "";
    private static EditText editTextUserName, editTextEmail,editTextCellNumber,editTextPassword,editTextMaxNumber;
    private static String baseUrl = "";
    private static int userId = 0;
    private String strUserInfo;
    UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextUserName = (EditText) findViewById(R.id.etResellerUserName);
        editTextEmail = (EditText) findViewById(R.id.etResellerEmail);
        editTextCellNumber = (EditText) findViewById(R.id.etResellerMobile);
        editTextPassword = (EditText) findViewById(R.id.etResellerPassword);
        editTextMaxNumber = (EditText) findViewById(R.id.etResellerMaxNumber);
        baseUrl = getIntent().getExtras().getString("BASE_URL");
        strUserInfo = getIntent().getExtras().getString("USER_INFO");
        sessionId = getIntent().getExtras().getString("SESSION_ID");
        try
        {
            strUserInfo = getIntent().getExtras().getString("USER_INFO");
            JSONObject jsonUserInfo  = new JSONObject(strUserInfo);
            userId = Integer.parseInt((String) jsonUserInfo.get("user_id"));


        }
        catch(Exception ex)
        {
            //handle the exception here
        }

        onClickButtonCreateResellerListener();


    }

    public void onClickButtonCreateResellerListener() {

        buttonCreateReseller = (Button) findViewById(R.id.bCreateReseller);
        buttonCreateReseller.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //given cell number validation
                            String phoneString = editTextCellNumber.getText().toString();
                            String regexPattern = "^[+880|0][1][1|6|7|8|9][0-9]{8}$";
                            boolean isValid = phoneString.matches(regexPattern);
                            if (isValid != true) {
                                Toast.makeText(getApplicationContext(), "Please assign a valid phone number !!", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            final ProgressDialog progress = new ProgressDialog(CreateReseller.this);
                            progress.setTitle("Processing");
                            progress.setMessage("Wait while creating new reseller...");
                            progress.show();
                            Thread createResellerThread = new Thread() {
                                @Override
                                public void run() {
                                    try {

                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        HttpClient client = new DefaultHttpClient();
                                        HttpPost post = new HttpPost(baseUrl + "androidapp/reseller/create_reseller");

                                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                                        nameValuePairs.add(new BasicNameValuePair("user_name", editTextUserName.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("email", editTextEmail.getText().toString()));

                                        nameValuePairs.add(new BasicNameValuePair("number", editTextCellNumber.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("password", editTextPassword.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("max_no", editTextMaxNumber.getText().toString()));

                                        nameValuePairs.add(new BasicNameValuePair("user_id", "" + userInfo.getUserId()));
                                        nameValuePairs.add(new BasicNameValuePair("session_id", "" + sessionId));
                                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                                        HttpResponse response = client.execute(post);
                                        BufferedReader rd = new BufferedReader
                                                (new InputStreamReader(response.getEntity().getContent()));
                                        String result = "";
                                        String line = "";
                                        while ((line = rd.readLine()) != null) {
                                            result += line;
                                        }
                                        if (result != null) {
                                            JSONObject resultEvent = new JSONObject(result.toString());

                                            int responseCode = 0;
                                            try {
                                                responseCode = (int) resultEvent.get("response_code");
                                            } catch (Exception ex) {

                                            }
                                            if (responseCode == 2000) {
                                                String message = "Reseller Add Successfully!!";
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                            } else if (responseCode == 5001) {
                                                Toast.makeText(getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String message = "";
                                                try {
                                                    message = (String) resultEvent.get("message");
                                                } catch (Exception ex) {
                                                    message = "Reseller Creation error!!";
                                                }
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                          String  message = "Invalid response from the server.";
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception ex) {
                                        Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                                    }
                                    progress.dismiss();
                                }
                            };
                            createResellerThread.start();
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Internal Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}
