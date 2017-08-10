package com.donkeymonkey.recyq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.PartnersFragment;

public class PartnersActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, PartnersActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return PartnersFragment.newInstance();
    }

}
