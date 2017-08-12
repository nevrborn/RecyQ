package com.donkeymonkey.recyq.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.donkeymonkey.recyq.R;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private User mUser;
    private ArrayList<StoreItem> mStoreItems;
    private ArrayList<RecyQLocation> mRecyQLocations;
    private ArrayList<Coupon> mCoupons;

    private FirebaseAuth mAuth;
    private DatabaseReference mClientsRef;
    private DatabaseReference mCouponsRef;
    private DatabaseReference mShopsRef;
    private DatabaseReference mRecyQLocationRef;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mClientsRef = FirebaseDatabase.getInstance().getReference("clients");
        mCouponsRef = FirebaseDatabase.getInstance().getReference("coupons");
        mShopsRef = FirebaseDatabase.getInstance().getReference("Shops");
        mRecyQLocationRef = FirebaseDatabase.getInstance().getReference("RecyQ Locations");

        // Set up view
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setIcon(R.drawable.recyq_logo);
            getSupportActionBar().setTitle("");
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mUser = User.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mUser = User.getInstance();
        mStoreItems = StoreItems.getInstance();
        mRecyQLocations = RecyQLocations.getInstance();
        mCoupons = Coupons.getInstance();

        getStoresFromFirebase();
        getRecyQLocationsFromFirebase();
        getCouponsFromFirebase();

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

        Float fontSize = 9f;

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.stats_icon, 0, 0);
        tabOne.setText("Stats");
        tabOne.setTextSize(fontSize);

        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Community");
        tabTwo.setTextSize(fontSize);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.community_icon, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Shop");
        tabThree.setTextSize(fontSize);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.store_icon, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Locations");
        tabFour.setTextSize(fontSize);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.location_icon, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText("Social");
        tabFive.setTextSize(fontSize);
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.rsz_social_icon, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);
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

    // MARK - FIREBASE METHODS

    public void getStoresFromFirebase() {

        mShopsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot storeSnapshot: dataSnapshot.getChildren()) {

                    for (DataSnapshot storeItemSnapshot: storeSnapshot.getChildren()) {
                        StoreItem storeItem = storeItemSnapshot.getValue(StoreItem.class);
                        mStoreItems.add(storeItem);
                        Log.e("Got StoreItem", storeItem.getItemName());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getRecyQLocationsFromFirebase() {

        mRecyQLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    RecyQLocation recyQLocation = snapshot.getValue(RecyQLocation.class);
                    mRecyQLocations.add(recyQLocation);
                    Log.e("Got RecyQLocation", recyQLocation.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCouponsFromFirebase() {

        mCouponsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Coupon coupon = snapshot.getValue(Coupon.class);
                    mCoupons.add(coupon);
                    Log.e("Got Coupon", coupon.getCouponName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
