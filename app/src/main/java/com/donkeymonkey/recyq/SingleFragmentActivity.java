package com.donkeymonkey.recyq;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.donkeymonkey.recyq.fragments.StatsFragment;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    @LayoutRes
    private int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        // set the references
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        // fragment code
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    protected abstract Fragment createFragment();

}
