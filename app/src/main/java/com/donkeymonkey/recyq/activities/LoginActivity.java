package com.donkeymonkey.recyq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.Utils.RecyQUtils;
import com.donkeymonkey.recyq.model.Community;
import com.donkeymonkey.recyq.model.Coupon;
import com.donkeymonkey.recyq.model.Coupons;
import com.donkeymonkey.recyq.model.Leaderboard;
import com.donkeymonkey.recyq.model.LeaderboardEntry;
import com.donkeymonkey.recyq.model.RecyQLocation;
import com.donkeymonkey.recyq.model.RecyQLocations;
import com.donkeymonkey.recyq.model.StoreItem;
import com.donkeymonkey.recyq.model.StoreItems;
import com.donkeymonkey.recyq.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private User mUser;
    private StoreItems mStoreItems;
    private RecyQLocations mRecyQLocations;
    private ArrayList<Coupon> mCoupons;
    private Community mCommunity;
    private Leaderboard mLeaderboard;

    private DatabaseReference mClientsRef;
    private DatabaseReference mCouponsRef;
    private DatabaseReference mShopsRef;
    private DatabaseReference mRecyQLocationRef;

    // Facebook Parameters
    private CallbackManager mCallbackManager;
    private static Boolean mIsLoggingOut = false;
    private static Boolean mHasJustLoggedOut = false;
    LoginButton mFacebookLoginButton;

    private FirebaseAuth mAuth;

    private int mFirebaseAPICalls = 0;

    private String mPassword;
    private String mMail;

    public LoginActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        // Setting up the different view elements
        final EditText emailEditText = (EditText) findViewById(R.id.login_edittext_email);
        final EditText passwordEditText = (EditText) findViewById(R.id.login_edittext_password);
        Button loginButton = (Button) findViewById(R.id.login_button);
        mFacebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        Button registerUserButton = (Button) findViewById(R.id.login_register_button);
        Button resetPasswordButton = (Button) findViewById(R.id.login_reset_password_button);

        mUser = User.getInstance();
        mStoreItems = StoreItems.getInstance();
        mRecyQLocations = RecyQLocations.getInstance();
        mCoupons = Coupons.getInstance();
        mCommunity = Community.getInstance();
        mLeaderboard = Leaderboard.getInstance();

        // Check if user is already logged in
        if (mUser.isUserIsLoggedIn() && !mUser.getUid().equals("")) {
            Intent intent = StatsActivity.newIntent(getApplicationContext());
            startActivity(intent);
            finish();
        }

        // Check if there is already a Firebase user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mClientsRef = FirebaseDatabase.getInstance().getReference("clients");
        mCouponsRef = FirebaseDatabase.getInstance().getReference("coupons");
        mShopsRef = FirebaseDatabase.getInstance().getReference("Shops");
        mRecyQLocationRef = FirebaseDatabase.getInstance().getReference("RecyQ Locations");

        mClientsRef.keepSynced(true);
        mCouponsRef.keepSynced(true);
        mShopsRef.keepSynced(true);
        mRecyQLocationRef.keepSynced(true);

        getCouponsFromFirebase();
        getRecyQLocationsFromFirebase();
        getStoresFromFirebase();
        getCommunityDataFromFirebase();

        if (firebaseUser != null) {
            User.set();
            queryOrderedBy("uid", User.getInstance().getUid());
        }

        FacebookSdk.sdkInitialize(this);
        mCallbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);

        // set testuser ready to log in - JUST FOR TESTING!
        emailEditText.setText("robstassen@gmail.com");
        passwordEditText.setText("rob123");

        // Firebase login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!String.valueOf(emailEditText.getText()).equals("") && !String.valueOf(passwordEditText.getText()).equals("")) {

                    mMail = emailEditText.getText().toString();
                    mPassword = passwordEditText.getText().toString();

                    if (RecyQUtils.hasInternetConnection(getApplicationContext()) && isGooglePlayServicesAvailable()) {
                        mAuth.signInWithEmailAndPassword(mMail, mPassword)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithEmail:success");

                                            User.set();
                                            queryOrderedBy("uid", User.getInstance().getUid());

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                                            Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.no_internet, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.auth_not_all_fields_filled, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Facebook Login Button Setup
        mFacebookLoginButton.setReadPermissions("email", "public_profile");

        mFacebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

                Intent intent = StatsActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignupActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFacebookParameters() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,hometown,friends{id,name,picture},gender,birthday,picture{url}");

        request.setParameters(parameters);
        request.executeAsync();

    }

    private void queryOrderedBy(String child, String value) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("clients").orderByChild("uid").equalTo(value);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {;
                mUser = dataSnapshot.getValue(User.class);
                mUser.setIsLoggedIn(true);

                User.setInstance(mUser);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            User.set();
                            queryOrderedBy("uid", User.getInstance().getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }

    // MARK - FIREBASE METHODS

    public void getStoresFromFirebase() {

        mShopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot storeSnapshot: dataSnapshot.getChildren()) {

                    for (DataSnapshot storeItemSnapshot: storeSnapshot.getChildren()) {
                        StoreItem storeItem = storeItemSnapshot.getValue(StoreItem.class);
                        storeItem.setKey(storeItemSnapshot.getKey());
                        mStoreItems.addStoreItem(storeItem);
                        Log.e("Got StoreItem", storeItem.getItemName());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getRecyQLocationsFromFirebase() {

        mRecyQLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    RecyQLocation recyQLocation = snapshot.getValue(RecyQLocation.class);
                    mRecyQLocations.addRecyQLocation(recyQLocation);
                    Log.e("Got RecyQLocation", recyQLocation.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCouponsFromFirebase() {

        mCouponsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Coupon coupon = snapshot.getValue(Coupon.class);
                    mCoupons.add(coupon);
                    Log.e("Got Coupon", coupon.getCouponName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCommunityDataFromFirebase() {

        mClientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCommunity.setCommunitySize((int) dataSnapshot.getChildrenCount());
                Log.e(TAG, String.valueOf(dataSnapshot.getChildrenCount()));

                for (DataSnapshot clientSnapshot: dataSnapshot.getChildren()) {

                    String key = "";
                    String firstName = "";
                    String lastName = "";

                    Object uidObject = clientSnapshot.child("uid").getValue();
                    Object firstNameObject = clientSnapshot.child("name").getValue();
                    Object lastNameObject =  clientSnapshot.child("lastName").getValue();

                    if (uidObject != null) key = uidObject.toString();
                    if (firstNameObject != null) firstName = firstNameObject.toString();
                    if (lastNameObject != null) lastName = lastNameObject.toString();

                    if (firstName.length() != 0) firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
                    if (lastName.length() != 0) lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

                    String nameOfuser = firstName + " " + lastName;

                    Double totalKgPerUser = 0.0;

                    Number plastic = (Number) clientSnapshot.child("amountOfPlastic").getValue();
                    Number biowaste = (Number) clientSnapshot.child("amountOfBioWaste").getValue();
                    Number eWaste = (Number) clientSnapshot.child("amountOfEWaste").getValue();
                    Number paper = (Number) clientSnapshot.child("amountOfPaper").getValue();
                    Number textile = (Number) clientSnapshot.child("amountOfTextile").getValue();


                    if (plastic != null) {
                        mCommunity.addKilos(plastic.doubleValue());
                        totalKgPerUser = totalKgPerUser + plastic.doubleValue();
                    }
                    if (biowaste != null) {
                        mCommunity.addKilos(biowaste.doubleValue());
                        totalKgPerUser = totalKgPerUser + biowaste.doubleValue();
                    }
                    if (eWaste != null) {
                        mCommunity.addKilos(eWaste.doubleValue());
                        totalKgPerUser = totalKgPerUser + eWaste.doubleValue();
                    }
                    if (paper != null) {
                        mCommunity.addKilos(paper.doubleValue());
                        totalKgPerUser = totalKgPerUser + paper.doubleValue();
                    }
                    if (textile != null) {
                        mCommunity.addKilos(textile.doubleValue());
                        totalKgPerUser = totalKgPerUser + textile.doubleValue();
                    }

                    if (dataSnapshot.hasChild("amountOfGlass")) {
                        Number glass = (Number) clientSnapshot.child("amountOfGlass").getValue();
                        if (glass != null) {
                            mCommunity.addKilos(glass.doubleValue());
                            totalKgPerUser = totalKgPerUser + glass.doubleValue();
                        }
                    }

                    LeaderboardEntry leaderboardEntry = new LeaderboardEntry(key, nameOfuser, totalKgPerUser);
                    mLeaderboard.addLeaderBoardEntry(leaderboardEntry);
                }


                Double total_co2 = (mCommunity.getTotalKilosDelivered()/35)*50;
                mCommunity.addCo2(total_co2);

                int total_tokens = (int) Math.round(mCommunity.getTotalKilosDelivered() / 35.0);
                mCommunity.addTokens(total_tokens);

                int total_trees = (int) Math.round(mCommunity.getTotalCO2Saved() / 16.6);
                mCommunity.addTrees(total_trees);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(getApplicationContext());
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(LoginActivity.this, status, 2404).show();
            }
            return false;
        }
        return true;
    }
}
