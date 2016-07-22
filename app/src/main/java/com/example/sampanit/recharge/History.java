package com.example.sampanit.recharge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
import java.util.HashMap;
import java.util.List;

//import static com.example.sampanit.recharge.Constants.FIRST_COLUMN;
//import static com.example.sampanit.recharge.Constants.SECOND_COLUMN;
//import static com.example.sampanit.recharge.Constants.THIRD_COLUMN;
//import static com.example.sampanit.recharge.Constants.FOURTH_COLUMN;

public class History extends AppCompatActivity {
    private static String baseUrl = "";
    private static int userId = 0;
    private static String sessionId;
    private static Button button_report_menu_back;
    private ArrayList<HashMap<String, String>> historyList;
    private String strUserInfo;
    UserInfo userInfo = new UserInfo();

    ArrayList<Integer> history_services ;
    GridView grid_history;
//    String[] grid_items = {
//            "Bluetooth",
//            "Email"
//    };
//
//    int[] grid_image1 = {
//            R.drawable.bkash,
//            R.drawable.flexiload
//
//    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<Integer> grid_image = new ArrayList<Integer>();
        List<String> grid_text_list = new ArrayList<String>();
        baseUrl = getIntent().getExtras().getString("BASE_URL");
        strUserInfo = getIntent().getExtras().getString("USER_INFO");
        try
        {
            strUserInfo = getIntent().getExtras().getString("USER_INFO");
            JSONObject jsonUserInfo  = new JSONObject(strUserInfo);
            userInfo.setFirstName((String) jsonUserInfo.get("first_name"));
            userInfo.setLastName((String) jsonUserInfo.get("last_name"));
            userInfo.setUserId(Integer.parseInt((String) jsonUserInfo.get("user_id")));
        }
        catch(Exception ex)
        {
            //handle the exception here
        }

        history_services = getIntent().getExtras().getIntegerArrayList("history_services");
        for (int i = 0; i < history_services.size(); i++) {
            if(history_services.get(i) == Constants.SERVICE_TYPE_TOPUP_HISTORY_FLAG){
                grid_image.add(R.drawable.flexiload);
                grid_text_list.add("TopUp History");

            }
            else if(history_services.get(i) == Constants.SERVICE_TYPE_ID_BKASH_CASHIN){
                grid_image.add( R.drawable.bkash);
                grid_text_list.add("bKash History");
            }
            else if(history_services.get(i) == Constants.SERVICE_TYPE_ID_DBBL_CASHIN){
                grid_image.add( R.drawable.dbbl);
                grid_text_list.add("DBBL History");
            }
            else if(history_services.get(i) == Constants.SERVICE_TYPE_ID_MCASH_CASHIN){
                grid_image.add( R.drawable.mcash);
                grid_text_list.add("mCash History");
            }
            else if(history_services.get(i) == Constants.SERVICE_TYPE_ID_UCASH_CASHIN){
                grid_image.add( R.drawable.ucash);
                grid_text_list.add("UCash History");
            }
        }

        CustomGrid historyAdapter = new CustomGrid(History.this, grid_text_list, grid_image);
        grid_history=(GridView)findViewById(R.id.history_list);
        grid_history.setAdapter(historyAdapter);
        grid_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
//
//                        try
//                        {
//                            //given cell number validation
//
//
//
//                            final ProgressDialog progress = new ProgressDialog(History.this);
//                            progress.setTitle("Processing");
//                            progress.setMessage("Wait while executing bkash transaction...");
//                            progress.show();
//                            Thread bkashThread = new Thread() {
//                                @Override
//                                public void run()
//                                {
//                                    try
//                                    {
//
//                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                                        StrictMode.setThreadPolicy(policy);
//                                        HttpClient client = new DefaultHttpClient();
//                                        HttpPost post = new HttpPost("http://122.144.10.249/rechargeserver/androidapp/transaction/get_transaction_list");
//
//                                        List<NameValuePair> nameValuePairs = new ArrayList<>();
//
//                                        nameValuePairs.add(new BasicNameValuePair("user_id", "" + userInfo.getUserId()));
//
//
//                                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                                        HttpResponse response = client.execute(post);
//                                        BufferedReader rd = new BufferedReader
//                                                (new InputStreamReader(response.getEntity().getContent()));
//                                        String result = "";
//                                        String line = "";
//                                        while ((line = rd.readLine()) != null) {
//                                            result += line;
//                                        }
//                                        if(result != null) {
//                                            JSONObject resultEvent = new JSONObject(result.toString());
//
//                                            int responseCode = 0;
//                                            try
//                                            {
//                                                responseCode = (int)resultEvent.get("response_code");
//                                            }
//                                            catch(Exception ex)
//                                            {
//
//                                            }
//                                            if(responseCode == 2000){
//                                                JSONObject resultedCurrentBalanceInfo = (JSONObject) resultEvent.get("result_event");
//                                                int currentBalance =  resultedCurrentBalanceInfo.getInt("current_balance");
//                                                String cBalance = Integer.toString(currentBalance);
//                                                progress.dismiss();
//                                                Intent intent = new Intent();
//                                                intent.putExtra("currentBalance", cBalance);
//                                                setResult(Constants.PAGE_BKASH_TRANSACTION_SUCCESS, intent);
//                                                finish();
//                                            }
//                                            else
//                                            {
//                                                String message = "";
//                                                try
//                                                {
//                                                    message = (String) resultEvent.get("message");
//                                                }
//                                                catch(Exception ex)
//                                                {
//                                                    message = "Transaction error!!";
//                                                }
//                                                Intent intent = new Intent();
//                                                intent.putExtra("message", message);
//                                                setResult(Constants.PAGE_BKASH_SERVER_ERROR, intent);
//                                                finish();
//                                            }
//                                        }
//                                        else
//                                        {
//                                            Intent intent = new Intent();
//                                            intent.putExtra("message", "Invalid response from the server.");
//                                            setResult(Constants.PAGE_BKASH_SERVER_ERROR, intent);
//                                            finish();
//                                        }
//
//                                    }
//                                    catch (Exception ex) {
//                                        Intent intent = new Intent();
//                                        setResult(Constants.PAGE_BKASH_SERVER_UNAVAILABLE, intent);
//                                        finish();
//                                    }
//                                    progress.dismiss();
//                                }
//                            };
//                            bkashThread.start();
//                        }
//                        catch (Exception ex){
//                            Toast.makeText(getApplicationContext(), "Internal Error.", Toast.LENGTH_SHORT).show();
//                        }

                        //Toast.makeText(History.this, "Bkash Feature is Not Available", Toast.LENGTH_SHORT).show();
                        showBkashHistory();
                        break;

                    case 1:
                        showDBBLHistory();
                        break;

                    case 2:
                        showMcashHistory();
                        break;

                    case 3:
                        showUcashHistory();
                        break;

                    case 4:
                        showTopupHistory();
                        break;

                }
            }
        });






//        ListView listView = (ListView) findViewById(R.id.list_view_history);
//        populateList();
//        bKashHistoryListViewAdapter adapter = new bKashHistoryListViewAdapter(this, historyList);
//        listView.setAdapter(adapter);


//        String  extraData = "" ;
//        extraData = getIntent().getExtras().getString("EXTRA_TRANSACTION_LIST");
//        if(extraData != ""){
//            try {
//                JSONArray jsonArray = new JSONArray(extraData);
//
//
//                TableLayout t1;
//
//                TableLayout tl = (TableLayout) findViewById(R.id.main_table);
//                int length = jsonArray.length();
//                TextView[] textArray = new TextView[length];
//                TableRow[] tr_head = new TableRow[length];
//
//                tr_head[0] = new TableRow(this);
//                tr_head[0].setId(0);
//                tr_head[0].setBackgroundColor(Color.WHITE);
//                tr_head[0].setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, 1));
//
//                textArray[0] = new TextView(this);
//                textArray[0].setText("Amount");
//                textArray[0].setTextColor(Color.BLUE);
//                textArray[0].setPadding(5, 5, 5, 5);
//                tr_head[0].addView(textArray[0]);
//
//                textArray[0] = new TextView(this);
//                textArray[0].setText("Mobile");
//                textArray[0].setTextColor(Color.BLUE);
//                textArray[0].setPadding(5, 5, 5, 5);
//                tr_head[0].addView(textArray[0]);
//
//                textArray[0] = new TextView(this);
//                textArray[0].setText("Title");
//                textArray[0].setTextColor(Color.BLUE);
//                textArray[0].setPadding(5, 5, 5, 5);
//                tr_head[0].addView(textArray[0]);
//
//                tl.addView(tr_head[0], new TableLayout.LayoutParams(
//                        GridLayout.LayoutParams.MATCH_PARENT,
//                        GridLayout.LayoutParams.WRAP_CONTENT));
//
//                for (int i = 0; i < length; i++) {
//
//
//                    String transctionData = jsonArray.get(i).toString();
//                    JSONObject transction = new JSONObject(transctionData.toString());
//
//                    int cellNo = transction.getInt("cell_no");
//                    String mobile = Integer.toString(cellNo);
//                    int amount = transction.getInt("amount");
//                    String userAmout = Integer.toString(amount);
//                    String title = (String)transction.get("title");
//
//
//                    //Create the tablerows
//                    tr_head[i] = new TableRow(this);
//                    tr_head[i].setId(i + 1);
//                    tr_head[i].setBackgroundColor(Color.WHITE);
//                    tr_head[i].setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, 1));
//                    // Here create the TextView dynamically
//
//                    /*
//                    ImageView imgView =(ImageView) findViewById(R.id.imgView);
//                    Drawable drawable  = getResources().getDrawable(R.drawable.img);
//                    imgView.setImageDrawable(drawable);
//
//
//                    textArray[i] = new TextView(this);
//                    textArray[i].setId(i + 110);
//                    textArray[i].setTextColor(Color.BLUE);
//                    textArray[i].setPadding(5, 5, 5, 5);
//                    tr_head[i].addView(textArray[i]);
//                    */
//
//
//                    textArray[i] = new TextView(this);
//                    textArray[i].setId(i + 111);
//                    textArray[i].setText(userAmout);
//                    textArray[i].setTextColor(Color.BLUE);
//                    textArray[i].setPadding(5, 5, 5, 5);
//                    tr_head[i].addView(textArray[i]);
//
//                    textArray[i] = new TextView(this);
//                    textArray[i].setId(i + 112);
//                    textArray[i].setText(mobile);
//                    textArray[i].setTextColor(Color.BLUE);
//                    textArray[i].setPadding(5, 5, 5, 5);
//                    tr_head[i].addView(textArray[i]);
//
//                    textArray[i] = new TextView(this);
//                    textArray[i].setId(i + 113);
//                    textArray[i].setText(title);
//                    textArray[i].setTextColor(Color.BLUE);
//                    textArray[i].setPadding(5, 5, 5, 5);
//                    tr_head[i].addView(textArray[i]);
//
//
//                    tl.addView(tr_head[i], new TableLayout.LayoutParams(
//                            GridLayout.LayoutParams.MATCH_PARENT,
//                            GridLayout.LayoutParams.WRAP_CONTENT));
//
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }


        //onClickButtonReportMenuBackListener();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }
//
//    private void populateList() {
//        historyList = new ArrayList<HashMap<String, String>>();
//        HashMap<String, String> temp = new HashMap<String, String>();
//        temp.put(FIRST_COLUMN, "Cell No");
//        temp.put(SECOND_COLUMN, "Amount");
//        temp.put(THIRD_COLUMN, "Title");
//        temp.put(FOURTH_COLUMN, "Status");
//        historyList.add(temp);
//
//        HashMap<String, String> temp2 = new HashMap<String, String>();
//        temp2.put(FIRST_COLUMN, "01912314466");
//        temp2.put(SECOND_COLUMN, "1000");
//        temp2.put(THIRD_COLUMN, "bKash");
//        temp2.put(FOURTH_COLUMN, "Success");
//        historyList.add(temp2);
//
//        HashMap<String, String> temp3 = new HashMap<String, String>();
//        temp3.put(FIRST_COLUMN, "01912314466");
//        temp3.put(SECOND_COLUMN, "1000");
//        temp3.put(THIRD_COLUMN, "bKash");
//        temp3.put(FOURTH_COLUMN, "Success");
//        historyList.add(temp3);
//
//    }






//    public void onClickButtonReportMenuBackListener(){
//        button_report_menu_back = (Button)findViewById(R.id.bHistoryMenuBack);
//        button_report_menu_back.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intentReportMenuBack = new Intent("com.example.sampanit.recharge.RechargeMenu");
//                        startActivity(intentReportMenuBack);
//                    }
//                }
//        );
//    }

    public void showBkashHistory()
    {
        try
        {
            final ProgressDialog progress = new ProgressDialog(History.this);
            progress.setTitle("Processing");
            progress.setMessage("Retrieving Bkash transaction list...");
            progress.show();
            Thread bkashThread = new Thread() {
                @Override
                public void run()
                {
                    try
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(baseUrl+"androidapp/transaction/get_bkash_transaction_list");

                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("user_id", userId+""));
                        nameValuePairs.add(new BasicNameValuePair("session_id", sessionId));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader
                                (new InputStreamReader(response.getEntity().getContent()));
                        String result = "";
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        if(result != null) {
                            JSONObject resultEvent = new JSONObject(result.toString());

                            int responseCode = 0;
                            try
                            {
                                responseCode = (int)resultEvent.get("response_code");
                            }
                            catch(Exception ex)
                            {
                                progress.dismiss();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(responseCode == 2000){
                                try
                                {
                                    JSONArray transactionList = resultEvent.getJSONArray("transaction_list");
                                    Intent intentbKash = new Intent(getBaseContext(), bKashHistory.class);
                                    intentbKash.putExtra("TRANSACTION_LIST", transactionList.toString());
                                    startActivity(intentbKash);
                                }
                                catch(Exception ex)
                                {
                                    progress.dismiss();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "Invalid response from the server..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }
                        else
                        {
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Invalid response from the server...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    catch (Exception ex) {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    progress.dismiss();
                }
            };
            bkashThread.start();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDBBLHistory()
    {
        try
        {
            final ProgressDialog progress = new ProgressDialog(History.this);
            progress.setTitle("Processing");
            progress.setMessage("Retrieving DBBL transaction list...");
            progress.show();
            Thread bkashThread = new Thread() {
                @Override
                public void run()
                {
                    try
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(baseUrl+"androidapp/transaction/get_dbbl_transaction_list");

                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("user_id", userId+""));
                        nameValuePairs.add(new BasicNameValuePair("session_id", sessionId));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader
                                (new InputStreamReader(response.getEntity().getContent()));
                        String result = "";
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        if(result != null) {
                            JSONObject resultEvent = new JSONObject(result.toString());

                            int responseCode = 0;
                            try
                            {
                                responseCode = (int)resultEvent.get("response_code");
                            }
                            catch(Exception ex)
                            {
                                progress.dismiss();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(responseCode == 2000){
                                try
                                {
                                    JSONArray transactionList = resultEvent.getJSONArray("transaction_list");
                                    Intent intentDBBL = new Intent(getBaseContext(), DBBLHistory.class);
                                    intentDBBL.putExtra("TRANSACTION_LIST", transactionList.toString());
                                    startActivity(intentDBBL);
                                }
                                catch(Exception ex)
                                {
                                    progress.dismiss();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "Invalid response from the server..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }
                        else
                        {
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Invalid response from the server...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    catch (Exception ex) {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    progress.dismiss();
                }
            };
            bkashThread.start();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showMcashHistory()
    {
        try
        {
            final ProgressDialog progress = new ProgressDialog(History.this);
            progress.setTitle("Processing");
            progress.setMessage("Retrieving Mcash transaction list...");
            progress.show();
            Thread bkashThread = new Thread() {
                @Override
                public void run()
                {
                    try
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(baseUrl+"androidapp/transaction/get_mcash_transaction_list");

                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("user_id", userId+""));
                        nameValuePairs.add(new BasicNameValuePair("session_id", sessionId));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader
                                (new InputStreamReader(response.getEntity().getContent()));
                        String result = "";
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        if(result != null) {
                            JSONObject resultEvent = new JSONObject(result.toString());

                            int responseCode = 0;
                            try
                            {
                                responseCode = (int)resultEvent.get("response_code");
                            }
                            catch(Exception ex)
                            {
                                progress.dismiss();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(responseCode == 2000){
                                try
                                {
                                    JSONArray transactionList = resultEvent.getJSONArray("transaction_list");
                                    Intent intentmCash = new Intent(getBaseContext(), mCashHistory.class);
                                    intentmCash.putExtra("TRANSACTION_LIST", transactionList.toString());
                                    startActivity(intentmCash);
                                }
                                catch(Exception ex)
                                {
                                    progress.dismiss();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "Invalid response from the server..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }
                        else
                        {
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Invalid response from the server...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    catch (Exception ex) {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    progress.dismiss();
                }
            };
            bkashThread.start();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showUcashHistory()
    {
        try
        {
            final ProgressDialog progress = new ProgressDialog(History.this);
            progress.setTitle("Processing");
            progress.setMessage("Retrieving Ucash transaction list...");
            progress.show();
            Thread bkashThread = new Thread() {
                @Override
                public void run()
                {
                    try
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(baseUrl+"androidapp/transaction/get_ucash_transaction_list");

                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("user_id", userId+""));
                        nameValuePairs.add(new BasicNameValuePair("session_id", sessionId));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader
                                (new InputStreamReader(response.getEntity().getContent()));
                        String result = "";
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        if(result != null) {
                            JSONObject resultEvent = new JSONObject(result.toString());

                            int responseCode = 0;
                            try
                            {
                                responseCode = (int)resultEvent.get("response_code");
                            }
                            catch(Exception ex)
                            {
                                progress.dismiss();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(responseCode == 2000){
                                try
                                {
                                    JSONArray transactionList = resultEvent.getJSONArray("transaction_list");
                                    Intent intentUCash = new Intent(getBaseContext(), UCashHistory.class);
                                    intentUCash.putExtra("TRANSACTION_LIST", transactionList.toString());
                                    startActivity(intentUCash);
                                }
                                catch(Exception ex)
                                {
                                    progress.dismiss();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "Invalid response from the server..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }
                        else
                        {
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Invalid response from the server...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    catch (Exception ex) {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    progress.dismiss();
                }
            };
            bkashThread.start();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showTopupHistory()
    {
        try
        {
            final ProgressDialog progress = new ProgressDialog(History.this);
            progress.setTitle("Processing");
            progress.setMessage("Retrieving Topup transaction list...");
            progress.show();
            Thread bkashThread = new Thread() {
                @Override
                public void run()
                {
                    try
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(baseUrl+"androidapp/transaction/get_topup_transaction_list");

                        List<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("user_id", userId+""));
                        nameValuePairs.add(new BasicNameValuePair("session_id", sessionId));

                        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = client.execute(post);
                        BufferedReader rd = new BufferedReader
                                (new InputStreamReader(response.getEntity().getContent()));
                        String result = "";
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        if(result != null) {
                            JSONObject resultEvent = new JSONObject(result.toString());

                            int responseCode = 0;
                            try
                            {
                                responseCode = (int)resultEvent.get("response_code");
                            }
                            catch(Exception ex)
                            {
                                progress.dismiss();
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getBaseContext(), "Invalid response from the server.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if(responseCode == 2000){
                                try
                                {
                                    JSONArray transactionList = resultEvent.getJSONArray("transaction_list");
                                    Intent intentTopUp = new Intent(getBaseContext(), TopUpHistory.class);
                                    intentTopUp.putExtra("TRANSACTION_LIST", transactionList.toString());
                                    startActivity(intentTopUp);
                                }
                                catch(Exception ex)
                                {
                                    progress.dismiss();
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getBaseContext(), "Invalid response from the server..", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }
                        else
                        {
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getBaseContext(), "Invalid response from the server...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    catch (Exception ex) {
                        progress.dismiss();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getBaseContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    progress.dismiss();
                }
            };
            bkashThread.start();
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
        }
    }
}
