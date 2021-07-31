package com.silverphoenix.soca.signUpLogin;

import static com.silverphoenix.soca.DBQuery.DBQueries.GOOGLE_SIGN_METHOD;
import static com.silverphoenix.soca.DBQuery.DBQueries.PHONE_SIGN_METHOD;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.silverphoenix.soca.R;

public class SignupActivity extends AppCompatActivity {


    private int METHOD = -1;
    private String email;
    private String profile;
    private String phone;

    private String TAG = "SignupActivity: ";
    private String meSelected = "non", inSelected = "non";
    private EditText edName, edPhone, edEmail;
    private LinearLayout phoneLinearLayout, emailLinearLayout;
    private Spinner birthYearSpinner, citySpinner, countryCodeSpinner;

    private ImageView meMale, meFemale, meOther;
    private TextView meMaleText, meFemaleText, meOtherText;

    private ImageView inMale, inFemale, inOther;
    private TextView inMaleText, inFemaleText, inOtherText;

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edName = findViewById(R.id.su_name);
        edPhone = findViewById(R.id.su_phone);
        edEmail = findViewById(R.id.su_email);

        phoneLinearLayout = findViewById(R.id.linearLayout_phone);
        emailLinearLayout = findViewById(R.id.linearLayout_email);

        birthYearSpinner = findViewById(R.id.su_birth_spinner);
        citySpinner = findViewById(R.id.su_city_spinner);
        countryCodeSpinner = findViewById(R.id.code_spinner);

        meMale = findViewById(R.id.me_male);
        meMaleText = findViewById(R.id.me_male_text);
        meFemale = findViewById(R.id.me_female);
        meFemaleText = findViewById(R.id.me_female_text);
        meOther = findViewById(R.id.me_other);
        meOtherText = findViewById(R.id.me_other_text);

        inMale = findViewById(R.id.in_male);
        inMaleText = findViewById(R.id.in_male_text);
        inFemale = findViewById(R.id.in_female);
        inFemaleText = findViewById(R.id.in_female_text);
        inOther = findViewById(R.id.in_other);
        inOtherText = findViewById(R.id.in_other_text);

        nextBtn = findViewById(R.id.next_btn);

        meGenderSelect();
        inGenderSelect();


        //............................................Spinner...........................................//

        String[] codeList = {
                "+91",
                "+1",
                "+66",
                "+99",
                "0",
        };

        ArrayAdapter<String> codeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, codeList);
        codeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        countryCodeSpinner.setAdapter(codeAdapter);


        String[] cityList = {
                "select",
                "malkangiri",
                "jeypor",
                "koraput",
                "hi",
        };

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cityList);
        cityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);


        String[] yearList = {
                "select",
                "2003",
                "2002",
                "2001",
                "2000",
        };

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, yearList);
        yearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        birthYearSpinner.setAdapter(yearAdapter);


        //............................................Spinner...........................................//
        METHOD = getIntent().getIntExtra("METHOD", -1);

        if (METHOD == GOOGLE_SIGN_METHOD) {
            emailLinearLayout.setVisibility(View.GONE);
            Bundle bundle = getIntent().getBundleExtra("BUNDLE");
            edName.setText(bundle.getString("NAME"));
            email = bundle.getString("EMAIL");
            profile = bundle.getString("PROFILE");

        } else if (METHOD == PHONE_SIGN_METHOD) {
            phoneLinearLayout.setVisibility(View.GONE);
            phone = getIntent().getStringExtra("PHONE");
        }

        nextBtn.setOnClickListener(v -> {
            checkInput();
        });

    }

    private void meGenderSelect() {
        meMale.setOnClickListener(v -> {
            if (!meSelected.equals("male")) {
                meMale.setImageResource(R.drawable.i_male);
                meMaleText.setTypeface(null, Typeface.BOLD);

                meFemale.setImageResource(R.drawable.i_o_female);
                meFemaleText.setTypeface(null, Typeface.NORMAL);

                meOther.setImageResource(R.drawable.i_o_user);
                meOtherText.setTypeface(null, Typeface.NORMAL);

                meSelected = "male";
            }

        });

        meFemale.setOnClickListener(v -> {
            if (!meSelected.equals("female")) {
                meMale.setImageResource(R.drawable.i_o_male);
                meMaleText.setTypeface(null, Typeface.NORMAL);

                meFemale.setImageResource(R.drawable.i_female);
                meFemaleText.setTypeface(null, Typeface.BOLD);

                meOther.setImageResource(R.drawable.i_o_user);
                meOtherText.setTypeface(null, Typeface.NORMAL);

                meSelected = "female";
            }

        });

        meOther.setOnClickListener(v -> {
            if (!meSelected.equals("other")) {
                meMale.setImageResource(R.drawable.i_o_male);
                meMaleText.setTypeface(null, Typeface.NORMAL);

                meFemale.setImageResource(R.drawable.i_o_female);
                meFemaleText.setTypeface(null, Typeface.NORMAL);

                meOther.setImageResource(R.drawable.i_user);
                meOtherText.setTypeface(null, Typeface.BOLD);

                meSelected = "other";
            }

        });
    }

    private void inGenderSelect() {
        inMale.setOnClickListener(v -> {
            if (!inSelected.equals("male")) {
                inMale.setImageResource(R.drawable.i_male);
                inMaleText.setTypeface(null, Typeface.BOLD);

                inFemale.setImageResource(R.drawable.i_o_female);
                inFemaleText.setTypeface(null, Typeface.NORMAL);

                inOther.setImageResource(R.drawable.i_o_user);
                inOtherText.setTypeface(null, Typeface.NORMAL);

                inSelected = "male";
            }

        });

        inFemale.setOnClickListener(v -> {
            if (!inSelected.equals("female")) {
                inMale.setImageResource(R.drawable.i_o_male);
                inMaleText.setTypeface(null, Typeface.NORMAL);

                inFemale.setImageResource(R.drawable.i_female);
                inFemaleText.setTypeface(null, Typeface.BOLD);

                inOther.setImageResource(R.drawable.i_o_user);
                inOtherText.setTypeface(null, Typeface.NORMAL);

                inSelected = "female";
            }

        });

        inOther.setOnClickListener(v -> {
            if (!inSelected.equals("other")) {
                inMale.setImageResource(R.drawable.i_o_male);
                inMaleText.setTypeface(null, Typeface.NORMAL);

                inFemale.setImageResource(R.drawable.i_o_female);
                inFemaleText.setTypeface(null, Typeface.NORMAL);

                inOther.setImageResource(R.drawable.i_user);
                inOtherText.setTypeface(null, Typeface.BOLD);

                inSelected = "other";
            }

        });
    }

    private void checkInput() {
        if (edName.getText().length() > 4 && !edName.getText().toString().equals("")) {

            if (!birthYearSpinner.getSelectedItem().toString().equals("select")) {
                if (!citySpinner.getSelectedItem().toString().equals("select")) {
                    if (!meSelected.equals("non")) {
                        if (!inSelected.equals("non")) {

                            boolean ok = false;
                            Bundle userBundle = new Bundle();
                            userBundle.putString("NAME", edName.getText().toString());
                            if (METHOD == GOOGLE_SIGN_METHOD) {
                                if (edPhone.getText().length() == 10 && !edPhone.getText().toString().equals("")) {
                                    userBundle.putString("EMAIL", email);
                                    userBundle.putString("PROFILE", profile);
                                    userBundle.putString("PHONE", countryCodeSpinner.getSelectedItem().toString() + edPhone.getText().toString());
                                    ok = true;
                                } else {
                                    Toast.makeText(this, "Enter Proper Phone Number", Toast.LENGTH_SHORT).show();
                                    ok=false;
                                }
                            } else if (METHOD == PHONE_SIGN_METHOD) {
                                //get other info
                                if (edEmail.getText().toString().contains("@") && edEmail.getText().toString().contains(".com")) {
                                    userBundle.putString("EMAIL", edEmail.getText().toString());
                                    userBundle.putString("PROFILE", "");
                                    userBundle.putString("PHONE", phone);
                                    ok = true;
                                } else {
                                    Toast.makeText(this, "Enter proper Email address", Toast.LENGTH_SHORT).show();
                                    ok = false;
                                }
                            }
                            userBundle.putString("BIRTH_YEAR", birthYearSpinner.getSelectedItem().toString());
                            userBundle.putString("CITY", citySpinner.getSelectedItem().toString());
                            userBundle.putString("GENDER", meSelected);
                            userBundle.putString("PREFERENCE", inSelected);

                            if (ok) {
                                Intent questionIntent = new Intent(this, QuestionActivity.class);
                                questionIntent.putExtra("BUNDLE", userBundle);
                                questionIntent.putExtra("METHOD", METHOD);
                                startActivity(questionIntent);
                                finish();
                            }

                        } else {
                            Toast.makeText(this, "Please Select your preference", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please select your nearest city you live in", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "please select your year of birth", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Enter Proper Name", Toast.LENGTH_SHORT).show();
        }
    }

}