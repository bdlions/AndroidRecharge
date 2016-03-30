package com.example.sampanit.recharge;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class History extends AppCompatActivity {
    private static Button button_report_menu_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String  extraData = "" ;
        extraData = getIntent().getExtras().getString("EXTRA_TRANSACTION_LIST");
        if(extraData != ""){
            try {
                JSONArray jsonArray = new JSONArray(extraData);


                TableLayout t1;

                TableLayout tl = (TableLayout) findViewById(R.id.main_table);
                int length = jsonArray.length();
                TextView[] textArray = new TextView[length];
                TableRow[] tr_head = new TableRow[length];

                tr_head[0] = new TableRow(this);
                tr_head[0].setId(0);
                tr_head[0].setBackgroundColor(Color.WHITE);
                tr_head[0].setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, 1));

                textArray[0] = new TextView(this);
                textArray[0].setText("Amount");
                textArray[0].setTextColor(Color.BLUE);
                textArray[0].setPadding(5, 5, 5, 5);
                tr_head[0].addView(textArray[0]);

                textArray[0] = new TextView(this);
                textArray[0].setText("Mobile");
                textArray[0].setTextColor(Color.BLUE);
                textArray[0].setPadding(5, 5, 5, 5);
                tr_head[0].addView(textArray[0]);

                textArray[0] = new TextView(this);
                textArray[0].setText("Title");
                textArray[0].setTextColor(Color.BLUE);
                textArray[0].setPadding(5, 5, 5, 5);
                tr_head[0].addView(textArray[0]);

                tl.addView(tr_head[0], new TableLayout.LayoutParams(
                        GridLayout.LayoutParams.MATCH_PARENT,
                        GridLayout.LayoutParams.WRAP_CONTENT));

                for (int i = 0; i < length; i++) {


                    String transctionData = jsonArray.get(i).toString();
                    JSONObject transction = new JSONObject(transctionData.toString());

                    int cellNo = transction.getInt("cell_no");
                    String mobile = Integer.toString(cellNo);
                    int amount = transction.getInt("amount");
                    String userAmout = Integer.toString(amount);
                    String title = (String)transction.get("title");


                    //Create the tablerows
                    tr_head[i] = new TableRow(this);
                    tr_head[i].setId(i + 1);
                    tr_head[i].setBackgroundColor(Color.WHITE);
                    tr_head[i].setLayoutParams(new GridView.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT, 1));
                    // Here create the TextView dynamically

                    /*
                    ImageView imgView =(ImageView) findViewById(R.id.imgView);
                    Drawable drawable  = getResources().getDrawable(R.drawable.img);
                    imgView.setImageDrawable(drawable);


                    textArray[i] = new TextView(this);
                    textArray[i].setId(i + 110);
                    textArray[i].setTextColor(Color.BLUE);
                    textArray[i].setPadding(5, 5, 5, 5);
                    tr_head[i].addView(textArray[i]);
                    */


                    textArray[i] = new TextView(this);
                    textArray[i].setId(i + 111);
                    textArray[i].setText(userAmout);
                    textArray[i].setTextColor(Color.BLUE);
                    textArray[i].setPadding(5, 5, 5, 5);
                    tr_head[i].addView(textArray[i]);

                    textArray[i] = new TextView(this);
                    textArray[i].setId(i + 112);
                    textArray[i].setText(mobile);
                    textArray[i].setTextColor(Color.BLUE);
                    textArray[i].setPadding(5, 5, 5, 5);
                    tr_head[i].addView(textArray[i]);

                    textArray[i] = new TextView(this);
                    textArray[i].setId(i + 113);
                    textArray[i].setText(title);
                    textArray[i].setTextColor(Color.BLUE);
                    textArray[i].setPadding(5, 5, 5, 5);
                    tr_head[i].addView(textArray[i]);


                    tl.addView(tr_head[i], new TableLayout.LayoutParams(
                            GridLayout.LayoutParams.MATCH_PARENT,
                            GridLayout.LayoutParams.WRAP_CONTENT));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        onClickButtonReportMenuBackListener();

    }
    public void onClickButtonReportMenuBackListener(){
        button_report_menu_back = (Button)findViewById(R.id.bHistoryMenuBack);
        button_report_menu_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentReportMenuBack = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        startActivity(intentReportMenuBack);
                    }
                }
        );
    }
}
