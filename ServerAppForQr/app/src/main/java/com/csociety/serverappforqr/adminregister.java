package com.csociety.serverappforqr;




        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.FirebaseDatabase;

public class adminregister extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button reg;
    private ImageView banner;
    private EditText fullname,emailid,cemailid,mobile,vehicleno,drivingno,rpassword,rcpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminregister);

        mAuth = FirebaseAuth.getInstance();
        reg=(Button) findViewById(R.id.registerUser);
        reg.setOnClickListener(this);
        banner=(ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        fullname=(EditText) findViewById(R.id.fname);
        emailid=(EditText) findViewById(R.id.semail);
        cemailid=(EditText) findViewById(R.id.cemailId);
        mobile=(EditText) findViewById(R.id.mobile);
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
        String pass=rpassword.getText().toString().trim();
        String cpass=rcpassword.getText().toString().trim();

        if(fname.isEmpty()){
            fullname.setError("Full name is required !");
            fullname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            emailid.setError("Email is required !");
            emailid.requestFocus();
            return;
        }
        if(cemail.isEmpty()){
            cemailid.setError("Email is required !");
            cemailid.requestFocus();
            return;
        }
        if(!email.equals(cemail)){
            cemailid.setError("Email Didn't Matched !");
            cemailid.requestFocus();
            return;
        }
        if(mobileno.isEmpty()){
            mobile.setError("Full name is required !");
            mobile.requestFocus();
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
        if(!pass.equals(cpass)){
            rcpassword.setError("password Didn't Matched !");
            rcpassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailid.setError("Email is invalid");
            emailid.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user=new User(fname,email,mobileno);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(adminregister.this,"User has been Registered Successfully",Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(adminregister.this,"Failed, try again!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(adminregister.this,"Failed!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        startActivity(new Intent(this,MainActivity.class));



    }
}