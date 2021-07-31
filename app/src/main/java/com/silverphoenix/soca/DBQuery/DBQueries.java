package com.silverphoenix.soca.DBQuery;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.silverphoenix.soca.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DBQueries {

    private final static String TAG = "DBQueries activity :-";

    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    public final static int GOOGLE_SIGN_METHOD = 0;
    public final static int PHONE_SIGN_METHOD = 1;
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static String database_user_full_name, database_user_phone_number, database_user_email, database_user_city;
    public static String database_user_birth_year, database_user_profile, database_user_gender, database_user_preference, database_user_response;


    /*this method is to get basic database_user information*/
    public static void database_userSetData(final Context context, final String NAME, final String EMAIL,
                                            final String PROFILE, final String PHONE, final String BIRTH_YEAR,
                                            final String CITY, final String GENDER, final String PREFER,
                                            final String RESPONSE, Dialog loadingDialog
    ) {

        Log.d(TAG, "setting user data, creating map");
        loadingDialog.show();
        //// creating a map to input data ////
        Map<String, Object> userdata = new HashMap<>();
        String data = NAME + "/-/" + EMAIL + "/-/" + PHONE + "/-/" + BIRTH_YEAR + "/-/" + GENDER + "/-/" + PREFER + "/-/" + CITY + "/-/" + RESPONSE;
        userdata.put("user_data", data);
        userdata.put("user_profile", PROFILE);
        userdata.put("date_joined", FieldValue.serverTimestamp());
        userdata.put("last_seen", FieldValue.serverTimestamp());


        ////// writing the data of user to the firestore firebase//// and creating all additional file like wish-list, cart-item, profile, etc ////////////
        firebaseFirestore.collection("USERS").document(Objects.requireNonNull(firebaseAuth.getUid()))
                .set(userdata)  // user data added or adding
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "created user data file, adding additional data and maps");
                        CollectionReference userDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                        //////////// Maps

                        //// creating the my wish-list folder inside the user data ////////////////
                        Map<String, Object> postMap = new HashMap<>();
                        postMap.put("list_size", (long) 0);

                        Map<String, Object> reelMap = new HashMap<>();
                        reelMap.put("list_size", (long) 0);

                        Map<String, Object> likedMap = new HashMap<>();
                        likedMap.put("list_size", (long) 0);

                        Map<String, Object> matchedMap = new HashMap<>();
                        matchedMap.put("list_size", (long) 0);

                        Map<String, Object> notificationMap = new HashMap<>();
                        notificationMap.put("list_size", (long) 0);


                        final List<String> documentName = new ArrayList<>();
                        documentName.add("MY_POST");
                        documentName.add("MY_REEL");
                        documentName.add("MY_LIKE");
                        documentName.add("MY_MATCHED");
                        documentName.add("MY_NOTIFICATION");

                        List<Map<String, Object>> documentField = new ArrayList<>();
                        documentField.add(postMap);
                        documentField.add(reelMap);
                        documentField.add(likedMap);
                        documentField.add(matchedMap);
                        documentField.add(notificationMap);


                        for (int x = 0; x < documentName.size(); x++) {

                            final int finalX = x;
                            userDataReference.document(documentName.get(x))
                                    .set(documentField.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (finalX == documentName.size() - 1) {
                                            Log.d(TAG, "Data set creation complete");
                                            loadingDialog.dismiss();
                                            // login to the data base and adding user id to the updated user list in database
                                            Intent intent = new Intent(context, MainActivity.class);
                                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            context.startActivity(intent);
                                        }
                                    } else {
                                        Log.d(TAG, "data set creation between half");
                                        loadingDialog.dismiss();
                                    }
                                }
                            });
                        }
                    } else {
                        Log.d(TAG, "data base creation failed");
                        loadingDialog.dismiss();
                    }
                });
    }

    public static void database_userGetData(final Context context, Dialog loadingDialog, boolean loadMainActivity) {
        final CollectionReference collectionReference = firebaseFirestore.collection("USERS");
        loadingDialog.show();
        Log.d(TAG, "getting user data");
        collectionReference.document(firebaseUser.getUid()).get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (task.isSuccessful()) {
                        String user_data = documentSnapshot.getString("user_data");

                        String[] data = user_data.split("/-/");


                        database_user_full_name = data[0];
                        database_user_email = data[1];
                        database_user_phone_number = data[2];
                        database_user_birth_year = data[3];
                        database_user_gender = data[4];
                        database_user_preference = data[5];
                        database_user_city = data[6];
                        database_user_response = data[7];

                        database_user_profile = documentSnapshot.getString("user_profile");

                        Log.d(TAG, "user data retrieve successfully, writing last seen");
                        collectionReference.document(firebaseUser.getUid()).update("last_seen", FieldValue.serverTimestamp())
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "user retrieve data completed");
                                        loadingDialog.dismiss();
                                        if (loadMainActivity ){
                                            Log.d(TAG, "moving to main activity");
                                            Intent mainIntent = new Intent(context, MainActivity.class);
                                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            context.startActivity(mainIntent);
                                        }
                                    }
                                });

                    } else {
                        loadingDialog.dismiss();
                        Log.d(TAG, "user retrieving data failed");
                        Toast.makeText(context, "we are unable to connect the server, all services may not be available please try after sometime.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
