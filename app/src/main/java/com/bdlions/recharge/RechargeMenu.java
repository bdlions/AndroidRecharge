package com.bdlions.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class RechargeMenu extends AppCompatActivity {
    private static String baseUrl = "";
    private static int userId = 0;
    private static String sessionId = "";
    private static TextView userName, currentBalance;
    UserInfo userInfo = new UserInfo();
    private boolean topUpFlag = false;
    private  int[] service_list;
    GridView grid;
    private String strUserInfo;
    List<Integer> history_services = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service_list = getIntent().getExtras().getIntArray("service_list");

        List<String> grid_services = new ArrayList<String>();

        List<Integer> grid_image = new ArrayList<Integer>();


        for (int i = 0; i < service_list.length; i++) {
            if(service_list[i] == Constants.SERVICE_TYPE_ID_BKASH_CASHIN){
                grid_services.add("bKash");
                grid_image.add( R.drawable.bkash);
                history_services.add(Constants.SERVICE_TYPE_ID_BKASH_CASHIN);
            } else if(service_list[i] == Constants.SERVICE_TYPE_ID_DBBL_CASHIN){
                grid_services.add("DBBL");
                grid_image.add( R.drawable.dbbl);
                history_services.add(Constants.SERVICE_TYPE_ID_DBBL_CASHIN);
            } else if(service_list[i] == Constants.SERVICE_TYPE_ID_MCASH_CASHIN){
                grid_services.add("mCash");
                grid_image.add( R.drawable.mcash);
                history_services.add(Constants.SERVICE_TYPE_ID_MCASH_CASHIN);
            }  else if(service_list[i] == Constants.SERVICE_TYPE_ID_UCASH_CASHIN){
                grid_services.add("UCash");
                grid_image.add( R.drawable.ucash);
                history_services.add(Constants.SERVICE_TYPE_ID_UCASH_CASHIN);
            }  else if(service_list[i] == Constants.SERVICE_TYPE_ID_TOPUP_GP
                    || service_list[i] == Constants.SERVICE_TYPE_ID_TOPUP_AIRTEL
                    ||  service_list[i] == Constants.SERVICE_TYPE_ID_TOPUP_BANGLALINK
                    ||  service_list[i] == Constants.SERVICE_TYPE_ID_TOPUP_ROBI
                    ||  service_list[i] == Constants.SERVICE_TYPE_ID_TOPUP_TELETALK){
                topUpFlag = true;
            }
        }
        if(topUpFlag != false){
            grid_services.add("TopUp");
            grid_image.add( R.drawable.flexiload);
            history_services.add(Constants.SERVICE_TYPE_TOPUP_HISTORY_FLAG);

        }

        grid_services.add("History");
        grid_image.add( R.drawable.history);
        grid_services.add("Reseller");
        grid_image.add( R.drawable.reseller);
        grid_services.add("Account");
        grid_image.add( R.drawable.account);


        userName = (TextView)findViewById(R.id.userName);
        currentBalance = (TextView)findViewById(R.id.currentBalance);


        CustomGrid adapter = new CustomGrid(RechargeMenu.this, grid_services, grid_image);
        grid=(GridView)findViewById(R.id.list);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        //Toast.makeText(RechargeMenu.this, "Bkash Feature is Not Available", Toast.LENGTH_SHORT).show();
                        Intent intentbKash = new Intent(getBaseContext(), bKash.class);
                        intentbKash.putExtra("BASE_URL", baseUrl);
                        intentbKash.putExtra("USER_ID", userId);
                        intentbKash.putExtra("SESSION_ID", sessionId);
                        intentbKash.putExtra("USER_INFO", strUserInfo);
                        startActivityForResult(intentbKash, Constants.PAGE_BKASH);
                        break;

                    case 1:
                        Intent intentDBBL = new Intent(getBaseContext(), DBBL.class);
                        intentDBBL.putExtra("BASE_URL", baseUrl);
                        intentDBBL.putExtra("USER_ID", userId);
                        intentDBBL.putExtra("SESSION_ID", sessionId);
                        startActivityForResult(intentDBBL, Constants.PAGE_DBBL);
                        break;

                    case 2:
                        Intent intentmCash = new Intent(getBaseContext(), mCash.class);
                        intentmCash.putExtra("BASE_URL", baseUrl);
                        intentmCash.putExtra("USER_ID", userId);
                        intentmCash.putExtra("SESSION_ID", sessionId);
                        startActivityForResult(intentmCash, Constants.PAGE_MCASH);
                        break;
                    case 3:
//                        Toast.makeText(RechargeMenu.this, "Recharge Feature is Not Available", Toast.LENGTH_SHORT).show();
                        Intent intentUCash = new Intent(getBaseContext(), UCash.class);
                        intentUCash.putExtra("BASE_URL", baseUrl);
                        intentUCash.putExtra("USER_ID", userId);
                        intentUCash.putExtra("SESSION_ID", sessionId);
                        startActivityForResult(intentUCash, Constants.PAGE_UCASH);
                        break;
                    case 4:
//                        Toast.makeText(RechargeMenu.this, "Recharge Feature is Not Available", Toast.LENGTH_SHORT).show();
                        Intent intentTopUp = new Intent(getBaseContext(), TopUp.class);
                        intentTopUp.putExtra("BASE_URL", baseUrl);
                        intentTopUp.putExtra("USER_ID", userId);
                        intentTopUp.putExtra("SESSION_ID", sessionId);
                        startActivityForResult(intentTopUp, Constants.PAGE_TOPUP);
                        break;
                    case 5:
                        Intent intentHistory = new Intent(getBaseContext(), History.class);
                        intentHistory.putExtra("BASE_URL", baseUrl);
                        intentHistory.putExtra("USER_INFO", strUserInfo);
                        intentHistory.putIntegerArrayListExtra("history_services", (ArrayList<Integer>) history_services);
                        startActivity(intentHistory);
                        break;

                    case 6:
                        Intent intentReseller = new Intent(getBaseContext(), Reseller.class);
                        intentReseller.putExtra("USER_INFO", strUserInfo);
                        intentReseller.putExtra("SESSION_ID", sessionId);
                        intentReseller.putExtra("BASE_URL", baseUrl);
                        startActivity(intentReseller);
                        break;

                    case 7:
                        Intent intentAccount = new Intent(getBaseContext(), Account.class);
                        intentAccount.putExtra("BASE_URL", baseUrl);
                        intentAccount.putExtra("USER_INFO", strUserInfo);
                        intentAccount.putIntegerArrayListExtra("history_services", (ArrayList<Integer>) history_services);
                        startActivity(intentAccount);
                        break;
                }
            }
        });


        baseUrl = getIntent().getExtras().getString("BASE_URL");
        currentBalance.setText(getIntent().getExtras().getString("CURRENT_BALANCE"));
        sessionId = getIntent().getExtras().getString("SESSION_ID");
        try
        {
            strUserInfo = getIntent().getExtras().getString("USER_INFO");
            JSONObject jsonUserInfo  = new JSONObject(strUserInfo);
            userInfo.setFirstName((String) jsonUserInfo.get("first_name"));
            userInfo.setLastName((String) jsonUserInfo.get("last_name"));
            userInfo.setUserId(Integer.parseInt((String) jsonUserInfo.get("user_id")));
            userId = Integer.parseInt((String) jsonUserInfo.get("user_id"));
            String UserName = userInfo.getFirstName() +" "+   userInfo.getLastName();
            userName.setText(UserName);
        }
        catch(Exception ex)
        {
            //handle the exception here
        }

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
            else if(resultCode == Constants.PAGE_BKASH_SESSION_EXPIRED){
                Toast.makeText(getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        }

        else if (requestCode == Constants.PAGE_DBBL) {
            if(resultCode == Constants.PAGE_DBBL_TRANSACTION_SUCCESS){
                currentBalance.setText(data.getStringExtra("currentBalance"));
                Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_DBBL_SERVER_UNAVAILABLE){
                Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_DBBL_SERVER_ERROR){
                Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_DBBL_SESSION_EXPIRED){
                Toast.makeText(getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        }

        else if (requestCode == Constants.PAGE_MCASH) {
            if(resultCode == Constants.PAGE_MCASH_TRANSACTION_SUCCESS){
                currentBalance.setText(data.getStringExtra("currentBalance"));
                Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_MCASH_SERVER_UNAVAILABLE){
                Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_MCASH_SERVER_ERROR){
                Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_MCASH_SESSION_EXPIRED){
                Toast.makeText(getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        }

        else if (requestCode == Constants.PAGE_UCASH) {
            if(resultCode == Constants.PAGE_UCASH_TRANSACTION_SUCCESS){
                currentBalance.setText(data.getStringExtra("currentBalance"));
                Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_UCASH_SERVER_UNAVAILABLE){
                Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_UCASH_SERVER_ERROR){
                Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_UCASH_SESSION_EXPIRED){
                Toast.makeText(getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        }

        else if (requestCode == Constants.PAGE_TOPUP) {
            if(resultCode == Constants.PAGE_TOPUP_TRANSACTION_SUCCESS){
                currentBalance.setText(data.getStringExtra("currentBalance"));
                Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_TOPUP_SERVER_UNAVAILABLE){
                Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_TOPUP_SERVER_ERROR){
                Toast.makeText(getApplicationContext(), data.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == Constants.PAGE_TOPUP_SESSION_EXPIRED){
                Toast.makeText(getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        }
    }
}
