package com.silverphoenix.soca.signUpLogin;

import static com.silverphoenix.soca.DBQuery.DBQueries.GOOGLE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.PHONE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.database_userGetData;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silverphoenix.soca.DBQuery.DBQueries;
import com.silverphoenix.soca.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private final String TAG = "LoginActivity";
    private LinearLayout googleBtn, phoneBtn, phoneInputLayout;
    private Spinner countryCodeSpinner;
    private EditText phoneEditText;
    private Button phoneContinueBtn;
    @SuppressLint("ResourceType")
    private Dialog noInternetDialog, loadingDialog;
    boolean clicked = false;



    private boolean ACTIVITY_RUNNING = false;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private GoogleSignInClient mGoogleSignInClint;
    private int SIGN_IN_TYPE = -1;
    private int GOOGLE_SIGN_IN = 0;
    private int GC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleBtn = findViewById(R.id.google_login_btn);
        phoneBtn = findViewById(R.id.phone_login_btn);
        phoneInputLayout = findViewById(R.id.phone_input_layout);
        countryCodeSpinner = findViewById(R.id.code_spinner);
        phoneEditText = findViewById(R.id.phone_input);
        phoneContinueBtn = findViewById(R.id.phone_continue_btn);


        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //........................... no internet connection layout start ............................//
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.solid_3);
        ImageView Image = loadingDialog.findViewById(R.id.loading_image);
        Glide.with(getApplicationContext()).load(R.drawable.gg).into(Image);
        //........................... no internet connection layout end ............................//


        //........................... no internet connection layout start ............................//
        noInternetDialog = new Dialog(this);
        noInternetDialog.setContentView(R.layout.dialog_no_internet_connection);
        noInternetDialog.setCancelable(false);
        noInternetDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noInternetDialog.getWindow().setBackgroundDrawableResource(R.drawable.solid_1);
        ImageView noInternetImage = noInternetDialog.findViewById(R.id.no_internet_image);
        Glide.with(getApplicationContext()).load(R.drawable.meter).into(noInternetImage);
        //........................... no internet connection layout end ............................//

        String[] codeList = {
                "+91",
                "+1",
                "+66",
                "+99",
                "0",
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, codeList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        countryCodeSpinner.setAdapter(adapter);


        phoneBtn.setOnClickListener(v -> {
            if (!clicked) {
                phoneInputLayout.setVisibility(View.VISIBLE);
                phoneContinueBtn.setVisibility(View.VISIBLE);
                clicked = true;
            }

        });

        phoneContinueBtn.setOnClickListener(v -> {
            if (phoneEditText.getText().toString().length() == 10) {
                Log.d(TAG, "moving to otp activity");
                Intent intent = new Intent(this, OTPActivity.class);
                intent.putExtra("METHOD", PHONE_SIGN_METHOD);
                intent.putExtra("PHONE",  countryCodeSpinner.getSelectedItem().toString() + phoneEditText.getText().toString());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Enter Proper Phone Number", Toast.LENGTH_SHORT).show();
            }
        });

        /*.................................. Google Sign Up starts ...................................*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClint = GoogleSignIn.getClient(this, gso);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneInputLayout.setVisibility(View.GONE);
                phoneContinueBtn.setVisibility(View.GONE);
                clicked = false;
                if (!ACTIVITY_RUNNING) {
                    GoogleSignUp();
                } else {
                    Toast.makeText(LoginActivity.this, "Please wait, Other Activity is running.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*................................. Google sign up ends.......................................*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInternetConnection(500);
    }

    private void checkInternetConnection(int delay) {


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (networkInfo != null && networkInfo.isConnected()) {
                    noInternetDialog.dismiss();
                } else {
                    noInternetDialog.show();
                }
                checkInternetConnection(1000);
            }
        }, delay);
    }


    /*.................... Google Sign In setting start ........................*/
    private void GoogleSignUp() {
        Intent googleSignInIntent = mGoogleSignInClint.getSignInIntent();
        SIGN_IN_TYPE = GOOGLE_SIGN_IN;
        startActivityForResult(googleSignInIntent, GC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ACTIVITY_RUNNING = true;
        if (SIGN_IN_TYPE == GOOGLE_SIGN_IN) {
            if (requestCode == GC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Toast.makeText(this, "We got access from Google, We will attempt Sign In now Please wait.", Toast.LENGTH_SHORT).show();
                    FirebaseGoogleAuth(account);
                } catch (ApiException e) {
                    Toast.makeText(this, "Unable to get access from Google, please try again after sometime.  " + e, Toast.LENGTH_SHORT).show();
//                    FirebaseGoogleAuth(null);
                }
            }
        }
        ACTIVITY_RUNNING = false;
    }

    private void FirebaseGoogleAuth(final GoogleSignInAccount account) {
        ACTIVITY_RUNNING = true;
        loadingDialog.show();
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                FirebaseUser user = mAuth.getCurrentUser();
                boolean newUser = Objects.requireNonNull(task.getResult().getAdditionalUserInfo()).isNewUser();

                if (newUser) {
                    // sign up just now for first time
                    Toast.makeText(LoginActivity.this, "Welcome user", Toast.LENGTH_SHORT).show();
                    assert user != null;
                    Bundle bundle = new Bundle();
                    bundle.putString("NAME", user.getDisplayName());
                    bundle.putString("EMAIL", user.getEmail());
                    bundle.putString("PROFILE", Objects.requireNonNull(user.getPhotoUrl()).toString());

                    Intent intent = new Intent(this, SignupActivity.class);
                    intent.putExtra("BUNDLE", bundle);
                    intent.putExtra("METHOD", GOOGLE_SIGN_METHOD);
                    startActivity(intent);
                    finish();

                    loadingDialog.dismiss();

                } else {
                    //sign up for second time or
                    loadingDialog.dismiss();
                    database_userGetData(LoginActivity.this,loadingDialog,true);
                    //TODO

                }
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed, Please try after sometime.", Toast.LENGTH_SHORT).show();
            }
        });


        ACTIVITY_RUNNING = false;
    }

    /*.................... Google Sign In setting end   ........................*/
}