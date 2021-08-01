package com.silverphoenix.soca.fragment.profile;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.silverphoenix.soca.R;
import com.silverphoenix.soca.fragment.reel.ReelFragment;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    private ChipNavigationBar chipNavigationBar;
    private ImageView uploadOptionBtn, menuBtn;
    private Dialog uploadOptionDialog, menuDialog;
    private LinearLayout otherAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        chipNavigationBar = view.findViewById(R.id.chipNavigationBar_profile);
        uploadOptionBtn = view.findViewById(R.id.fp_upload_option);
        menuBtn = view.findViewById(R.id.fp_menu);
        otherAction = view.findViewById(R.id.lph_other_action);
        otherAction.setVisibility(View.GONE);

        /*..............................option dialog..............................*/


        uploadOptionDialog = new Dialog(getContext());
        uploadOptionDialog.setContentView(R.layout.dialog_upload_options);
        uploadOptionDialog.setCancelable(true);
        uploadOptionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        uploadOptionDialog.getWindow().setBackgroundDrawableResource(R.drawable.solid_3);
        uploadOptionDialog.getWindow().setGravity(Gravity.BOTTOM);

        LinearLayout feedPostBtn = uploadOptionDialog.findViewById(R.id.uo_feed_post);
        LinearLayout reelsBtn = uploadOptionDialog.findViewById(R.id.uo_reels);
        LinearLayout storyBtn = uploadOptionDialog.findViewById(R.id.uo_story);

        feedPostBtn.setOnClickListener(v->{
            Toast.makeText(getContext(), "open upload Feed Action", Toast.LENGTH_SHORT).show();
        });

        reelsBtn.setOnClickListener(v->{
            Toast.makeText(getContext(), "open upload Reel Action", Toast.LENGTH_SHORT).show();
        });

        storyBtn.setOnClickListener(v->{
            Toast.makeText(getContext(), "open upload Story Action", Toast.LENGTH_SHORT).show();
        });

        uploadOptionBtn.setOnClickListener(v -> uploadOptionDialog.show());


        /*..............................option dialog..............................*/

        /*..............................menu dialog..............................*/


        menuDialog = new Dialog(getContext());
        menuDialog.setContentView(R.layout.dialog_menu_options);
        menuDialog.setCancelable(true);
        menuDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuDialog.getWindow().setBackgroundDrawableResource(R.drawable.solid_3);
        menuDialog.getWindow().setGravity(Gravity.BOTTOM);

        LinearLayout settingBtn = menuDialog.findViewById(R.id.mo_setting);
        LinearLayout likePostBtn = menuDialog.findViewById(R.id.mo_like_post);
        LinearLayout savePostBtn = menuDialog.findViewById(R.id.mo_save_post);

        settingBtn.setOnClickListener(v->{
            Toast.makeText(getContext(), "open setting Action", Toast.LENGTH_SHORT).show();
        });

        likePostBtn.setOnClickListener(v->{
            Toast.makeText(getContext(), "open like post Action", Toast.LENGTH_SHORT).show();
        });

        savePostBtn.setOnClickListener(v->{
            Toast.makeText(getContext(), "open Save post Action", Toast.LENGTH_SHORT).show();
        });


        menuBtn.setOnClickListener(v -> menuDialog.show());


        /*..............................menu dialog..............................*/


        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profile_relative_layout, new PostContainerFragment()).commit();
        chipNavigationBar.setItemSelected(R.id.profile_feed, true);

        navController();
        return view;
    }

    private void navController() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                if (i == R.id.profile_feed) {
                    fragment = new PostContainerFragment();
                } else if (i == R.id.profile_reels) {
                    fragment = new ReelContainerFragment();
                }
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profile_relative_layout, fragment).commit();

            }
        });
    }


}