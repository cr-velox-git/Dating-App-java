package com.silverphoenix.soca.fragment.reel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silverphoenix.soca.R;

import java.util.ArrayList;
import java.util.List;

public class ReelFragment extends Fragment {

    private ViewPager2 videoViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_reel, container, false);

        videoViewPager = view.findViewById(R.id.videoViewPager);

        List<ReelVideoModel> reelVideoList = new ArrayList<>();
        reelVideoList.add(new ReelVideoModel("https://firebasestorage.googleapis.com/v0/b/caller-c99f5.appspot.com/o/video%2F8f45cc02d0b88d51e96b7f47de6ff8bd.mp4?alt=media&token=10b54803-a9f6-4863-9b98-37130f42ab93", "Video 1", "what is going on"));
        reelVideoList.add(new ReelVideoModel("https://firebasestorage.googleapis.com/v0/b/caller-c99f5.appspot.com/o/video%2Fed698288d03f65668bdf379983169710.mp4?alt=media&token=8c98e16b-c74e-4f06-ac7a-57abd97fac5e", "Videoooo", "dude"));
        reelVideoList.add(new ReelVideoModel("https://firebasestorage.googleapis.com/v0/b/caller-c99f5.appspot.com/o/video%2F8f45cc02d0b88d51e96b7f47de6ff8bd.mp4?alt=media&token=10b54803-a9f6-4863-9b98-37130f42ab93", "something like this", "comer here"));
        reelVideoList.add(new ReelVideoModel("https://firebasestorage.googleapis.com/v0/b/caller-c99f5.appspot.com/o/video%2Fed698288d03f65668bdf379983169710.mp4?alt=media&token=8c98e16b-c74e-4f06-ac7a-57abd97fac5e", "probabily", ""));

        ReelVideoAdapter adapter = new ReelVideoAdapter(reelVideoList);
        videoViewPager.setAdapter(adapter);


        return view;
    }
}