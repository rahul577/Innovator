package com.example.akshay.dustaway;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class cameraActivity extends AppCompatActivity {

    public String ImageUri;
    String YourLocation;
    ImageView complaint;

    FloatingActionButton click,send,pick;
    Spinner spinner;

    int RC_PHOTO_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        complaint= (ImageView) findViewById(R.id.complaintImage);

        YourLocation = "https://www.google.co.in/maps/place/Chhota+Phool,+Punjab+140001/@30.9582271,76.4650176,13.19z/data=!4m5!3m4!1s0x390554e2a494c011:0x1bf6428043a26f7d!8m2!3d30.9626595!4d76.488118";


        click=findViewById(R.id.OpenCameraFAB);
        send=findViewById(R.id.floatingActionButtonsent);
        pick=findViewById(R.id.ImagePicker);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2500);




            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"municipal_corporation@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "I found the pile of garbage here");
                    intent.putExtra(Intent.EXTRA_TEXT, "Sir the following picture acts as a proof of the collected garbage in the region "+YourLocation);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(ImageUri));
                    startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            ImageUri = data.getData().toString();
            complaint.setImageURI(Uri.parse(ImageUri));
        }
    }
}
