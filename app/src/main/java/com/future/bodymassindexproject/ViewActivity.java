package com.future.bodymassindexproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;



public class ViewActivity extends AppCompatActivity {

    TextView lvData;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        lvData = (TextView)findViewById(R.id.lvData);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        //lvData.setText("Date & Time: " + currentDateTimeString + "\n");

        String data = MainActivity.db.viewStudent();
        if(data.length() == 0)

            lvData.setText("No records to show");
        else {
            lvData.setText("Date & Time : " + currentDateTimeString + "\n" + data + "\n");
        }


        //Intent i = getIntent();

        //String weight = sp.getString("weight","");
        //String bmi = sp.getString("bmi","");



    }
}
