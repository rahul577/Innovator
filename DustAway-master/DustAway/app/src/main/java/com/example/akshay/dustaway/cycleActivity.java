package com.example.akshay.dustaway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.DetectedActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class cycleActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private String TAG = cycleActivity.class.getSimpleName();
    BroadcastReceiver broadcastReceiver;

    private TextView txtActivity, txtConfidence;
    private ImageView imgActivity;
    private Button btnStartTrcking, btnStopTracking;
    public Chronometer mChronometer;
    public long timeWhenStopped = 0;
    public TextToSpeech tts;
    long start, runTime = 0;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNotifyDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);

        tts = new TextToSpeech(cycleActivity.this, this);
        tts.setLanguage(Locale.US);

        mFirebaseDatabase= FirebaseDatabase.getInstance();
        mNotifyDatabaseReference=mFirebaseDatabase.getReference().child("akshay").child("points");

        txtActivity = findViewById(R.id.txt_activity);
        txtConfidence = findViewById(R.id.txt_confidence);
        imgActivity = findViewById(R.id.img_activity);
        btnStartTrcking = findViewById(R.id.btn_start_tracking);
        btnStopTracking = findViewById(R.id.btn_stop_tracking);

        btnStartTrcking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTracking();
            }
        });

        btnStopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTracking();
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }
        };

        mChronometer = (Chronometer) findViewById(R.id.chronometer); // initiate a chronometer

    }

    private void handleUserActivity(int type, int confidence) {
        String label = getString(R.string.activity_unknown);
        int icon = R.drawable.ic_still;

        switch (type) {
            case DetectedActivity.IN_VEHICLE: {
                label = getString(R.string.activity_in_vehicle);
                icon = R.drawable.ic_driving;
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                label = getString(R.string.activity_on_bicycle);
                icon = R.drawable.ic_on_bicycle;
                break;
            }
            case DetectedActivity.ON_FOOT: {
                label = getString(R.string.activity_on_foot);
                icon = R.drawable.ic_walking;
                break;
            }
            case DetectedActivity.RUNNING: {
                label = getString(R.string.activity_running);
                icon = R.drawable.ic_running;
                break;
            }
            case DetectedActivity.STILL: {
                label = getString(R.string.activity_still);
                break;
            }
            case DetectedActivity.TILTING: {
                label = getString(R.string.activity_tilting);
                icon = R.drawable.ic_tilting;
                break;
            }
            case DetectedActivity.WALKING: {
                label = getString(R.string.activity_walking);
                icon = R.drawable.ic_walking;
                break;
            }
            case DetectedActivity.UNKNOWN: {
                label = getString(R.string.activity_unknown);
                break;
            }
        }

        if(DetectedActivity.STILL == type )
        {
            long start = System.currentTimeMillis();
        }

        else
        {
            int timeElapsed = (int) (SystemClock.elapsedRealtime() - mChronometer.getBase());
            int hours = (int) (timeElapsed / 3600000);
            int minutes = (int) (timeElapsed - hours * 3600000) / 60000;
            int seconds = (int) (timeElapsed - hours * 3600000 - minutes * 60000) / 1000;
            long runTime = System.currentTimeMillis() - start;
            hours = (int) (runTime / 3600000);
            minutes = (int) (runTime - hours * 3600000) / 60000;
            seconds = (int) (runTime - hours * 3600000 - minutes * 60000) / 1000;
            String s = "You are still for " + Integer.toString(hours) + " hours " + Integer.toString(minutes) + "minutes and " +  Integer.toString(seconds) + " seconds.";
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
            mChronometer.stop();
        }

        Log.e(TAG, "User activity: " + label + ", Confidence: " + confidence);

        if (confidence > Constants.CONFIDENCE) {
            txtActivity.setText(label);
            txtConfidence.setText("Confidence: " + confidence);
            imgActivity.setImageResource(icon);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.BROADCAST_DETECTED_ACTIVITY));
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void startTracking() {
        Intent intent = new Intent(cycleActivity.this, BackgroundDetectedActivitiesService.class);
        startService(intent);
        start = System.currentTimeMillis();
        mChronometer.start();
    }

    private void stopTracking() {
        runTime += System.currentTimeMillis() - start;
        mNotifyDatabaseReference.setValue(700);
        Toast.makeText(this, "Congrats you earned 100 points.", Toast.LENGTH_LONG).show();
        int hours = (int) (runTime / 3600000);
        int minutes = (int) (runTime - hours * 3600000) / 60000;
        int seconds = (int) (runTime - hours * 3600000 - minutes * 60000) / 1000;
        String s = "You are still for " + Integer.toString(hours) + " hours " + Integer.toString(minutes) + " minutes and " +  Integer.toString(seconds) + " seconds.";
        Intent intent = new Intent(cycleActivity.this, BackgroundDetectedActivitiesService.class);
        stopService(intent);
        tts.speak(s, TextToSpeech.QUEUE_ADD, null);
        mChronometer.stop();
        timeWhenStopped = 0;
    }

    @Override
    public void onInit(int i) {

    }
}
