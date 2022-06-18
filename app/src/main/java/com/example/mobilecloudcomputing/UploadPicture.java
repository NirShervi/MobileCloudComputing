package com.example.mobilecloudcomputing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class UploadPicture extends AppCompatActivity {
    private ImageView picture;
    private Button upload;
    private Button download;
    private static final int PICK_IMAGE_REQUEST =1;
    private Uri mImageUri;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private String imageName = "";
    private FirebaseStorage storage;
    private Uri selectedImage;
    private  StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);
        picture = findViewById(R.id.imagePicture);
        upload = findViewById(R.id.upload);
        download = findViewById(R.id.download);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        imageName= UUID.randomUUID().toString();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("Image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            picture.setImageURI(mImageUri);
        }
    }

    private String getFileExtansion(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        if (mImageUri!=null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtansion(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadPicture.this,"upload file success",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadPicture.this,"upload file fail",Toast.LENGTH_SHORT).show();
                }
            });

            //https://www.youtube.com/watch?v=lPfQN-Sfnjw
            Upload upload = new Upload();
        } else{
            Toast.makeText(this,"No file secleted",Toast.LENGTH_SHORT).show();
        }


    }

}