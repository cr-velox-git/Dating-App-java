package com.silverphoenix.soca;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

public class UploadPostActivity extends AppCompatActivity {

    private ImageView moveBack, moveForward, imageHolder;
    private VideoView videoHolder;
    private Button uploadBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        moveBack = findViewById(R.id.up_move_back);
        moveForward = findViewById(R.id.up_move_forward);
        imageHolder = findViewById(R.id.up_image_holder);
        videoHolder = findViewById(R.id.up_video_holder);

        imageHolder.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // " M " stands for marshmallow
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);  // creating a intent to go in gallery of device
                    galleryIntent.setType("image/*"); // we are choosing what type of pick we will use "image" is type and " * " means all item
                    startActivityForResult(galleryIntent, 1);
                } else { // if we didn't get permission we have to request for it
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);  // creating a intent to go in gallery of device
                galleryIntent.setType("image/*"); // we are choosing what type of pick we will use "image" is type and " * " means all item
                startActivityForResult(galleryIntent, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri imageUri = data.getData();  // here we are getting the image uri
                    Glide.with(this).asBitmap().optionalFitCenter().load(imageUri).optionalCenterCrop().into(imageHolder);
                    // Glide.with(getContext()).load(imageUri).into(imageView);
                } else {
                    Toast.makeText(this, "Image not found!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);  // creating a intent to go in gallery of device
                galleryIntent.setType("image/*"); // we are choosing what type of pick we will use "image" is type and " * " means all item
                startActivityForResult(galleryIntent, 1);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPicture(Uri Uri) {



    }

}