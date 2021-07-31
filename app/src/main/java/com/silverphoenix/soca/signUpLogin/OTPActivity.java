package com.silverphoenix.soca.signUpLogin;

import static com.silverphoenix.soca.DBQuery.DBQueries.GOOGLE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.PHONE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.database_userGetData;
import static com.silverphoenix.soca.DBQuery.DBQueries.database_userSetData;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silverphoenix.soca.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {


    private int METHOD = -1;
    private final String TAG = "OTPActivity:-";

    private boolean ACTIVITY_RUNNING = false;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private Bundle bundle;
    private Dialog loadingDialog, noInternetDialog;

    private TextView phoneNumberText, resendOTPBtn;
    private EditText otpInput;
    private Button verifyBtn;

    private String PHONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        phoneNumberText = findViewById(R.id.phone_number);
        otpInput = findViewById(R.id.ed_otp);
        resendOTPBtn = findViewById(R.id.resend_text);
        verifyBtn = findViewById(R.id.verify_btn);


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

        METHOD = getIntent().getIntExtra("METHOD", -1);

        if (METHOD == GOOGLE_SIGN_METHOD) {
            Log.d(TAG, "from google auth");
            bundle = getIntent().getBundleExtra("BUNDLE");
            PHONE = bundle.getString("PHONE");
        } else if (METHOD == PHONE_SIGN_METHOD) {
            Log.d(TAG, "from phone auth");
            PHONE = getIntent().getStringExtra("PHONE");
            Toast.makeText(this, "Phone " + PHONE, Toast.LENGTH_SHORT).show();
        }
        sendVerificationCode(PHONE);
        phoneNumberText.setText(PHONE);

        verifyBtn.setOnClickListener(v -> {
            if (otpInput.getText().toString().length() == 6){

                verifyCode(otpInput.getText().toString());
            }else{
                Toast.makeText(this, "Enter proper otp", Toast.LENGTH_SHORT).show();
            }
        });

        resendOTPBtn.setOnClickListener(v -> {
            sendVerificationCode(PHONE);
        });

    }

    /*......................Phone Verifying Method Start..............................*/
    //method to send verification code
    //method to check the verification code
    //method to input the code manually
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        if (METHOD == GOOGLE_SIGN_METHOD) {
            Log.d(TAG, "code verified, moving to create database, google method");
            database_userSetData(OTPActivity.this,
                    bundle.get("NAME").toString(),
                    bundle.get("EMAIL").toString(),
                    bundle.get("PROFILE").toString(),
                    bundle.get("PHONE").toString(),
                    bundle.get("BIRTH_YEAR").toString(),
                    bundle.get("CITY").toString(),
                    bundle.get("GENDER").toString(),
                    bundle.get("PREFERENCE").toString(),
                    bundle.get("RESPONSE").toString(),
                    loadingDialog);

        } else if (METHOD == PHONE_SIGN_METHOD) {
            Log.d(TAG, "code verified, moving to sign in user, phone method");
            signInWithCredential(credential);
        }
    }

    //////////////////////////////// All authentication code start ////////////////////////////////////////////////////////////

    private void sendVerificationCode(String phone) {
        Log.d(TAG, "initiating code sent function");
        loadingDialog.show();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phone)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {

                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.

                    // Save verification ID and resending token so we can use them later
                    super.onCodeSent(verificationId, token);
                    mVerificationId = verificationId;
                    mResendToken = token;
loadingDialog.dismiss();
                    Log.d(TAG, "verification code sent");
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        verifyCode(code);
                        loadingDialog.dismiss();
                    }
                    Log.d(TAG, "verification code received, automatically");
                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        Log.d(TAG, "verification failed, input wrong otp" + e.toString());
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        Log.d(TAG, "verification failed sms quota exceeded" + e.toString());
                    }
                    loadingDialog.dismiss();
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                    Log.d(TAG, "verification code retrieval time out");
               loadingDialog.dismiss();
                }
            };

    // to check or verify the phone number
    private void signInWithCredential(PhoneAuthCredential credential) {
        Log.d(TAG, "attempting sign in");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "sign in successful");
                            try {
                                FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                                assert user != null;
                                long creationTimeStamp = Objects.requireNonNull(user.getMetadata()).getCreationTimestamp();
                                long lastSignInTimestamp = user.getMetadata().getLastSignInTimestamp();

                                if (creationTimeStamp == lastSignInTimestamp) {
                                    Log.d(TAG, "phone new user");
                                    //new user

                                    Intent intent = new Intent(OTPActivity.this, SignupActivity.class);
                                    intent.putExtra("PHONE", PHONE);
                                    intent.putExtra("METHOD", PHONE_SIGN_METHOD);
                                    startActivity(intent);
                                    finish();
                                    loadingDialog.dismiss();
                                } else {
                                    Log.d(TAG, "phone old user");
                                    database_userGetData(OTPActivity.this, loadingDialog, true);
                                }


                            } catch (Exception e) {
                                Log.d(TAG, "error getting user or time stamp");
                            }

                        } else {
                            Log.d(TAG, "sign in failed");
                            // Toast.makeText(PhoneAuthenticationActivity.this, "Phone Number Verification Failed", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.d(TAG, "sign in failed");
                            }
                        }
                    }
                });
    }
    /*......................Phone Verifying Method End..............................*/

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
                checkInternetConnection(500);
            }
        }, delay);
    }

}