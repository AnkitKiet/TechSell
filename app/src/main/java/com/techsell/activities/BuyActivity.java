package com.techsell.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techsell.R;
import com.techsell.adapter.BuyRecyclerAdapter;
import com.techsell.dto.BuyDto;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 12/09/16.
 */
public class BuyActivity extends AppCompatActivity {

    @Bind(R.id.advertise_list)
    RecyclerView advertiselist;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private BuyRecyclerAdapter adapter;
    private List<BuyDto> listbuy = new ArrayList<>();
    private ProgressDialog mProgress;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        mProgress = new ProgressDialog(this);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.BuyActivity);
        mAuth = FirebaseAuth.getInstance();
        populate();

    }


    private void populate() {
        mProgress.setMessage("Retrieving... ");
        mProgress.show();

        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Advertisement").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                listbuy.removeAll(listbuy);

                for (DataSnapshot k : dataSnapshot.getChildren()) {
                    BuyDto obj = new BuyDto();
                    System.out.println("Desc:" + k.child("description").getValue());
                    obj.setTitle(k.child("title").getValue().toString());
                    obj.setDescription(k.child("description").getValue().toString());
                    obj.setImage(k.child("image").getValue().toString());
                    listbuy.add(obj);
                }
                System.out.print(listbuy);
                mProgress.dismiss();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BuyActivity.this, "Sorry!! try again", Toast.LENGTH_SHORT).show();
                mProgress.dismiss();
            }
        });
        adapter = new BuyRecyclerAdapter(BuyActivity.this, listbuy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BuyActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        advertiselist.setLayoutManager(layoutManager);
        advertiselist.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        populate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(BuyActivity.this,DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}


