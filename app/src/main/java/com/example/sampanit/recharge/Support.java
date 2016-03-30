package com.example.sampanit.recharge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

        //this.setTitle("Second Activity");


        int  userId ;
        userId = getIntent().getExtras().getInt("TestId");
        if(userId != 0 ) {

        }

        onClickButtonSupportMenuBackListener();

        //Intent intent = new Intent();
       // intent.putExtra("edittextvalue","value_here");
       // setResult(RESULT_OK, intent);
       // finish();

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Obj", "back here");
        setResult(Activity.RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
    public void onClickButtonSupportMenuBackListener(){
        button_support_menu_back = (Button)findViewById(R.id.bSupportMenuBack);
        button_support_menu_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(),RechargeMenu.class);
                        intent.putExtra("Obj", "value_here");
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                      //  super.onBackPressed();
                    }
                }
        );
    }
}
