package com.silverphoenix.soca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silverphoenix.soca.signUpLogin.LoginActivity;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView noInternet;
    Animation in_bottomAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(TAG, "SplashActivity started");

        in_bottomAnim = AnimationUtils.loadAnimation(this, R.anim.in_bottom_anim);
        noInternet = findViewById(R.id.no_internet);

    }

    private void checkInternetConnection(int delay) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (networkInfo != null && networkInfo.isConnected()) {
                    Log.d(TAG, "internet connected");
                    onStartActivity();
                } else {
                    Log.d(TAG, "no internet connection");
                    noInternet.setVisibility(View.VISIBLE);
                    noInternet.setAnimation(in_bottomAnim);
                    checkInternetConnection(500);
                }
            }
        }, delay);

    }

    private void onStartActivity() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        Log.d(TAG, "checking for user");
        if (user != null) {
            try {
                FirebaseFirestore.getInstance().collection("USERS").document(user.getUid())
                        .update("last_seen", FieldValue.serverTimestamp()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "user found");
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "server error 1 " + task.getException().toString());
                        }
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "server error 2  " + e);
            }

        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInternetConnection(3000);
    }
}