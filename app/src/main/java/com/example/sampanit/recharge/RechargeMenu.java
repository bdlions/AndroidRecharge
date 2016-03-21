package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class RechargeMenu extends AppCompatActivity {
    private static Button button_logo_flexiload;
    private static Button button_logo_bKash;
    private static Button button_logo_fund;
    private static Button button_logo_Report;
    private static Button button_logo_support;
    private static Button button_logo_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickButtonFlexiloadListener();
        onClickButtonLogobKashListener();
        onClickButtonLogoFundListener();
        onClickButtonLogoReportListener();
        onClickButtonLogoSupportListener();
        onClickButtonLogointentHistoryListener();

    }


    public void onClickButtonFlexiloadListener(){
        button_logo_flexiload = (Button)findViewById(R.id.FlexiLogo);
        button_logo_flexiload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        Intent intentbKash = new Intent("com.example.sampanit.recharge.bKash");
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
                        Intent intentHistory = new Intent("com.example.sampanit.recharge.History");
                        startActivity(intentHistory);
                    }
                }
        );
    }
}
