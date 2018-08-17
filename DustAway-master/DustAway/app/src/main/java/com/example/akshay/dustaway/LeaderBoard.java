package com.example.akshay.dustaway;

import android.graphics.ColorSpace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderBoard extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNotifyDatabaseReference;

    private ChildEventListener mChildEventListener;

    private List<ModelUser> userList;
    ItemAdapter<ModelUser> itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        mRecyclerView = (RecyclerView) findViewById(R.id.pointsRecyclerView);
        userList = new ArrayList<>();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        itemAdapter = new ItemAdapter<>();
//create the managing FastAdapter, by passing in the itemAdapter
        mFirebaseDatabase=FirebaseDatabase.getInstance();

        mNotifyDatabaseReference=mFirebaseDatabase.getReference().child("cleaners");

        FastAdapter fastAdapter = FastAdapter.with(Arrays.asList(itemAdapter));

//set our adapters to the RecyclerView
        mRecyclerView.setAdapter(fastAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<ModelUser> user = new ArrayList<>();

        userList.add(new ModelUser("1234", "hello"));

        itemAdapter.setNewList(userList);



        mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                   userList.add( dataSnapshot.getValue(ModelUser.class));
                    Toast.makeText(getApplicationContext(), dataSnapshot.getValue(ModelUser.class).getUsername(), Toast.LENGTH_SHORT).show();
                    Collections.sort(userList, new Comparator<ModelUser>() {
                        @Override
                        public int compare(ModelUser modelUser, ModelUser t1) {
                            int i1 = Integer.valueOf(modelUser.getCash());
                            int i2 = Integer.valueOf(t1.getCash());

                            if(i1>i2)
                                return 1;
                            else
                                return -1;
                        }
                    });
                    itemAdapter.setNewList(userList);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    for(int i=0;i<userList.size();i++){
                        if(dataSnapshot.getValue(ModelUser.class).getUsername() == userList.get(i).getUsername()){
                            userList.remove(i);
                        }
                    }
                   userList.add(dataSnapshot.getValue(ModelUser.class));
                    Collections.sort(userList, new Comparator<ModelUser>() {
                        @Override
                        public int compare(ModelUser modelUser, ModelUser t1) {
                            int i1 = Integer.valueOf(modelUser.getCash());
                            int i2 = Integer.valueOf(t1.getCash());

                            if(i1>i2)
                                return 1;
                            else
                                return -1;
                        }
                    });
                    itemAdapter.setNewList(userList);



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


//set the items to your ItemAdapter]

}
