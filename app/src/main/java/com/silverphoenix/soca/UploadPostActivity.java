package com.silverphoenix.soca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.VideoView;

public class UploadPostActivity extends AppCompatActivity {

    private ImageView moveBack, moveForward, imageHolder;
    private VideoView videoHolder;
    private Spinner galleryFolderSpinner;
    private RecyclerView imageHolderRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        moveBack = findViewById(R.id.up_move_back);
        moveForward = findViewById(R.id.up_move_forward);
        imageHolder = findViewById(R.id.up_image_holder);
        videoHolder = findViewById(R.id.up_video_holder);
        galleryFolderSpinner = findViewById(R.id.up_gallery_spinner);
        imageHolderRecycleView = findViewById(R.id.up_all_recycle_view);

    }
}