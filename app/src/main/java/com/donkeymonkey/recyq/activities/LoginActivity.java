package com.donkeymonkey.recyq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.LoginFragment;
import com.donkeymonkey.recyq.fragments.StatsFragment;
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

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginFragment";

    // Facebook Parameters
    private CallbackManager mCallbackManager;
    private static Boolean mIsLoggingOut = false;
    private static Boolean mHasJustLoggedOut = false;
    LoginButton mFacebookLoginButton;
    Boolean mAddedDummyData = false;

    private FirebaseAuth mFirebaseAuth;
    private User mUser = User.get();

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

        mFirebaseAuth = FirebaseAuth.getInstance();

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

                    mFirebaseAuth.signInWithEmailAndPassword(mMail, mPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, R.string.sign_in_succesfull, Toast.LENGTH_SHORT).show();

                                User.set();
                                queryOrderedBy("uid", User.get().getUid());
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, R.string.auth_not_all_fields_filled, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Facebook Login Button Setup
        mFacebookLoginButton.setReadPermissions("email", "public_profile");
        mFacebookLoginButton.setFragment(LoginFragment.newInstance());

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
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: Do we need this? Can we delete it?
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
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mUser = dataSnapshot.getValue(User.class);

                User.setInstance(mUser);

                Intent intent = MainActivity.;
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
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            User.set();
                            queryOrderedBy("uid", User.get().getUid());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }
}