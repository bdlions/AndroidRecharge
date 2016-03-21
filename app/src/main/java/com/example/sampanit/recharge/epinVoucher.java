package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class epinVoucher extends AppCompatActivity {
    private static Button button_fund_menu_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epin_voucher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickButtonFundMenuBackListener();
    }
    public void onClickButtonFundMenuBackListener(){
        button_fund_menu_back = (Button)findViewById(R.id.bFundMenuBack);
        button_fund_menu_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentFundadMenuBack = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        startActivity(intentFundadMenuBack);
                    }
                }
        );
    }
}
