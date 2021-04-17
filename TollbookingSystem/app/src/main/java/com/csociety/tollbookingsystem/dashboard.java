package com.csociety.tollbookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class dashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PaymentResultListener {
    private Button logout;
    private ImageButton caltok;
    public TextView tokens, amount;
    private ImageButton paybtn;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button purchased;
    String email;
    String mobile;
    String name;
    FirebaseAuth mAuth ;
    DatabaseReference referenced;
    public static String s1,s2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        final Spinner[] spinner = {findViewById(R.id.spinner)};
        final Spinner[] spinner2 = {findViewById(R.id.spinner2)};
        String alltokens[];
        mAuth=FirebaseAuth.getInstance();

        Checkout.preload(getApplicationContext());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.numbers2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner[0].setAdapter(adapter);
        spinner[0].setOnItemSelectedListener(this);
        spinner2[0].setAdapter(adapter2);
        spinner2[0].setOnItemSelectedListener(this);

        //String s1=spinner[0].toString();
        // String s2=spinner2[0].toString();
        purchased=(Button)findViewById(R.id.purchased);
        purchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this,qr.class));
            }
        });


        tokens = (TextView) findViewById(R.id.tokens);
        amount = (TextView) findViewById(R.id.amount);
        caltok = (ImageButton) this.findViewById(R.id.CalTok);
        caltok.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //Toast.makeText(dashboard.this, "working", Toast.LENGTH_SHORT).show();
                //tokens.setText("3 Tokens");
                //amount.setText("Rs. 140");

                s1 = spinner[0].getSelectedItem().toString();
                s2 = spinner2[0].getSelectedItem().toString();
                Toast.makeText(dashboard.this, s1 + " to " + s2, Toast.LENGTH_SHORT).show();


                if ("Mumbai".equals(s1) && "Mumbai".equals(s2)) {
                    tokens.setText("0 Tokens");
                    amount.setText("0");
                }
                if ("Mumbai".equals(s1) && "Panvel".equals(s2)) {
                    tokens.setText("3 Tokens");
                    amount.setText("100");


                }
                if ("Mumbai".equals(s1) && "Thane".equals(s2)) {
                    tokens.setText("1 Tokens");
                    amount.setText("40");


                }
                if ("Mumbai".equals(s1) && "Pune".equals(s2)) {
                    tokens.setText("3 Tokens");
                    amount.setText("120");


                }
                if ("Mumbai".equals(s1) && "Ratnagiri".equals(s2)) {
                    tokens.setText("5 Tokens");
                    amount.setText("150");

                }
                if ("Panvel".equals(s1) && "Panvel".equals(s2)) {
                    tokens.setText("0 Tokens");
                    amount.setText("0");
                }
                if ("Panvel".equals(s1) && "Thane".equals(s2)) {
                    tokens.setText("2 Tokens");
                    amount.setText("80");
                }
                if ("Panvel".equals(s1) && "Pune".equals(s2)) {
                    tokens.setText("3 Tokens");
                    amount.setText("120");
                }
                if ("Panvel".equals(s1) && "Ratnagiri".equals(s2)) {
                    tokens.setText("4 Tokens");
                    amount.setText("140");

                }
                if ("Panvel".equals(s1) && "Mumbai".equals(s2)) {
                    tokens.setText("3 Tokens");
                    amount.setText("100");


                }
                if ("Thane".equals(s1) && "Thane".equals(s2)) {
                    tokens.setText("0 Tokens");
                }
                if ("Thane".equals(s1) && "Pune".equals(s2)) {
                    tokens.setText("4 Tokens");
                    amount.setText("150");


                }
                if ("Thane".equals(s1) && "Ratnagiri".equals(s2)) {
                    tokens.setText("6 Tokens");
                    amount.setText("180");

                }
                if ("Thane".equals(s1) && "Mumbai".equals(s2)) {
                    tokens.setText("1 Tokens");
                    amount.setText("40");


                }
                if ("Thane".equals(s1) && "Panvel".equals(s2)) {
                    tokens.setText("2 Tokens");
                    amount.setText("80");

                }
                if ("Pune".equals(s1) && "Thane".equals(s2)) {
                    tokens.setText("4 Tokens");
                    amount.setText("160");

                }
                if ("Pune".equals(s1) && "Pune".equals(s2)) {
                    tokens.setText("0 Tokens");
                    amount.setText("0");

                }
                if ("Pune".equals(s1) && "Ratnagiri".equals(s2)) {
                    tokens.setText("3 Tokens");
                    amount.setText("120");

                }
                if ("Pune".equals(s1) && "Mumbai".equals(s2)) {
                    tokens.setText("4 Tokens");
                    amount.setText("150");

                }
                if ("Pune".equals(s1) && "Panvel".equals(s2)) {
                    tokens.setText("4 Tokens");
                    amount.setText("160");

                }
                if ("Ratnagiri".equals(s1) && "Thane".equals(s2)) {
                    tokens.setText("6 Tokens");
                    amount.setText("180");


                }
                if ("Ratnagiri".equals(s1) && "Pune".equals(s2)) {
                    tokens.setText("4 Tokens");
                    amount.setText("160");

                }
                if ("Ratnagiri".equals(s1) && "Ratnagiri".equals(s2)) {
                    tokens.setText("0 Tokens");
                    amount.setText("0");
                }
                if ("Ratnagiri".equals(s1) && "Mumbai".equals(s2)) {
                    tokens.setText("6 Tokens");
                    amount.setText("180");

                }
                if ("Ratnagiri".equals(s1) && "Panvel".equals(s2)) {
                    tokens.setText("5 Tokens");
                    amount.setText("150");

                }
                if ("Select Source Location".equals(s1) || "Select Destination Location".equals(s2)) {
                    Toast.makeText(dashboard.this, " Please Choose both Source and Destination ", Toast.LENGTH_SHORT).show();

                }


            }
        });




        paybtn = (ImageButton) this.findViewById(R.id.rpay);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(dashboard.this, Payment.class));
                makepayment();

            }
        });



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).getKey();


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    email = userProfile.emailid;
                    mobile = userProfile.mobile;
                    name = userProfile.fname;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(dashboard.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(dashboard.this, MainActivity.class));
            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


//Payment

    public void makepayment() {
        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_uEBcQP0XhUIhsy");

        checkout.setImage(R.drawable.icon);


        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();
            String cost=amount.getText().toString().trim()+"00";
            options.put("name", name);
            options.put("description", "Project Name");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", cost);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", mobile);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        String uid=FirebaseAuth.getInstance().getUid();
        String source = s1;
        String destination = s2;
        String amounts= amount.getText().toString().trim();
        String tk=tokens.getText().toString().trim();
        String noToken[]=tk.split(" ");
        String token=noToken[0];
        String purchaseStatus="True";
        Random rand = new Random();
        int ran = rand.nextInt(100000);
        String ra= String.valueOf(ran);
        String tokenid=s1+"To"+s2+ra;

        Token tokens=new Token(uid,tokenid,source,destination,token,amounts,purchaseStatus);
        reference.child(userID).child("Tokens").child(tokenid).setValue(tokens);
        startActivity(new Intent(dashboard.this, qr.class));

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
    }






    private void sendTokendata() {



       }



}

