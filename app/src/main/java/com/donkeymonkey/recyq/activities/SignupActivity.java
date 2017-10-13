package com.donkeymonkey.recyq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.SignupFragment;

public class SignupActivity extends SingleFragmentActivity {

    private static Boolean mIsFacebookUser;

    public static Intent newIntent(Context context, Boolean isFacebookUser) {
        mIsFacebookUser = isFacebookUser;
        return new Intent(context, SignupActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return SignupFragment.newInstance(mIsFacebookUser);
    }



}
