package com.csociety.tollbookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    private EditText femail;
    private Button resetbutton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        femail=(EditText) findViewById(R.id.forgotemail);
        resetbutton=(Button) findViewById(R.id.resetPass);
        auth=FirebaseAuth.getInstance();
        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email=femail.getText().toString().trim();
        if(email.isEmpty()){
            femail.setError("Email is Required");
            femail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            femail.setError("Please Provide Valid Email !!");
            femail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPassword.this, "Check Your Email to Reset your Password !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(forgotPassword.this, "Something Wrong Happened !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}