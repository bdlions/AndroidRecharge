package com.example.sampanit.recharge;

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
    private static String baseUrl = "";
    private static int userId = 0;
    private static String sessionId = "";
    //private static Button button_logo_flexiload;
    private static Button button_logo_bKash;
    //private static Button button_logo_fund;
    private static Button button_logo_Report;
    private static Button button_logo_support;
    private static Button button_logo_history;
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

       /*

        onClickButtonLogoFundListener();
        */
        //     onClickButtonLogobKashListener();
        onClickButtonLogoReportListener();
        onClickButtonLogoSupportListener();
        onClickButtonLogointentHistoryListener();

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
/*
        String  extraData = "" ;
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

       }

*/
    }





//    public void onClickButtonLogobKashListener(){
//        button_logo_bKash = (Button)findViewById(R.id.bKashLogo);
//        button_logo_bKash.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        // Intent i = new Intent(this,  bKash.class);
//                        Intent intentbKash = new Intent(getBaseContext(), bKash.class);
//                        intentbKash.putExtra("USER_INFO", strUserInfo);
//                        startActivityForResult(intentbKash, Constants.PAGE_BKASH);
//
//                        //    Intent intentbKash = new Intent("com.example.sampanit.recharge.bKash");
//                        // startActivity(intentbKash);
//                    }
//                }
//        );
//    }
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
