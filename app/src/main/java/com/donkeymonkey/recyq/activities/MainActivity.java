package com.donkeymonkey.recyq.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.dialogs.DialogPartners;
import com.donkeymonkey.recyq.dialogs.DialogProfile;
import com.donkeymonkey.recyq.fragments.CommunityFragment;
import com.donkeymonkey.recyq.fragments.LocationsFragment;
import com.donkeymonkey.recyq.fragments.StoreFragment;
import com.donkeymonkey.recyq.fragments.SocialFragment;
import com.donkeymonkey.recyq.fragments.StatsFragment;
import com.donkeymonkey.recyq.model.Coupon;
import com.donkeymonkey.recyq.model.Coupons;
import com.donkeymonkey.recyq.model.RecyQLocation;
import com.donkeymonkey.recyq.model.RecyQLocations;
import com.donkeymonkey.recyq.model.StoreItem;
import com.donkeymonkey.recyq.model.StoreItems;
import com.donkeymonkey.recyq.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private static final String DIALOG_PROFILE= "dialog_profile";

    private User mUser;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.stats_icon,
            R.drawable.community_icon,
            R.drawable.store_icon,
            R.drawable.location_icon,
            R.drawable.home_icon
    };
    private TabItem mProfileTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up view
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setTitle("");

        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mProfileTab = (TabItem) findViewById(R.id.action_profile);

        setupTabIcons();

        mProfileTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                DialogProfile dialogProfile = new DialogProfile();

                dialogProfile.show(fragmentManager, DIALOG_PROFILE);

            }
        });

        checkForUpdates();

    }

    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

    // MARK - TAB BAR METHODS

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StatsFragment(), "Stats");
        adapter.addFragment(new CommunityFragment(), "Community");
        adapter.addFragment(new StoreFragment(), "Shop");
        adapter.addFragment(new LocationsFragment(), "Locations");
        adapter.addFragment(new SocialFragment(), "Social");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {

        Float fontSize = 11f;

        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.custom_tab, null, false);

        LinearLayout tabStats = (LinearLayout)   headerView.findViewById(R.id.tab_stats);
        LinearLayout tabCommunity = (LinearLayout)   headerView.findViewById(R.id.tab_community);
        LinearLayout tabShop = (LinearLayout)   headerView.findViewById(R.id.tab_shop);
        LinearLayout tabLocations = (LinearLayout)   headerView.findViewById(R.id.tab_locations);
        LinearLayout tabSocial = (LinearLayout)   headerView.findViewById(R.id.tab_social);

        tabLayout.getTabAt(0).setCustomView(tabStats);
        tabLayout.getTabAt(1).setCustomView(tabCommunity);
        tabLayout.getTabAt(2).setCustomView(tabShop);
        tabLayout.getTabAt(3).setCustomView(tabLocations);
        tabLayout.getTabAt(4).setCustomView(tabSocial);


    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        mUser = User.getInstance();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        if (mUser.isLoggingOut()) {
            mAuth.signOut();

            mUser = User.getInstance();
            mUser.clearUser();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

            finish();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
