package com.example.sampanit.recharge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

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

        ListView listView = (ListView) findViewById(R.id.list_view_top_up_history);
        populateList();
        TopUpHistoryListViewAdapter adapter = new TopUpHistoryListViewAdapter(this, topUpHistoryList);
        listView.setAdapter(adapter);
    }
    private void populateList() {
        topUpHistoryList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Cell Number");
        temp.put(SECOND_COLUMN, "Amount");
        temp.put(THIRD_COLUMN, "Title");
        temp.put(FOURTH_COLUMN, "Status");
        topUpHistoryList.add(temp);

        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "01712314466");
        temp2.put(SECOND_COLUMN, "1000");
        temp2.put(THIRD_COLUMN, "Flexiload");
        temp2.put(FOURTH_COLUMN, "Success");
        topUpHistoryList.add(temp2);

        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put(FIRST_COLUMN, "01812314466");
        temp3.put(SECOND_COLUMN, "1000");
        temp3.put(THIRD_COLUMN, "Robiload");
        temp3.put(FOURTH_COLUMN, "Success");
        topUpHistoryList.add(temp3);

        HashMap<String, String> temp4 = new HashMap<String, String>();
        temp4.put(FIRST_COLUMN, "01912314466");
        temp4.put(SECOND_COLUMN, "1000");
        temp4.put(THIRD_COLUMN, "iTopUp");
        temp4.put(FOURTH_COLUMN, "Failed");
        topUpHistoryList.add(temp4);

        HashMap<String, String> temp5 = new HashMap<String, String>();
        temp5.put(FIRST_COLUMN, "01512314466");
        temp5.put(SECOND_COLUMN, "1000");
        temp5.put(THIRD_COLUMN, "TeleCharge");
        temp5.put(FOURTH_COLUMN, "Success");
        topUpHistoryList.add(temp5);

        HashMap<String, String> temp6 = new HashMap<String, String>();
        temp6.put(FIRST_COLUMN, "01512314466");
        temp6.put(SECOND_COLUMN, "1000");
        temp6.put(THIRD_COLUMN, "Recharge");
        temp6.put(FOURTH_COLUMN, "Success");
        topUpHistoryList.add(temp6);

    }
}
