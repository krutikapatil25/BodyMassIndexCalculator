package com.future.bodymassindexproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CustomerLoginActivity extends AppCompatActivity {

    EditText etMainEmail, etMainPassword;
    Button btnMainSignIn, btnMainSignUp,btnForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        final FirebaseAuth firebaseAuth;

        etMainEmail = (EditText)findViewById(R.id.etMainEmail);
        etMainPassword = (EditText)findViewById(R.id.etMainPassword);
        btnMainSignIn = (Button)findViewById(R.id.btnMainSignIn);
        btnMainSignUp = (Button)findViewById(R.id.btnMainSignUp);
        btnForget = (Button)findViewById(R.id.btnForget);
        firebaseAuth = FirebaseAuth.getInstance();


        btnMainSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerLoginActivity.this, CustomerSignUpActivity.class);
                startActivity(i);
            }
        });

        btnMainSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = etMainEmail.getText().toString();
                if(e.length() == 0)
                {
                    etMainEmail.setError("Enter a valid email address");
                    etMainEmail.requestFocus();
                    return;
                }
                String p = etMainPassword.getText().toString();
                if(p.length() == 0)
                {
                    etMainPassword.setError("Enter a valid password");
                    etMainPassword.requestFocus();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(CustomerLoginActivity.this, "You have signed in successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CustomerLoginActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(CustomerLoginActivity.this, " issue :" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerLoginActivity.this,ForgetActivity.class);
                startActivity(i);
            }
        });
    }
}
