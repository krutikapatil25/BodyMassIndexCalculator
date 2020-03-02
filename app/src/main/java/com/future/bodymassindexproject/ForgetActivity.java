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
import com.google.firebase.auth.FirebaseAuth;



public class ForgetActivity extends AppCompatActivity {

    EditText etForgetEmail;
    Button btnSubmit;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        etForgetEmail = (EditText)findViewById(R.id.etForgetEmail);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        firebaseAuth = FirebaseAuth.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = etForgetEmail.getText().toString();
                if(em.length() == 0)
                {
                    etForgetEmail.setError("Enter a valid email address");
                    etForgetEmail.requestFocus();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetActivity.this, "Password reset link is sent to your registered email-id. Please go to the link and try sign-in again.", Toast.LENGTH_LONG).show();

                            Intent i = new Intent(ForgetActivity.this,CustomerLoginActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(ForgetActivity.this, "issue:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });
    }
}
