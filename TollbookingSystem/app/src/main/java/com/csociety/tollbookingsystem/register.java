package com.csociety.tollbookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private Button reg;
    private ImageView banner;
    private EditText fullname,emailid,cemailid,mobile,vehicleno,drivingno,rpassword,rcpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        reg=(Button) findViewById(R.id.registerUser);
        reg.setOnClickListener(this);
        banner=(ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        fullname=(EditText) findViewById(R.id.fname);
        emailid=(EditText) findViewById(R.id.semail);
        cemailid=(EditText) findViewById(R.id.cemailId);
        mobile=(EditText) findViewById(R.id.mobile);
        vehicleno=(EditText) findViewById(R.id.vehicleno);
        drivingno=(EditText) findViewById(R.id.drivingno);
        rpassword=(EditText)findViewById(R.id.rpassword);
        rcpassword=(EditText)findViewById(R.id.rcpassword);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();

                break;

        }

    }

    private void registerUser() {
        String fname=fullname.getText().toString().trim();
        String email=emailid.getText().toString().trim();
        String cemail=cemailid.getText().toString().trim();
        String mobileno=mobile.getText().toString().trim();
        String vehicle=vehicleno.getText().toString().trim();
        String driving=drivingno.getText().toString().trim();
        String pass=rpassword.getText().toString().trim();
        String cpass=rcpassword.getText().toString().trim();
        String splittedname[]=fname.split(" ");
        String firstname=splittedname[0];
        String trimmednumber=mobileno.substring(6,10);
        String userid=firstname+trimmednumber;
        if(fname.isEmpty()){
            fullname.setError("Full name is required !");
            fullname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailid.setError("Full name is required !");
            emailid.requestFocus();
            return;
        }
        if(cemail.isEmpty()){
            cemailid.setError("Full name is required !");
            cemailid.requestFocus();
            return;
        }
        if(mobileno.isEmpty()){
            mobile.setError("Full name is required !");
            mobile.requestFocus();
            return;
        }
        if(vehicle.isEmpty()){
            vehicleno.setError("Full name is required !");
            vehicleno.requestFocus();
            return;
        }
        if(driving.isEmpty()){
            drivingno.setError("Full name is required !");
            drivingno.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            rpassword.setError("Full name is required !");
            rpassword.requestFocus();
            return;
        }
        if(cpass.isEmpty()){
            rcpassword.setError("Full name is required !");
            rcpassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailid.setError("Email is invalid");
            emailid.requestFocus();
            return;
        }
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

        if(!checkBox.isChecked()){
            checkBox.setError("Accept the terms and conditions");
            checkBox.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(fname,email,mobileno,vehicle,driving,userid);
                            String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            String uidd=uid.substring(1,5);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this,"User has been Registered Successfully",Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(register.this,"Failed, try again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(register.this,"Failed, try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        startActivity(new Intent(this,MainActivity.class));


    }
}