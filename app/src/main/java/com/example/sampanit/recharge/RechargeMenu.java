package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RechargeMenu extends AppCompatActivity {
    //private static Button button_logo_flexiload;
    private static Button button_logo_bKash;
    //private static Button button_logo_fund;
    private static Button button_logo_Report;
    private static Button button_logo_support;
    private static Button button_logo_history;
    private static TextView userName, currentBalance;
    UserInfo userInfo = new UserInfo();
    private String strUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (TextView)findViewById(R.id.userName);
        currentBalance = (TextView)findViewById(R.id.currentBalance);


       /*
        onClickButtonFlexiloadListener();
        onClickButtonLogoFundListener();
        */
        onClickButtonLogobKashListener();
        onClickButtonLogoReportListener();
        onClickButtonLogoSupportListener();
        onClickButtonLogointentHistoryListener();

        currentBalance.setText(getIntent().getExtras().getString("CURRENT_BALANCE"));
        try
        {
            strUserInfo = getIntent().getExtras().getString("USER_INFO");
            JSONObject jsonUserInfo  = new JSONObject(strUserInfo);
            userInfo.setFirstName((String) jsonUserInfo.get("first_name"));
            userInfo.setLastName((String) jsonUserInfo.get("last_name"));
            userInfo.setUserId(Integer.parseInt((String) jsonUserInfo.get("user_id")));
            String UserName = userInfo.getFirstName() +" "+   userInfo.getLastName();
            userName.setText(UserName);
        }
        catch(Exception ex)
        {
            //handle the exception here
        }

        /*String  extraData = "" ;
        extraData = getIntent().getExtras().getString("USER_INFO");
       if(extraData != ""){
           try {
               JSONObject extraObject = new JSONObject(extraData);

               JSONObject userInformation  = (JSONObject)extraObject.get("user_info");

               userInfo.setFirstName((String) userInformation.get("first_name"));
               userInfo.setLastName((String) userInformation.get("last_name"));
               userInfo.setUserId((int) userInformation.get("user_id"));
               String UserName = userInfo.getFirstName() +" "+   userInfo.getLastName();
               userName.setText(UserName);

           } catch (JSONException e) {
               e.printStackTrace();
           }
           System.out.println(userInfo.getUserId());

       }*/

    }



/*

    public void onClickButtonFlexiloadListener(){



        button_logo_flexiload = (Button)findViewById(R.id.FlexiLogo);
        button_logo_flexiload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(userInfo.getUserId());
                        Intent intentFlexiload = new Intent("com.example.sampanit.recharge.Flexiload");
                        startActivity(intentFlexiload);
                    }
                }
        );
    }

    */

    public void onClickButtonLogobKashListener(){
        button_logo_bKash = (Button)findViewById(R.id.bKashLogo);
        button_logo_bKash.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Intent i = new Intent(this,  bKash.class);
                        Intent intentbKash = new Intent(getBaseContext(), bKash.class);
                        intentbKash.putExtra("USER_INFO", strUserInfo);
                        startActivityForResult(intentbKash, Constants.PAGE_BKASH);

                        //    Intent intentbKash = new Intent("com.example.sampanit.recharge.bKash");
                        // startActivity(intentbKash);
                    }
                }
        );
    }
/*
    public void onClickButtonLogoFundListener(){
        button_logo_fund = (Button)findViewById(R.id.bFundLogo);
        button_logo_fund.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(userInfo.getUserId());
                        Intent intentFund = new Intent("com.example.sampanit.recharge.epinVoucher");
                        startActivity(intentFund);
                    }
                }
        );
    }
    */

    public void onClickButtonLogoReportListener(){

        /*button_logo_Report = (Button)findViewById(R.id.ReportLogo);
        button_logo_Report.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //System.out.println(userInfo.getUserId());
                        //Intent intentReport = new Intent("com.example.sampanit.recharge.Report");
                        //startActivity(intentReport);
                    }
                }
        );*/
    }

    public void onClickButtonLogoSupportListener(){
        /*button_logo_support = (Button)findViewById(R.id.SupportLogo);
        button_logo_support.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // System.out.println(userInfo.getUserId());
                        //Intent intentSupport = new Intent("com.example.sampanit.recharge.Support");
                        //startActivity(intentSupport);
                        //Intent intent = new Intent(getBaseContext(), Support.class);
                        //intent.putExtra("TestId", userInfo.getUserId());
                        //startActivityForResult(intent, 1);
                    }
                }
        );*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PAGE_BKASH) {
            if(resultCode == Constants.PAGE_BKASH_BACK){
                //nothing to do
            }
            else if(resultCode == Constants.PAGE_BKASH_TRANSACTION_SUCCESS){
                currentBalance.setText(data.getStringExtra("currentBalance"));
                Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_BKASH_SERVER_UNAVAILABLE){
                Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_BKASH_SERVER_ERROR){
                Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickButtonLogointentHistoryListener(){
        /*button_logo_history = (Button)findViewById(R.id.HistoryLogo);
        button_logo_history.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                            StrictMode.setThreadPolicy(policy);

                            HttpClient client = new DefaultHttpClient();
                            HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/androidapp/transaction/get_transaction_list");


                            List<NameValuePair> nameValuePairs = new ArrayList<>();
                            nameValuePairs.add(new BasicNameValuePair("user_id", "" + userInfo.getUserId()));
                            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = client.execute(post);
                            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                            String result = "";
                            String line = "";
                            while ((line = rd.readLine()) != null) {
                                //textView.append(line);
                                result += line;
                            }
                            if(result != null) {
                                JSONObject resultEvent = new JSONObject(result.toString());
                                int responseCode = (int)resultEvent.get("response_code");
                                String message = (String) resultEvent.get("message");
                                if(responseCode == 2000){
                                    JSONObject resultedUserInfo = (JSONObject) resultEvent.get("result_event");
                                    JSONArray transctionList = (JSONArray) resultedUserInfo.get("transaction_list");
                                    Intent intent = new Intent(getBaseContext(), History.class);
                                    intent.putExtra("EXTRA_TRANSACTION_LIST", transctionList.toString());
                                    startActivity(intent);
                                }
                            }

                        } catch (Exception ex) {
                            //Toast.makeText(this.getApplicationContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(ex.getMessage());
                        }

                    }
                }
        );*/
    }

}
