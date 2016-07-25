package com.bdlions.recharge;

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

import static com.bdlions.recharge.Constants.FIRST_COLUMN;
import static com.bdlions.recharge.Constants.SECOND_COLUMN;
import static com.bdlions.recharge.Constants.THIRD_COLUMN;

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
        paymentHistoryList = new ArrayList<HashMap<String, String>>();
        populateList(paymentTransactionList);
        PaymentHistoryListViewAdapter adapter = new PaymentHistoryListViewAdapter(this, paymentHistoryList);
        listView.setAdapter(adapter);

    }
    private void populateList(String paymentTList) {
        //table header
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Amount");
        temp.put(SECOND_COLUMN, "Date");
        temp.put(THIRD_COLUMN, "Status");
        paymentHistoryList.add(temp);
        try
        {
            JSONArray paymentTransactionArray = new JSONArray(paymentTList);
            for (int i = 0; i < paymentTransactionArray.length(); i++) {
                JSONObject transactionObject = paymentTransactionArray.getJSONObject(i);
                HashMap<String, String> temp2 = new HashMap<String, String>();
                temp2.put(FIRST_COLUMN, transactionObject.getDouble("amount")+"");
                temp2.put(SECOND_COLUMN, (String)transactionObject.get("date"));
                temp2.put(THIRD_COLUMN, (String)transactionObject.get("status"));
                paymentHistoryList.add(temp2);
            }
        }
        catch(Exception ex)
        {

        }
    }

}
