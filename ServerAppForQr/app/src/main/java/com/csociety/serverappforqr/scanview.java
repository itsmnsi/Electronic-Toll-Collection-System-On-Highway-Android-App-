package com.csociety.serverappforqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scanview extends AppCompatActivity implements ZXingScannerView.ResultHandler {
ZXingScannerView scannerView;
DatabaseReference reference;
public static String uid,tid,tokenid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView=new ZXingScannerView(this);
        setContentView(scannerView);

        reference=FirebaseDatabase.getInstance().getReference("Users");

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void handleResult(Result rawResult) {
    String rtext=rawResult.getText();
    String id[]=rtext.split(" ");
    uid=id[0];
    tid=id[1];

        reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);
                Token tokendetail=snapshot.child("Tokens").child(tid).getValue(Token.class);

                if(userProfile!=null && tokendetail!=null){
                    String fullname=userProfile.fname;
                    String email=userProfile.emailid;
                    String mobile=userProfile.mobile;
                    String carmodel=userProfile.drivingno;
                    String licence=userProfile.vehicleno;
                    String uids=userProfile.userid;

                    dashboard.uiname.setText("User Name: "+fullname);
                    dashboard.uiemail.setText("Email: "+email);
                    dashboard.uimobile.setText("Mobile: "+mobile);
                    dashboard.uicarmodel.setText("Car Model: "+carmodel);
                    dashboard.uilicence.setText("Licence No: "+licence);
                    dashboard.uiuserid.setText("User Id: "+uids);

                    String source=tokendetail.source;
                    String destination=tokendetail.destination;
                    String tokens=tokendetail.tokens;
                    String amount=tokendetail.amount;
                    String purchaseStatus=tokendetail.purchaseStatus;
                    Log.i("source",source);
                    tokenid=tokendetail.tokenid;

                    dashboard.uisource.setText("Source: "+source);
                    dashboard.uidestination.setText("Destination: "+destination);
                    dashboard.uitokens.setText("No. of Tokens Remaining: "+tokens);
                    dashboard.tokenbackup.setText(tokens);
                    dashboard.uiamountpaid.setText("Amount Paid: "+amount);
                    dashboard.uitokenstatus.setText("Purchase Status: "+purchaseStatus);
                    dashboard.uiexpire.setText("Expiry: "+"After "+tokens+" uses");

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(scanview.this, "Something wrong happened", Toast.LENGTH_SHORT).show();

            }
        });

    onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}