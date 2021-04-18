package com.csociety.serverappforqr;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private Button regi;
    private Button forgot;
    private Button signIn;
    private FirebaseAuth mAuth;

    private TextView reg,forgotpass;
    private EditText semail,rpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        reg = (TextView) findViewById(R.id.regis);
        reg.setOnClickListener(this);
        forgotpass =(TextView) findViewById(R.id.forgotpass);
        forgotpass.setOnClickListener(this);
        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        semail=(EditText) findViewById(R.id.semail);
        rpassword=(EditText)findViewById(R.id.rpassword);
        mAuth=FirebaseAuth.getInstance();



    }


    public void register(View v) {
        //
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.regis:
                startActivity(new Intent(this,adminregister.class));
                break;

            case R.id.signIn:
                userLogin();
                break;
            case R.id.forgotpass:
                startActivity(new Intent(MainActivity.this,forgotPassword.class));
                break;

        }

    }

    private void userLogin() {
        String email=semail.getText().toString().trim();
        String password=rpassword.getText().toString().trim();

        if(email.isEmpty()){
            semail.setError("Email is required !");
            semail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            rpassword.setError("Password is required !");
            rpassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            semail.setError("Email is invalid");
            semail.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, dashboard.class));
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "failed to login wrong credential !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}