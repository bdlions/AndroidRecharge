package com.example.sampanit.recharge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sampanit.recharge.Constants.FIRST_COLUMN;
import static com.example.sampanit.recharge.Constants.FOURTH_COLUMN;
import static com.example.sampanit.recharge.Constants.SECOND_COLUMN;
import static com.example.sampanit.recharge.Constants.THIRD_COLUMN;

public class TopUpHistory extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> topUpHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String transactionList = getIntent().getExtras().getString("TRANSACTION_LIST");

        ListView listView = (ListView) findViewById(R.id.list_view_top_up_history);
        topUpHistoryList = new ArrayList<HashMap<String, String>>();
        populateList(transactionList);
        TopUpHistoryListViewAdapter adapter = new TopUpHistoryListViewAdapter(this, topUpHistoryList);
        listView.setAdapter(adapter);
    }
    private void populateList(String transactionList) {

        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Cell Number");
        temp.put(SECOND_COLUMN, "Amount");
        temp.put(THIRD_COLUMN, "Title");
        temp.put(FOURTH_COLUMN, "Status");
        topUpHistoryList.add(temp);

        try
        {
            JSONArray transactionArray = new JSONArray(transactionList);
            for (int i = 0; i < transactionArray.length(); i++) {
                JSONObject transactionObject = transactionArray.getJSONObject(i);
                HashMap<String, String> temp2 = new HashMap<String, String>();
                temp2.put(FIRST_COLUMN, (String)transactionObject.get("cell_no"));
                temp2.put(SECOND_COLUMN, transactionObject.getDouble("amount") + "");
                temp2.put(THIRD_COLUMN, (String)transactionObject.get("title"));
                temp2.put(FOURTH_COLUMN, (String)transactionObject.get("status"));
                topUpHistoryList.add(temp2);
            }
        }
        catch(Exception ex)
        {

        }

    }
}
