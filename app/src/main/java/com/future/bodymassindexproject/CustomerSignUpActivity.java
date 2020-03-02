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


public class CustomerSignUpActivity extends AppCompatActivity {

    EditText etSignEmail,etSignPassword;
    Button btnSignRegister;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);

        etSignEmail = (EditText)findViewById(R.id.etSignEmail);
        etSignPassword = (EditText)findViewById(R.id.etSignPassword);
        btnSignRegister = (Button)findViewById(R.id.btnSignRegister);
        firebaseAuth = FirebaseAuth.getInstance();

        btnSignRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e = etSignEmail.getText().toString();
                if(e.length() == 0)
                {
                    etSignEmail.setError("Enter a valid email address");
                    etSignEmail.requestFocus();
                    return;
                }
                String p = etSignPassword.getText().toString();
                if(p.length() == 0)
                {
                    etSignPassword.setError("Enter a valid email address");
                    etSignPassword.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(CustomerSignUpActivity.this, "You have registered successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CustomerSignUpActivity.this,CustomerLoginActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(CustomerSignUpActivity.this, " issue :" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
