package com.example.akshay.dustaway;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.media.MediaCodecInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QRScanActivity extends AppCompatActivity {
    //View Objects
    private TextView nameTitle;
    private TextView textViewName;
    private final static String ID="-L8JOHhLatbhnt1lj63R";
    private ChildEventListener mChildEventListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNotifyDatabaseReference;

    //qr code scanner object
    private IntentIntegrator qrScan;
    String key = "-LK7XZ69M3GvaMLpndyD";
    int points = 0, i;

    ModelUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        //View objects
        textViewName = (TextView) findViewById(R.id.textViewName);
        nameTitle = (TextView) findViewById(R.id.nameTitle);

        textViewName.setVisibility(View.GONE);
        nameTitle.setVisibility(View.GONE);
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        mFirebaseDatabase= FirebaseDatabase.getInstance();
        mNotifyDatabaseReference=mFirebaseDatabase.getReference().child("akshay").child("points");


        if(mChildEventListener==null) {
            mChildEventListener = new ChildEventListener() {


                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    points = dataSnapshot.getValue(int.class);
                    Toast.makeText(getBaseContext(), String.valueOf(points), Toast.LENGTH_LONG).show();
                    Button b = (Button) findViewById(R.id.buttonScan);
                    b.setText(String.valueOf(points));
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            mNotifyDatabaseReference.addChildEventListener(mChildEventListener);

        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try{
                    i = Integer.valueOf(result.getContents());
                }catch(NumberFormatException ex){ // handle your exception
                    i = 0;
                }
                    points = i;
                    textViewName.setVisibility(View.VISIBLE);
                    textViewName.setText("Congratulations you earned " + String.valueOf(i) + " Points");
                    mNotifyDatabaseReference.setValue(i);
                    mNotifyDatabaseReference=mFirebaseDatabase.getReference().child("rahul").child("points");
                    mNotifyDatabaseReference.setValue(i);
                    /*
                    taskref.child("message").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        user = (ModelUser) snapshot.getValue();  //prints "Do you have data? You'll love Firebase."
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
*/

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setPoints(){
        cleaner Cleaner = new cleaner();
        Cleaner.setName("Akshay Kumar");
        Cleaner.setPoints(points + i);
        mNotifyDatabaseReference.child(key).setValue(Cleaner);
        Toast.makeText(this, "Here", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        detachDatabaseReadListener();

    }


    private void detachDatabaseReadListener() {
        if (mChildEventListener!=null){
            mNotifyDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener=null;

        }
    }



    public void ScanQR(View view) {
        qrScan.initiateScan();
    }
}
