package com.example.sampanit.recharge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sampanit.recharge.Constants.FIRST_COLUMN;
import static com.example.sampanit.recharge.Constants.SECOND_COLUMN;
import static com.example.sampanit.recharge.Constants.THIRD_COLUMN;

public class Payment extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> paymentHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String paymentTransactionList = getIntent().getExtras().getString("PAYMENT_TRANSACTION_LIST");

        ListView listView = (ListView) findViewById(R.id.list_view_payment_history);
        populateList(paymentTransactionList);
        PaymentHistoryListViewAdapter adapter = new PaymentHistoryListViewAdapter(this, paymentHistoryList);
        listView.setAdapter(adapter);
//        String paymentTransactionList = getIntent().getExtras().getString("PAYMENT_TRANSACTION_LIST");
//        ListView listView = (ListView) findViewById(R.id.list_view_payment_history);
//        paymentHistoryList = new ArrayList<HashMap<String, String>>();
//        populateList(paymentTransactionList);
//        PaymentHistoryListViewAdapter adapter = new PaymentHistoryListViewAdapter(this, paymentHistoryList);
//        listView.setAdapter(adapter);

    }
    private void populateList(String paymentTList) {
        //table header
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "User Name");
        temp.put(SECOND_COLUMN, "Date");
        temp.put(THIRD_COLUMN, "Status Type");
        paymentHistoryList.add(temp);
        try
        {
            JSONArray paymentTransactionArray = new JSONArray(paymentTList);
            for (int i = 0; i < paymentTransactionArray.length(); i++) {
                JSONObject transactionObject = paymentTransactionArray.getJSONObject(i);
                HashMap<String, String> temp2 = new HashMap<String, String>();
                temp2.put(FIRST_COLUMN, (String)transactionObject.get("user_name"));
                temp2.put(SECOND_COLUMN, (String)transactionObject.get("date"));
                temp2.put(THIRD_COLUMN, (String)transactionObject.get("status_type"));
                paymentHistoryList.add(temp2);
            }
        }
        catch(Exception ex)
        {

        }
    }
    /*private void populateList(String paymentTransactionList) {
        paymentHistoryList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "User Name");
        temp.put(SECOND_COLUMN, "Date");
        temp.put(THIRD_COLUMN, "Status Type");
        paymentHistoryList.add(temp);

        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "A.K.M. Nazmul Islam");
        temp2.put(SECOND_COLUMN, "16/07/2016");
        temp2.put(THIRD_COLUMN, "Failed");
        paymentHistoryList.add(temp2);

        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put(FIRST_COLUMN, "Nazmul Hasan");
        temp3.put(SECOND_COLUMN, "15/07/2016");
        temp3.put(THIRD_COLUMN, "Success");
        paymentHistoryList.add(temp3);

        HashMap<String, String> temp4 = new HashMap<String, String>();
        temp4.put(FIRST_COLUMN, "Alamgir Kabir");
        temp4.put(SECOND_COLUMN, "14/07/2016");
        temp4.put(THIRD_COLUMN, "Success");
        paymentHistoryList.add(temp4);
    }*/


}
