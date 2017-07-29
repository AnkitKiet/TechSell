package com.techsell.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.techsell.R;
import com.techsell.ui.SnackBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 11/09/16.
 */
public class SellActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.edttitle)
    EditText edtTitle;
    @Bind(R.id.edtdesc)
    EditText edtDesc;
    @Bind(R.id.btnpost)
    Button btnPost;
    private ImageButton mSelectImage;
    private static final int GALLERY_REQUEST = 1;
    private Uri imageuri = null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellactivity);
        mSelectImage = (ImageButton) findViewById(R.id.imageButton);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.SellActivity);
        mProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference().child("Advertisements");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {
        final String title_val = edtTitle.getText().toString().trim();
        final String desc_val = edtDesc.getText().toString().trim();
        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && imageuri != null) {
            mProgress.setMessage("Posting... ");
            mProgress.show();
            StorageReference filepath = mStorage.child(imageuri.getLastPathSegment());
            filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid().toString();
                    DatabaseReference newPost = mDatabase.child("Advertisement").push();
                    /*newPost.child("user").setValue(userId);
                    newPost.child("title").setValue(title_val);
                    newPost.child("description").setValue(desc_val);
                    newPost.child("image").setValue(downloadUri.toString());
                    */
                    Map<String, Object> userid = new HashMap<String, Object>();
                    userid.put("user", userId);
                    userid.put("title",title_val);
                    userid.put("description",desc_val);
                    userid.put("image",downloadUri.toString());
                    newPost.updateChildren(userid);
                    Toast.makeText(SellActivity.this, "Done :)", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SellActivity.this, DashboardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    SnackBar.show(getApplicationContext(), "Sorry! Please try Again");
                }
            });
        }
        else {
            SnackBar.show(SellActivity.this,"Enter Details");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageuri = data.getData();
            mSelectImage.setImageURI(imageuri);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}