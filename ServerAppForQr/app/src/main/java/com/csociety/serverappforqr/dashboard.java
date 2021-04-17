package com.csociety.serverappforqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

public class dashboard extends AppCompatActivity {
Button scanbutton,viewdata;
ValueEventListener listener;
    DatabaseReference reference,treference;
    FirebaseUser user;
    public static String uid,tid;

public static TextView tokenbackup,uiname,uiemail,uiexpire,uitokens,uimobile,uicarmodel,uilicence,uisource,uidestination,uitokenstatus,uiamountpaid,uiuserid;
public static String info;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        reference = FirebaseDatabase.getInstance().getReference("Users");


        uiname = (TextView) findViewById(R.id.uiname);
        uiuserid = (TextView) findViewById(R.id.uiuserid);
        uimobile = (TextView) findViewById(R.id.uimobile);
        uicarmodel = (TextView) findViewById(R.id.uicarmodel);
        uilicence = (TextView) findViewById(R.id.uilicence);
        uisource = (TextView) findViewById(R.id.uisource);
        uidestination = (TextView) findViewById(R.id.uidestination);
        uitokenstatus = (TextView) findViewById(R.id.uitokenstatus);
        uiamountpaid = (TextView) findViewById(R.id.uiamountpaid);
        uitokens= (TextView)findViewById(R.id.uitokens);
        uiemail = (TextView) findViewById(R.id.uiemail);
        uiexpire=(TextView) findViewById(R.id.uiexpire);
        tokenbackup=(TextView)findViewById(R.id.tokenbackup);




        scanbutton = (Button) findViewById(R.id.scanuser);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashboard.this, scanview.class));
            }


        });

        Button opendoor;
        opendoor=(Button)findViewById(R.id.opendoor);
        opendoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=tokenbackup.getText().toString().trim();
                if(!key.equals("0")) {
                    reference.child(scanview.uid).child("Tokens").child(scanview.tid).child("tokens").setValue(tokenvalue());
                    //startActivity(new Intent(dashboard.this, MainActivity.class));
                    Toast.makeText(dashboard.this, "Token Used ,Boom Barrier Opened", Toast.LENGTH_SHORT).show();
                onBackPressed();
                }
                else {
                    reference.child(scanview.uid).child("Tokens").child(scanview.tid).removeValue();
                    Toast.makeText(dashboard.this, "This Token is Expired", Toast.LENGTH_SHORT).show();
                    onBackPressed();


                }
                }
        });





    }

String tokenvalue(){
    String n=tokenbackup.getText().toString().trim();

    int x= Integer.parseInt(n);
    if (x>0) {
        x -= 1;
    }else {
        return "null";
    }
    n=Integer.toString(x);
    return n;
}
}







