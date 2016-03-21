package com.example.sampanit.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    private static Button button_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onClickButtonLoginListener();

    }
    public void onClickButtonLoginListener(){
        button_Login = (Button)findViewById(R.id.bLogin);
        button_Login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentLogin = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        startActivity(intentLogin);
                    }
                }
        );
    }
}
