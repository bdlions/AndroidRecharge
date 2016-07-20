package com.example.sampanit.recharge;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.sampanit.recharge.Constants.FIRST_COLUMN;
import static com.example.sampanit.recharge.Constants.FOURTH_COLUMN;
import static com.example.sampanit.recharge.Constants.SECOND_COLUMN;
import static com.example.sampanit.recharge.Constants.THIRD_COLUMN;

public class bKashHistory extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> historyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_kash_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

                ListView listView = (ListView) findViewById(R.id.list_view_bKash_history);
        populateList();
        HistoryListViewAdapter adapter = new HistoryListViewAdapter(this, historyList);
        listView.setAdapter(adapter);
    }

    private void populateList() {
        historyList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "Cell No");
        temp.put(SECOND_COLUMN, "Amount");
        temp.put(THIRD_COLUMN, "Title");
        temp.put(FOURTH_COLUMN, "Status");
        historyList.add(temp);

        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "01912314466");
        temp2.put(SECOND_COLUMN, "1000");
        temp2.put(THIRD_COLUMN, "bKash");
        temp2.put(FOURTH_COLUMN, "Success");
        historyList.add(temp2);

        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put(FIRST_COLUMN, "01912314466");
        temp3.put(SECOND_COLUMN, "1000");
        temp3.put(THIRD_COLUMN, "bKash");
        temp3.put(FOURTH_COLUMN, "Success");
        historyList.add(temp3);

    }

}
