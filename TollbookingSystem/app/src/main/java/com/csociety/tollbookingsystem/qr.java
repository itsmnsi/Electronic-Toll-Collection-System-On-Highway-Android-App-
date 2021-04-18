package com.csociety.tollbookingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class qr extends AppCompatActivity {
    ImageView qrcode;

    Button qrbutton,remaining, download;
    TextView qremail,tokensremained;
    public static Spinner tokenSpinner;
    private FirebaseUser user;
    private DatabaseReference reference;
    public static String userID, selected;
    public static String email, info;
    String a[];




    public static String userid, username, driving, vehical, mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);


        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    userid = userProfile.userid;
                    username = userProfile.fname;
                    driving = userProfile.drivingno;
                    vehical = userProfile.vehicleno;
                    mobile = userProfile.mobile;
                    email = userProfile.emailid;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(qr.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });


        reference.child(userID).child("Tokens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> alltokens = new ArrayList<String>();

                for (DataSnapshot tokenSnapshot : dataSnapshot.getChildren()) {
                    String tokenkey = tokenSnapshot.getKey();
                    alltokens.add(tokenkey);
                }

                tokenSpinner = (Spinner) findViewById(R.id.spinner3);
                ArrayAdapter<String> tokenAdapter = new ArrayAdapter<String>(qr.this, android.R.layout.simple_spinner_item, alltokens);
                tokenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tokenSpinner.setAdapter(tokenAdapter);
                tokenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(qr.this, tokenSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                        setTokens();

                        qrs();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });









        qrcode = (ImageView) findViewById(R.id.qr);



    }
    public  void setTokens(){
        String sel=tokenSpinner.getSelectedItem().toString().trim();
        //Toast.makeText(this, sel, Toast.LENGTH_SHORT).show();

        reference.child(userID).child("Tokens").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Token tokendetail=snapshot.child(sel).getValue(Token.class);
                if( tokendetail!=null){
                    String tokens=tokendetail.tokens;
                    Toast.makeText(qr.this, "tokens", Toast.LENGTH_SHORT).show();

                    Toast.makeText(qr.this, tokens+ " remaining", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(qr.this, "failed", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

public void qrs(){
    selected=tokenSpinner.getSelectedItem().toString();
    String qrcodes= userID+" "+selected;
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    try{
        BitMatrix bitMatrix = multiFormatWriter.encode(qrcodes.trim(), BarcodeFormat.QR_CODE,600,600);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        qrcode.setImageBitmap(bitmap);
    }catch (Exception e){
        e.printStackTrace();
    }
}
}