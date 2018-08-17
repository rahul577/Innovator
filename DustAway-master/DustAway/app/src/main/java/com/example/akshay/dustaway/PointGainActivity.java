package com.example.akshay.dustaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PointGainActivity extends AppCompatActivity {
    TextView point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_gain);
        point = (TextView) findViewById(R.id.pointtextview);
        point.setText("Points Earned: 25000");
    }
}
