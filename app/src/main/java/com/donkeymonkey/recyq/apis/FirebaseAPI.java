package com.donkeymonkey.recyq.apis;

import android.support.annotation.NonNull;

import com.donkeymonkey.recyq.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAPI {

    private static final String TAG = "FirebaseAPI";

    private String mEmail;
    private String mPassword;
    private User mUser;

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mClientsRef;
    private DatabaseReference mCouponsRef;


    public void setUpFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mClientsRef = FirebaseDatabase.getInstance().getReference("clients");
        mCouponsRef = FirebaseDatabase.getInstance().getReference("coupons");
    }

    public void signUpNewUser(final User user, final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getAddedByUser(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {

                } else if (task.isSuccessful()) {

                }

            }
        });
    }

    public void addUserDateToFirebare(User user) {
        String userID = User.getInstance().getKey();

    }

    public void loginWithEmailAndPassword(String email, String password) {

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {

                } else if (task.isSuccessful()) {

                    User.set();
                }

            }
        });
    }

    public void sendRequestForPasswordReset(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (!task.isSuccessful()) {

                } else if (task.isSuccessful()) {


                }
            }
        });
    }

    public void getUserDataFromFirebase() {

        String userID = User.getInstance().getKey();

        DatabaseReference mUserRef = mClientsRef.child(userID);


    }

    public void signUpFacebookUser() {

    }

}
