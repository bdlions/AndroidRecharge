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
import static com.bdlions.recharge.Constants.FOURTH_COLUMN;

public class DBBLHistory extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> dbblHistoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbblhistory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String transactionList = getIntent().getExtras().getString("TRANSACTION_LIST");

        ListView listView = (ListView) findViewById(R.id.list_view_dbbl_history);
        dbblHistoryList = new ArrayList<HashMap<String, String>>();
        populateList(transactionList);
        DBBLHistoryListViewAdapter DBBLadapter = new DBBLHistoryListViewAdapter(this, dbblHistoryList);
        listView.setAdapter(DBBLadapter);
    }

    private void populateList(String transactionList) {
        //table header
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Cell Number");
        temp.put(SECOND_COLUMN, "Amount");
        temp.put(THIRD_COLUMN, "Title");
        temp.put(FOURTH_COLUMN, "Status");
        dbblHistoryList.add(temp);
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
                dbblHistoryList.add(temp2);
            }
        }
        catch(Exception ex)
        {

        }
    }
}
