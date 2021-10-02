package com.silverphoenix.soca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.silverphoenix.soca.DBQuery.DBQueries;
import com.silverphoenix.soca.fragment.chatLike.ContainerFragment;
import com.silverphoenix.soca.fragment.feed.FeedFragment;
import com.silverphoenix.soca.fragment.match.MatchingFragment;
import com.silverphoenix.soca.fragment.profile.ProfileFragment;
import com.silverphoenix.soca.fragment.reel.ReelFragment;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.chip_nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MatchingFragment()).commit();
        chipNavigationBar.setItemSelected(R.id.nav_matching,true);
        bottomMenu();

    }

    @SuppressLint("NonConstantResourceId")
    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.nav_feed:
                    fragment = new FeedFragment();
                    break;
                case R.id.nav_reels:
                    fragment = new ReelFragment();
                    break;
                case R.id.nav_matching:
                    fragment = new MatchingFragment();
                    break;
                    case R.id.nav_chats:
                    fragment = new ContainerFragment();
                    break;
                case R.id.nav_profile:
                    fragment = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragment();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setFragment();
    }

    private void setFragment(){
        Fragment fragment = null;
        switch (DBQueries.CURRENT_MAIN_FRAGMENT) {
            case DBQueries.FEED_FRAGMENT:
                fragment = new FeedFragment();
                chipNavigationBar.setItemSelected(R.id.nav_feed,true);
                break;
            case DBQueries.REEL_FRAGMENT:
                fragment = new ReelFragment();
                chipNavigationBar.setItemSelected(R.id.nav_reels,true);
                break;
            case DBQueries.MATCH_FRAGMENT:
                fragment = new MatchingFragment();
                chipNavigationBar.setItemSelected(R.id.nav_matching,true);
                break;
            case DBQueries.CHAT_FRAGMENT:
                fragment = new ContainerFragment();
                chipNavigationBar.setItemSelected(R.id.nav_chats,true);
                break;
            case DBQueries.PROFILE_FRAGMENT:
                fragment = new ProfileFragment();
                chipNavigationBar.setItemSelected(R.id.nav_profile,true);
                break;
        }

       // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }
}