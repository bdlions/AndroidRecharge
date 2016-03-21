package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Support extends AppCompatActivity {
    private static Button button_support_menu_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickButtonSupportMenuBackListener();

    }
    public void onClickButtonSupportMenuBackListener(){
        button_support_menu_back = (Button)findViewById(R.id.bSupportMenuBack);
        button_support_menu_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentSupportMenuBack = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        startActivity(intentSupportMenuBack);
                    }
                }
        );
    }
}
