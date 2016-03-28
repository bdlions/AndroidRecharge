package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RechargeMenu extends AppCompatActivity {
    private static Button button_logo_flexiload;
    private static Button button_logo_bKash;
    private static Button button_logo_fund;
    private static Button button_logo_Report;
    private static Button button_logo_support;
    private static Button button_logo_history;
    private static TextView userName, currentBalance;
    UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (TextView)findViewById(R.id.userName);
        currentBalance = (TextView)findViewById(R.id.currentBalance);

        onClickButtonFlexiloadListener();
        onClickButtonLogobKashListener();
        onClickButtonLogoFundListener();
        onClickButtonLogoReportListener();
        onClickButtonLogoSupportListener();
        onClickButtonLogointentHistoryListener();
        String  extraData = "" ;
        extraData = getIntent().getExtras().getString("EXTRA_OBJECT");
       if(extraData != ""){
           try {
               JSONObject extraObject = new JSONObject(extraData);
               int balance = (int)extraObject.get("current_balance");
               String cBalance = Integer.toString(balance);
               currentBalance.setText(cBalance);
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

       }

    }





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

    public void onClickButtonLogobKashListener(){
        button_logo_bKash = (Button)findViewById(R.id.bKashLogo);
        button_logo_bKash.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentbKash = new Intent(getBaseContext(), bKash.class);
                    //    Intent intentbKash = new Intent("com.example.sampanit.recharge.bKash");
                        intentbKash.putExtra("USER_ID", userInfo.getUserId());
                        startActivity(intentbKash);
                    }
                }
        );
    }

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

    public void onClickButtonLogoReportListener(){

        button_logo_Report = (Button)findViewById(R.id.ReportLogo);
        button_logo_Report.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(userInfo.getUserId());
                        Intent intentReport = new Intent("com.example.sampanit.recharge.Report");
                        startActivity(intentReport);
                    }
                }
        );
    }

    public void onClickButtonLogoSupportListener(){
        button_logo_support = (Button)findViewById(R.id.SupportLogo);
        button_logo_support.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(userInfo.getUserId());
                        Intent intentSupport = new Intent("com.example.sampanit.recharge.Support");
                        startActivity(intentSupport);
                    }
                }
        );
    }

    public void onClickButtonLogointentHistoryListener(){
        button_logo_history = (Button)findViewById(R.id.HistoryLogo);
        button_logo_history.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(userInfo.getUserId());
                        Intent intentHistory = new Intent("com.example.sampanit.recharge.History");
                        startActivity(intentHistory);
                    }
                }
        );
    }

}
