package com.donkeymonkey.recyq.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.donkeymonkey.recyq.SingleFragmentActivity;
import com.donkeymonkey.recyq.fragments.CommunityFragment;
import com.donkeymonkey.recyq.fragments.StoreItemFragment;
import com.donkeymonkey.recyq.model.StoreItem;

public class StoreItemActivity extends SingleFragmentActivity {

    private static final String STORE_ITEM = "com.donkeymonkey.recyq.model.storeitem";
    private static String mStoreItemKey;

    public static Intent newIntent(Context context, String storeItemKey) {
        mStoreItemKey = storeItemKey;
        return new Intent(context, StoreItemActivity.class);
    }

    @Override
    protected Fragment createFragment() {

        return StoreItemFragment.newInstance(mStoreItemKey);
    }

}
