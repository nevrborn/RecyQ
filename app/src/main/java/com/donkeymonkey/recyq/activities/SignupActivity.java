package com.donkeymonkey.recyq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.SignupFragment;

public class SignupActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SignupActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return SignupFragment.newInstance();
    }



}
