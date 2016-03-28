package com.example.sampanit.recharge;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout.LayoutParams;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Report extends AppCompatActivity {
    private static Button button_history_menu_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*TableLayout t1;

        TableLayout tl = (TableLayout) findViewById(R.id.main_table);
        TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);        // part1
        tr_head.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));


        TextView label_hello = new TextView(this);
        label_hello.setId(20);
        label_hello.setText("HELLO");
        label_hello.setTextColor(Color.WHITE);          // part2
        label_hello.setPadding(5, 5, 5, 5);
        tr_head.addView(label_hello);// add the column to the table row here

        TextView label_android = new TextView(this);    // part3
        label_android.setId(21);// define id that must be unique
        label_android.setText("ANDROID..!!"); // set the text for the header
        label_android.setTextColor(Color.WHITE); // set the color
        label_android.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_android); // add the column to the table row here

        tl.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,                    //part4
                LayoutParams.FILL_PARENT));*/

        TableLayout t1;

        TableLayout tl = (TableLayout) findViewById(R.id.main_table);
        int length = 2;
        TextView[] textArray = new TextView[length];
        TableRow[] tr_head = new TableRow[length];

        for (int i = 0; i < length; i++) {

            String productData = "product";
            String productDescription = "description";

//Create the tablerows
            tr_head[i] = new TableRow(this);
            tr_head[i].setId(i + 1);
            tr_head[i].setBackgroundColor(Color.GRAY);
            tr_head[i].setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            // Here create the TextView dynamically

            textArray[i] = new TextView(this);
            textArray[i].setId(i + 112);
            textArray[i].setText(productData);
            textArray[i].setTextColor(Color.WHITE);
            textArray[i].setPadding(5, 5, 5, 5);
            tr_head[i].addView(textArray[i]);

            textArray[i] = new TextView(this);
            textArray[i].setId(i + 111);
            textArray[i].setText(productDescription);
            textArray[i].setTextColor(Color.WHITE);
            textArray[i].setPadding(5, 5, 5, 5);
            tr_head[i].addView(textArray[i]);



            tl.addView(tr_head[i], new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

        } // end of for loop


            onClickButtonHistoryMenuBackListener();


        }

    public void onClickButtonHistoryMenuBackListener(){
        button_history_menu_back = (Button)findViewById(R.id.bReportMenuBack);
        button_history_menu_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentHistoryMenuBack = new Intent("com.example.sampanit.recharge.RechargeMenu");
                        startActivity(intentHistoryMenuBack);
                    }
                }
        );
    }
}
