package com.future.bodymassindexproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    TextView tvDetails;
    EditText etName,etNumber,etAge;
    Button btnRegister;
    RadioGroup rgGender;
    SharedPreferences sp;
    static MyDbHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDetails = (TextView)findViewById(R.id.tvDetails);
        etName = (EditText)findViewById(R.id.etName);
        etNumber = (EditText)findViewById(R.id.etNumber);
        etAge = (EditText)findViewById(R.id.etAge);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        rgGender = (RadioGroup)findViewById(R.id.rgGender);
        db = new MyDbHandler(this);

        sp = getSharedPreferences("p1", MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                if(name.length() == 0)
                {
                    etName.setError("Enter name");
                    etName.requestFocus();
                    return;
                }
                String age = etAge.getText().toString();
                if(age.length() == 0)
                {
                    etAge.setError("Enter age");
                    etAge.requestFocus();
                    return;
                }
                String number = etNumber.getText().toString();
                if(number.length() != 10)
                {
                    etNumber.setError("Enter valid number");
                    etNumber.requestFocus();
                    return;
                }


                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", name);
                editor.putString("age",age);
                editor.putString("number",number);
                editor.commit();


                Toast.makeText(MainActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this,CalculateActivity.class);
                i.putExtra("name",name);
                startActivity(i);
                finish();

            }
        });


    }
}
