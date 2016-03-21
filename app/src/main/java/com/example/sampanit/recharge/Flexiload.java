package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Flexiload extends AppCompatActivity {
    private static Button button_flexiload_menu_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexiload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickButtonFlexiloadMenuBackListener();

    }
    public void onClickButtonFlexiloadMenuBackListener(){
        button_flexiload_menu_back = (Button)findViewById(R.id.bFlexiloadMenuBack);
        button_flexiload_menu_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentFlexiloadMenuBack = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        startActivity(intentFlexiloadMenuBack);
                    }
                }
        );
    }
}
