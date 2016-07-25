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

public class DBBL extends AppCompatActivity {
    private static String baseUrl = "";
    private static int userId = 0;
    private static String sessionId = "";
    private static Button buttonDBBLSend;
    private static EditText editTextCellNumber, editTextAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbbl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextCellNumber = (EditText) findViewById(R.id.etMobileNumberDBBL);
        editTextAmount = (EditText) findViewById(R.id.etAmountDBBL);

        baseUrl = getIntent().getExtras().getString("BASE_URL");
        userId = getIntent().getExtras().getInt("USER_ID");
        sessionId = getIntent().getExtras().getString("SESSION_ID");

        onClickButtonDBBLMenuBackListener();
    }

    public void onClickButtonDBBLMenuBackListener()
    {
        buttonDBBLSend = (Button) findViewById(R.id.bSendNowDBBL);
        buttonDBBLSend.setOnClickListener(
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
                            double doubleAmount = 0.0;
                            String strAmount = editTextAmount.getText().toString();
                            try {
                                doubleAmount = Double.parseDouble(strAmount);
                                if (doubleAmount < 50) {
                                    Toast.makeText(getApplicationContext(), "Amount must be greater than 50", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getApplicationContext(), "Please assign valid amount", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            final ProgressDialog progress = new ProgressDialog(DBBL.this);
                            progress.setTitle("Processing");
                            progress.setMessage("Wait while executing DBBL transaction...");
                            progress.show();
                            Thread bkashThread = new Thread() {
                                @Override
                                public void run() {
                                    try {

                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        HttpClient client = new DefaultHttpClient();
                                        HttpPost post = new HttpPost(baseUrl + "androidapp/transaction/dbbl");

                                        List<NameValuePair> nameValuePairs = new ArrayList<>();

                                        nameValuePairs.add(new BasicNameValuePair("number", editTextCellNumber.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("amount", editTextAmount.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("user_id", "" + userId));
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
                                                JSONObject resultedCurrentBalanceInfo = (JSONObject) resultEvent.get("result_event");
                                                int currentBalance = resultedCurrentBalanceInfo.getInt("current_balance");
                                                String cBalance = Integer.toString(currentBalance);
                                                progress.dismiss();
                                                Intent intent = new Intent();
                                                intent.putExtra("currentBalance", cBalance);
                                                setResult(Constants.PAGE_DBBL_TRANSACTION_SUCCESS, intent);
                                                finish();
                                            } else if (responseCode == 5001) {
                                                progress.dismiss();
                                                Intent intent = new Intent();
                                                setResult(Constants.PAGE_DBBL_SESSION_EXPIRED, intent);
                                                finish();
                                            } else {
                                                String message = "";
                                                try {
                                                    message = (String) resultEvent.get("message");
                                                } catch (Exception ex) {
                                                    message = "Transaction error!!";
                                                }
                                                Intent intent = new Intent();
                                                intent.putExtra("message", message);
                                                setResult(Constants.PAGE_DBBL_SERVER_ERROR, intent);
                                                finish();
                                            }
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra("message", "Invalid response from the server.");
                                            setResult(Constants.PAGE_DBBL_SERVER_ERROR, intent);
                                            finish();
                                        }

                                    } catch (Exception ex) {
                                        Intent intent = new Intent();
                                        setResult(Constants.PAGE_DBBL_SERVER_UNAVAILABLE, intent);
                                        finish();
                                    }
                                    progress.dismiss();
                                }
                            };
                            bkashThread.start();
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Internal Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}
