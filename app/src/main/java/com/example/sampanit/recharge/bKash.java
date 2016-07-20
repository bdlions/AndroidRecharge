package com.example.sampanit.recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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

public class bKash extends AppCompatActivity {
    //private static Button button_bKash_menu_back;
    private static Button buttonBkashSend;
    private static EditText editTextCellNumber, editTextAmount;
    UserInfo userInfo = new UserInfo();
    private String strUserInfo;
    private int currentBalance;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_kash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextCellNumber = (EditText) findViewById(R.id.etMobileNumberbKash);
        editTextAmount = (EditText) findViewById(R.id.etAmountbKash);
        strUserInfo = getIntent().getExtras().getString("USER_INFO");
        try
        {
            strUserInfo = getIntent().getExtras().getString("USER_INFO");
            JSONObject jsonUserInfo  = new JSONObject(strUserInfo);
            userInfo.setFirstName((String) jsonUserInfo.get("first_name"));
            userInfo.setLastName((String) jsonUserInfo.get("last_name"));
            userInfo.setUserId(Integer.parseInt((String) jsonUserInfo.get("user_id")));
        }
        catch(Exception ex)
        {
            //handle the exception here
        }
        //userId = getIntent().getExtras().getInt("USER_ID");


       //String userInfo = getIntent().getExtras().getString("USER_INFO");



        //UserInfo abc = userInfo.getClass();

       // System.out.println(userInfo);
        /*if(userInfo != ""){
            try {
            JSONObject userInformation  = new JSONObject(userInfo);
                System.out.println(userInformation);
             //   userInfo.setUserId(userInformation.getInt(""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }*/


            onClickButtonbKashMenuBackListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String stredittext=data.getStringExtra("edittextvalue");
            }
        }
    }

    public void onClickButtonbKashMenuBackListener() {
//        button_bKash_menu_back = (Button) findViewById(R.id.bbKashMenuBack);
//        button_bKash_menu_back.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        setResult(Constants.PAGE_BKASH_BACK, intent);
//                        finish();
//                    }
//                }
//        );

        buttonBkashSend = (Button) findViewById(R.id.bSendNowbKash);
        buttonBkashSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            //given cell number validation
                            String phoneString = editTextCellNumber.getText().toString();
                            String regexPattern = "^[+880|0][1][1|6|7|8|9][0-9]{8}$";
                            boolean isValid = phoneString.matches(regexPattern);
                            if(isValid != true){
                                Toast.makeText(getApplicationContext(), "Please assign a valid phone number !!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            double doubleAmount = 0.0;
                            String strAmount = editTextAmount.getText().toString();
                            try
                            {
                                doubleAmount = Double.parseDouble(strAmount);
                                if(doubleAmount < 50)
                                {
                                    Toast.makeText(getApplicationContext(), "Amount must be greater than 50", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            catch(Exception ex)
                            {
                                Toast.makeText(getApplicationContext(), "Please assign valid amount", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            final ProgressDialog progress = new ProgressDialog(bKash.this);
                            progress.setTitle("Processing");
                            progress.setMessage("Wait while executing bkash transaction...");
                            progress.show();
                            Thread bkashThread = new Thread() {
                                @Override
                                public void run()
                                {
                                    try
                                    {

                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                        HttpClient client = new DefaultHttpClient();
                                        HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/androidapp/transaction/demobkash");

                                        List<NameValuePair> nameValuePairs = new ArrayList<>();

                                        nameValuePairs.add(new BasicNameValuePair("number", editTextCellNumber.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("amount", editTextAmount.getText().toString()));
                                        nameValuePairs.add(new BasicNameValuePair("user_id", "" + userInfo.getUserId()));


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

                                            }
                                            if(responseCode == 2000){
                                                JSONObject resultedCurrentBalanceInfo = (JSONObject) resultEvent.get("result_event");
                                                int currentBalance =  resultedCurrentBalanceInfo.getInt("current_balance");
                                                String cBalance = Integer.toString(currentBalance);
                                                progress.dismiss();
                                                Intent intent = new Intent();
                                                intent.putExtra("currentBalance", cBalance);
                                                setResult(Constants.PAGE_BKASH_TRANSACTION_SUCCESS, intent);
                                                finish();
                                            }
                                            else
                                            {
                                                String message = "";
                                                try
                                                {
                                                    message = (String) resultEvent.get("message");
                                                }
                                                catch(Exception ex)
                                                {
                                                    message = "Transaction error!!";
                                                }
                                                Intent intent = new Intent();
                                                intent.putExtra("message", message);
                                                setResult(Constants.PAGE_BKASH_SERVER_ERROR, intent);
                                                finish();
                                            }
                                        }
                                        else
                                        {
                                            Intent intent = new Intent();
                                            intent.putExtra("message", "Invalid response from the server.");
                                            setResult(Constants.PAGE_BKASH_SERVER_ERROR, intent);
                                            finish();
                                        }

                                    }
                                    catch (Exception ex) {
                                        Intent intent = new Intent();
                                        setResult(Constants.PAGE_BKASH_SERVER_UNAVAILABLE, intent);
                                        finish();
                                    }
                                    progress.dismiss();
                                }
                            };
                            bkashThread.start();
                        }
                        catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Internal Error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "bKash Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sampanit.recharge/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "bKash Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sampanit.recharge/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
