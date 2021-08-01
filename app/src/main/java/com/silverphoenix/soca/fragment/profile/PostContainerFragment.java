package com.silverphoenix.soca.fragment.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silverphoenix.soca.R;

import java.util.ArrayList;
import java.util.List;


public class PostContainerFragment extends Fragment {

    private List<PostModel> postList = new ArrayList<>();
    private  RecyclerView postRecyclerView;
    private PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_container, container, false);

        postRecyclerView = view.findViewById(R.id.profile_post_recycle_view);

        postRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        postList.add(new PostModel( R.drawable.img_20200926_152752_942,"bell"));
        postList.add(new PostModel(R.drawable.img_20201105_005457_560,"calander"));
        postList.add(new PostModel(R.drawable.i_chat,"chat"));
        postList.add(new PostModel(R.drawable.i_female,"female"));
        postList.add(new PostModel(R.drawable.aaddhar,"fire"));
        postList.add(new PostModel(R.drawable.i_flash,"flash"));

        postAdapter =new PostAdapter(view.getContext(), postList);
        postRecyclerView.setAdapter(postAdapter);


        return view;
    }
}