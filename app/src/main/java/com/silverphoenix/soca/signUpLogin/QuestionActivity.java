package com.silverphoenix.soca.signUpLogin;

import static com.silverphoenix.soca.DBQuery.DBQueries.GOOGLE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.PHONE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.database_userSetData;
import static com.silverphoenix.soca.signUpLogin.QuestionModel.Q_DEFAULT;
import static com.silverphoenix.soca.signUpLogin.QuestionModel.Q_NO;
import static com.silverphoenix.soca.signUpLogin.QuestionModel.Q_YES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.silverphoenix.soca.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private final String TAG = "QuestionActivity";

    private Button continueBtn;
    private ViewPager2 viewPager2;
    private List<QuestionModel> questionModelList = new ArrayList<>();
    private Bundle bundle;
    private int METHOD;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        continueBtn = findViewById(R.id.question_continue_btn);
        viewPager2 = findViewById(R.id.q_viewpager2);

        //........................... no internet connection layout start ............................//
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.solid_3);
        ImageView Image = loadingDialog.findViewById(R.id.loading_image);
        Glide.with(getApplicationContext()).load(R.drawable.gg).into(Image);
        //........................... no internet connection layout end ............................//


        bundle = getIntent().getBundleExtra("BUNDLE");
        METHOD = getIntent().getIntExtra("METHOD", -1);

        questionModelList.add(new QuestionModel("Did you have a happy childhood?", Q_DEFAULT));
        questionModelList.add(new QuestionModel("Did you ever sneak out at night?", Q_DEFAULT));
        questionModelList.add(new QuestionModel("Can money buy happiness?", Q_DEFAULT));
        questionModelList.add(new QuestionModel("Should women change their last names after married?", Q_DEFAULT));
        questionModelList.add(new QuestionModel("Is cheating on a test wrong?", Q_DEFAULT));

        QuestionAdapter questionAdapter = new QuestionAdapter(questionModelList);
        viewPager2.setAdapter(questionAdapter);

        continueBtn.setOnClickListener(v -> processData());

    }

    private void processData() {
        String response = "";
        for (int i = 0; i < questionModelList.size(); i++) {
            if (questionModelList.get(i).getRespond() != Q_DEFAULT ) {
                if (questionModelList.get(i).getRespond() == Q_YES) {
                    response = response + "1";
                } else if (questionModelList.get(i).getRespond() == Q_NO) {
                    response = response + "0";
                }
            } else {
                Toast.makeText(this, "Please answer all the question for finding better match", Toast.LENGTH_SHORT).show();
                break;
            }
            if (i == questionModelList.size() - 1) {
                if (METHOD == GOOGLE_SIGN_METHOD){
                    Toast.makeText(this, "continue", Toast.LENGTH_SHORT).show();
                    bundle.putString("RESPONSE", response);
                    Intent intent = new Intent(this, OTPActivity.class);
                    intent.putExtra("BUNDLE",bundle);
                    intent.putExtra("METHOD",GOOGLE_SIGN_METHOD);
                    startActivity(intent);
                    finish();
                }else if (METHOD == PHONE_SIGN_METHOD){
                    database_userSetData(QuestionActivity.this,
                            bundle.get("NAME").toString(),
                            bundle.get("EMAIL").toString(),
                            bundle.get("PROFILE").toString(),
                            bundle.get("PHONE").toString(),
                            bundle.get("BIRTH_YEAR").toString(),
                            bundle.get("CITY").toString(),
                            bundle.get("GENDER").toString(),
                            bundle.get("PREFERENCE").toString(),
                            response,
                            loadingDialog);
                }
            }
        }
    }
}