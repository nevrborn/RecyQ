package com.donkeymonkey.recyq.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.activities.MainActivity;
import com.donkeymonkey.recyq.activities.TutorialActivity;
import com.donkeymonkey.recyq.model.User;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SignupFragment extends Fragment {

    private static final String TAG = "SignUpFragment";

    private User mUser;

    // Facebook Parameters
    private CallbackManager mCallbackManager;
    private static Boolean mIsLoggingOut = false;
    private static Boolean mHasJustLoggedOut = false;
    Boolean mAddedDummyData = false;
    private static Boolean mIsFacebookUser = false;

    private FirebaseAuth mFirebaseAuth;

    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mAddressField;
    private EditText mPostcodeField;
    private EditText mCityField;
    private EditText mPhoneNumberField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Spinner mWasteLocationField;
    private Button mRegisterButton;

    private String mPassword;

    public static SignupFragment newInstance(Boolean isFacebookUser) {
        mIsFacebookUser = isFacebookUser;
        return new SignupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mUser = User.getInstance();

        mFirstNameField = (EditText) view.findViewById(R.id.registration_editText_firstName);
        mLastNameField = (EditText) view.findViewById(R.id.registration_editText_lastName);
        mAddressField = (EditText) view.findViewById(R.id.registration_editText_address);
        mPostcodeField = (EditText) view.findViewById(R.id.registration_editText_postcode);
        mCityField = (EditText) view.findViewById(R.id.registration_editText_city);
        mPhoneNumberField = (EditText) view.findViewById(R.id.registration_editText_phonenumber);
        mEmailField = (EditText) view.findViewById(R.id.registration_editText_email);
        mPasswordField = (EditText) view.findViewById(R.id.registration_editText_password);
        mWasteLocationField = (Spinner) view.findViewById(R.id.registration_spinner_wasteLocation);
        mRegisterButton = (Button) view.findViewById(R.id.registration_button);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_waste, getResources().getStringArray(R.array.waste_locations));
        mWasteLocationField.setPrompt(getResources().getString(R.string.wasteLocation));
        mWasteLocationField.setAdapter(spinnerAdapter);

        if (mIsFacebookUser) {
            if (mUser.getName() != null && !mUser.getName().equals("")) mFirstNameField.setText(mUser.getName());
            if (mUser.getAddedByUser() != null && !mUser.getAddedByUser().equals("")) mEmailField.setText(mUser.getAddedByUser());
            if (mUser.getLastName() != null && !mUser.getLastName().equals("")) mLastNameField.setText(mUser.getLastName());
            if (mUser.getAddress() != null && !mUser.getAddress().equals("")) mAddressField.setText(mUser.getAddress());
            if (mUser.getZipCode() != null && !mUser.getZipCode().equals("")) mFirstNameField.setText(mUser.getZipCode());
            if (mUser.getCity() != null && !mUser.getCity().equals("")) mCityField.setText(mUser.getCity());
            if (mUser.getPhoneNumber() != null && !mUser.getPhoneNumber().equals("")) mPhoneNumberField.setText(mUser.getPhoneNumber());

            mPasswordField.setVisibility(View.GONE);
            mEmailField.setVisibility(View.GONE);

        } else {
            mPasswordField.setVisibility(View.VISIBLE);
            mEmailField.setVisibility(View.VISIBLE);
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkThatAllFieldsAreFilled()) {
                    mUser.setName(mFirstNameField.getText().toString());
                    mUser.setLastName(mLastNameField.getText().toString());
                    mUser.setAddress(mAddressField.getText().toString());
                    mUser.setZipCode(mPostcodeField.getText().toString());
                    mUser.setCity(mCityField.getText().toString());
                    mUser.setPhoneNumber(mPhoneNumberField.getText().toString());
                    mUser.setAddedByUser(mEmailField.getText().toString());
                    mUser.setNearestWasteLocation(mWasteLocationField.getSelectedItem().toString());

                    //Default values
                    mUser.setAmountOfPlastic(0.0);
                    mUser.setAmountOfPaper(0.0);
                    mUser.setAmountOfTextile(0.0);
                    mUser.setAmountOfEWaste(0.0);
                    mUser.setAmountOfBioWaste(0.0);
                    mUser.setAmountOfGlass(0.0);
                    mUser.setSpentCoins(0);
                    mUser.setCompleted(false);

                    mPassword = mPasswordField.getText().toString();

                    if (!mIsFacebookUser) {
                        createUserWithEmailAndPassword(mUser, mPassword);
                    } else {
                        setFirebaseUserInfo(mUser, mPassword);
                    }
                }
            }
        });

        return view;
    }

    private void createUserWithEmailAndPassword(final User user, final String password) {

        mFirebaseAuth.createUserWithEmailAndPassword(user.getAddedByUser(), password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                    if (firebaseUser != null) {
                        user.setUid(firebaseUser.getUid());
                        setFirebaseUserInfo(user, password);
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getContext(), "Sign up mislukt", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void signInUserWithEmailAndPassword(String email, String password) {

        mFirebaseAuth.signInWithEmailAndPassword(email, mPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(getActivity(), R.string.auth_failed, Toast.LENGTH_SHORT).show();
                } else if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), R.string.sign_in_succesfull, Toast.LENGTH_SHORT).show();

                    queryOrderedBy("uid", User.getInstance().getUid());

                    Intent intent = new Intent(getContext(), TutorialActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void setFirebaseUserInfo(User user, String password) {

        DatabaseReference mClientsRef = FirebaseDatabase.getInstance().getReference("clients");

        DatabaseReference mUserRef = mClientsRef.child(user.getUid());

        mUserRef.child("addedByUser").setValue(user.getAddedByUser());
        mUserRef.child("address").setValue(user.getAddress());
        mUserRef.child("amountOfBioWaste").setValue(0);
        mUserRef.child("amountOfEWaste").setValue(0);
        mUserRef.child("amountOfPaper").setValue(0);
        mUserRef.child("amountOfPlastic").setValue(0);
        mUserRef.child("amountOfTextile").setValue(0);
        mUserRef.child("city").setValue(user.getCity());
        mUserRef.child("completed").setValue(false);
        mUserRef.child("dateCreated").setValue("");
        mUserRef.child("didReceiveRecyQBags").setValue(false);
        mUserRef.child("lastName").setValue(user.getLastName());
        mUserRef.child("name").setValue(user.getName());
        mUserRef.child("nearestWasteLocation").setValue(user.getNearestWasteLocation());
        mUserRef.child("phoneNumber").setValue(user.getPhoneNumber());
        mUserRef.child("registeredVia").setValue("");
        mUserRef.child("spentCoins").setValue(0);
        mUserRef.child("uid").setValue(user.getUid());
        mUserRef.child("zipCode").setValue(user.getZipCode());

        if (!mIsFacebookUser) {
            signInUserWithEmailAndPassword(user.getAddedByUser(), password);
        } else {
            queryOrderedBy("uid", user.getUid());
            Intent intent = new Intent(getContext(), TutorialActivity.class);
            startActivity(intent);
        }

    }

    private void queryOrderedBy(String child, String value) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("clients").orderByChild("uid").equalTo(value);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mUser = dataSnapshot.getValue(User.class);
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


    private Boolean checkThatAllFieldsAreFilled() {

        Boolean allFieldsFilledIn = true;

        if (mFirstNameField.getText().equals("")) {
            allFieldsFilledIn = false;
            mFirstNameField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mFirstNameField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mLastNameField.getText().equals("")) {
            allFieldsFilledIn = false;
            mLastNameField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mLastNameField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mAddressField.getText().equals("")) {
            allFieldsFilledIn = false;
            mAddressField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mAddressField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mPostcodeField.getText().equals("")) {
            allFieldsFilledIn = false;
            mPostcodeField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mPostcodeField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mCityField.getText().equals("")) {
            allFieldsFilledIn = false;
            mCityField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mCityField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mPhoneNumberField.getText().equals("")) {
            allFieldsFilledIn = false;
            mPhoneNumberField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mPhoneNumberField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mEmailField.getText().equals("")) {
            allFieldsFilledIn = false;
            mEmailField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mEmailField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mPasswordField.getText().equals("")) {
            allFieldsFilledIn = false;
            mPasswordField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mPasswordField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        if (mWasteLocationField.getSelectedItem().toString().equals("") || mWasteLocationField.getSelectedItem().toString().equals(getResources().getString(R.string.wasteLocation))) {
            allFieldsFilledIn = false;
            mPasswordField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
        } else {
            mPasswordField.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorGreyLight));
        }

        return allFieldsFilledIn;
    }
}
