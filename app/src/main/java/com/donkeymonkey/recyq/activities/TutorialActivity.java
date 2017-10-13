package com.donkeymonkey.recyq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.TutorialFragment;


public class TutorialActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TutorialActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return TutorialFragment.newInstance();
    }
}
