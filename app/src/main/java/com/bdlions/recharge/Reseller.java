package com.bdlions.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reseller extends AppCompatActivity {
    private static String baseUrl = "";
    private static int userId = 0;
    private static String sessionId = "";
    GridView reseller_grid;
    List<String> grid_reseller_services = new ArrayList<String>();
    List<Integer> grid_reseller_images = new ArrayList<Integer>();
    private String strUserInfo;
    UserInfo userInfo = new UserInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseller);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        baseUrl = getIntent().getExtras().getString("BASE_URL");
        strUserInfo = getIntent().getExtras().getString("USER_INFO");
        sessionId = getIntent().getExtras().getString("SESSION_ID");

        grid_reseller_services.add("Create Reseller");
        grid_reseller_images.add(R.drawable.reseller);

        CustomGrid adapter = new CustomGrid(Reseller.this, grid_reseller_services, grid_reseller_images);
        reseller_grid = (GridView)findViewById(R.id.reseller_list);
        reseller_grid.setAdapter(adapter);
        reseller_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //Toast.makeText(RechargeMenu.this, "Bkash Feature is Not Available", Toast.LENGTH_SHORT).show();
                        Intent intentReseller = new Intent(getBaseContext(), CreateReseller.class);
                        intentReseller.putExtra("USER_INFO", strUserInfo);
                        intentReseller.putExtra("BASE_URL", baseUrl);
                        intentReseller.putExtra("SESSION_ID", sessionId);
                        startActivity(intentReseller);
                        break;

                }
            }
        });
    }

}
