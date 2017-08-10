package com.donkeymonkey.recyq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.ProfileFragment;

public class ProfileActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ProfileActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return ProfileFragment.newInstance();
    }
}
