package com.bdlions.recharge;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class Account extends AppCompatActivity {
    private static String baseUrl = "";
    private static int userId = 0;
    private static String sessionId = "";
    private String strUserInfo;
    GridView account_grid;
    List<String> grid_account_services = new ArrayList<String>();
    List<Integer> grid_account_images = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        baseUrl = getIntent().getExtras().getString("BASE_URL");
        strUserInfo = getIntent().getExtras().getString("USER_INFO");
        sessionId = getIntent().getExtras().getString("SESSION_ID");

        grid_account_services.add("Payment History");
        grid_account_images.add(R.drawable.payment);
        grid_account_services.add("Change Password");
        grid_account_images.add(R.drawable.change_password);

        CustomGrid adapter = new CustomGrid(Account.this, grid_account_services, grid_account_images);
        account_grid = (GridView)findViewById(R.id.account_list);
        account_grid.setAdapter(adapter);
        account_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        showPaymentHistory();
                        break;

                    case 1:
                        Intent intentChangePassword = new Intent(Account.this, ChangePassword.class);
                        startActivity(intentChangePassword);
                        break;
                }
            }
        });
    }

    public void showPaymentHistory()
    {
        try
        {
            final ProgressDialog progress = new ProgressDialog(Account.this);
            progress.setTitle("Processing");
            progress.setMessage("Retrieving Payment transaction list...");
            progress.show();
            Thread paymentThread = new Thread() {
                @Override
                public void run()
                {
                    try
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(baseUrl+"androidapp/transaction/get_payment_transaction_list");

                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("user_id", userId+""));
                        nameValuePairs.add(new BasicNameValuePair("session_id", sessionId));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader
                                (new InputStreamReader(response.getEntity().getContent()));
                        String result = "";
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        if(result != null) {
                            JSONObject resultEvent = new JSONObject(result.toString());

                            int responseCode = 0;
                            try
                            {
                                responseCode = (int)resultEvent.get("response_code");
                            }
                            catch(Exception ex)
                            {
                                progress.dismiss();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(responseCode == 2000){
                                try
                                {
                                    JSONArray paymentTransactionList = resultEvent.getJSONArray("payment_list");
                                    Intent intentPayment = new Intent(getBaseContext(), Payment.class);
                                    intentPayment.putExtra("PAYMENT_TRANSACTION_LIST", paymentTransactionList.toString());
                                    startActivity(intentPayment);
                                }
                                catch(Exception ex)
                                {
                                    progress.dismiss();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "Invalid response from the server..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }
                        else
                        {
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Invalid response from the server...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    catch (Exception ex) {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    progress.dismiss();
                }
            };
            paymentThread.start();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
        }
    }
}
