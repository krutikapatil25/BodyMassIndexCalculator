package com.future.bodymassindexproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;



public class ResultActivity extends AppCompatActivity {

    TextView tvResult;
    ImageView iv2;
    Button btnShare, btnLogout, btnSave;
    String msg;
    SharedPreferences sp;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = (TextView)findViewById(R.id.tvResult);
        iv2 = (ImageView)findViewById(R.id.iv2);
        btnShare = (Button)findViewById(R.id.btnShare);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        sp = getSharedPreferences("p1", MODE_PRIVATE);
        firebaseAuth = FirebaseAuth.getInstance();


        Intent i = getIntent();
        float bmi = i.getFloatExtra("bmi",0);


        if(bmi < 18.5) {
            tvResult.setTextColor(Color.RED);
            tvResult.setText("Your bmi is " + bmi +"\n" + "You are UnderWeight");
            msg = "I am Underweight";
        }
        else if(bmi > 18.5 && bmi < 25) {
            tvResult.setTextColor(Color.GREEN);
            tvResult.setText("Your bmi is " + bmi +"\n" + "You are Normal");
            msg = "I am Normal";
        }
        else if(bmi > 25 && bmi < 30) {
            tvResult.setTextColor(Color.RED);
            tvResult.setText("Your bmi is " + bmi +"\n" + "You are OverWeight");
            msg = "I am Overweight";
        }
        else {
            tvResult.setTextColor(Color.BLUE);
            tvResult.setText("Your bmi is " + bmi +"\n" + "You are Obese");
            msg = "I am Obese";
        }


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                Intent i = new Intent(ResultActivity.this,CustomerLoginActivity.class);
                startActivity(i);
            }
        });



        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = sp.getString("name","");
                String sage = sp.getString("age","");
                String weight = sp.getString("weight","");
                String bmi = sp.getString("bmi","");


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,"Name : " + name + "\n"  + "Age : " + sage + "\n" + "Weight : "
                        + weight + "\n" + "BMI : " + bmi + "\n" +  msg);
                i.putExtra(Intent.EXTRA_SUBJECT, "BODY MASS INDEX");

                Intent c = Intent.createChooser(i,"Complete action using");
                startActivity(c);


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = sp.getString("name","");
                String sage = sp.getString("age","");
                String weight = sp.getString("weight","");
                String bmi = sp.getString("bmi","");

                MainActivity.db.addData(name,sage,weight,bmi);



            }
        });

    }


}
