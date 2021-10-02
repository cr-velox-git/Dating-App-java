package com.silverphoenix.soca.fragment.chatLike;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silverphoenix.soca.DBQuery.DBQueries;
import com.silverphoenix.soca.R;


public class ContainerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_container, container, false);

        DBQueries.CURRENT_MAIN_FRAGMENT = DBQueries.CHAT_FRAGMENT;

        return view;
    }
}