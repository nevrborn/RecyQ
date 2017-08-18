package com.donkeymonkey.recyq.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.dialogs.DialogPickUp;
import com.donkeymonkey.recyq.model.RecyQLocation;
import com.donkeymonkey.recyq.model.RecyQLocations;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class LocationsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationsFragment";
    private static final String DIALOG_PICKUP = "dialog_pick_up";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private RecyQLocations mRecyQLocations;
    private List<RecyQLocation> mRecyQLocationList;
    private GoogleApiClient mGoogleApiClient;

    private RecyclerView mRecyclerView;
    private LocationsAdapter mLocationAdapter;

    public static LocationsFragment newInstance() {
        return new LocationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity() /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkPlayServices();
    }

    @Override
    public void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        mRecyQLocations = RecyQLocations.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.locations_recyclerview);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLocationAdapter = new LocationsAdapter(mRecyQLocations);
        mRecyclerView.setAdapter(mLocationAdapter);

        return view;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // MARK - Recycler view methods

    public class LocationsAdapter extends RecyclerView.Adapter<LocationsHolder> {


        public LocationsAdapter(RecyQLocations recyQLocations) {
            mRecyQLocationList = recyQLocations.getRecyQLocationsList();
        }

        @Override
        public LocationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.viewholder_location, parent, false);
            return new LocationsHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LocationsHolder holder, int position) {
            RecyQLocation recyQLocation = mRecyQLocationList.get(position);
            holder.setLocation(recyQLocation);
            holder.itemView.setOnClickListener(holder);

            holder.mLocationName.setText(recyQLocation.getName());
            holder.mLocatioNDetailName.setText(recyQLocation.getName());
            holder.mAddresse.setText(recyQLocation.getAddress());
            holder.mPostcode.setText(recyQLocation.getPostalCodePlace());
            holder.mPhoneNumber.setText(recyQLocation.getPhoneNumber());
            holder.mEmail.setText(recyQLocation.getEmail());
            holder.mWebsite.setText(recyQLocation.getWebsiteURL());

            GoogleMap thisMap = holder.mMap;

            if (thisMap != null) {

            }


            if (position == 0) holder.mLocationName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenDark));
            if (position == 1) holder.mLocationName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorYellow));
            if (position == 2) holder.mLocationName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlue));
            if (position == 3) holder.mLocationName.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRedDark));
        }

        @Override
        public int getItemCount() {
            return mRecyQLocationList.size();
        }

        @Override
        public void onViewRecycled(LocationsHolder holder) {
            super.onViewRecycled(holder);

            if (holder.mMap != null) {
                holder.mMap.clear();
                holder.mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
            }
        }
    }

    public class LocationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnMapReadyCallback {

        private RecyQLocation mRecyQLocation;

        private TextView mLocationName;
        private TextView mLocatioNDetailName;
        private TextView mAddresse;
        private TextView mPostcode;
        private TextView mPhoneNumber;
        private TextView mEmail;
        private TextView mWebsite;
        private TextView mPickup;
        private LinearLayout mPickupLayout;
        private MapView mMapView;

        private GoogleMap mMap;
        private Location mCurrentLocation;
        private OnMapReadyCallback mOnMapReadyCallback;


        public LocationsHolder(View itemView) {
            super(itemView);

            mLocationName = (TextView) itemView.findViewById(R.id.locations_textview_name);
            mLocatioNDetailName = (TextView) itemView.findViewById(R.id.locations_textview_details_name);
            mAddresse = (TextView) itemView.findViewById(R.id.locations_textview_details_adresse);
            mPostcode = (TextView) itemView.findViewById(R.id.locations_textview_details_postcode);
            mPhoneNumber = (TextView) itemView.findViewById(R.id.locations_textview_details_phonenumber);
            mEmail = (TextView) itemView.findViewById(R.id.locations_textview_details_email);
            mWebsite = (TextView) itemView.findViewById(R.id.locations_textview_details_website);
            mPickup = (TextView) itemView.findViewById(R.id.locations_textview_pickup);
            mPickupLayout = (LinearLayout) itemView.findViewById(R.id.locations_pickup);
            mMapView = (MapView) itemView.findViewById(R.id.locations_map);

            if (mMapView != null) {
                mMapView.onCreate(null);
                mMapView.onResume();
                mMapView.getMapAsync(this);
            }

            // Setting fonts
            Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
            Typeface pt_mono_bold_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono_bold.ttf");
            Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

            mLocationName.setTypeface(volvobroad_font);
            mLocatioNDetailName.setTypeface(pt_mono_bold_font);
            mAddresse.setTypeface(pt_mono_font);
            mPostcode.setTypeface(pt_mono_font);
            mPhoneNumber.setTypeface(pt_mono_font);
            mEmail.setTypeface(pt_mono_font);
            mWebsite.setTypeface(pt_mono_font);
            mPickup.setTypeface(pt_mono_font);

            mLocationName.setTextSize(22);

            mPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(mPhoneNumber.getText().toString()));
                    startActivity(intent);
                }
            });

            mEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(mWebsite.getText().toString()));
                    startActivity(intent);
                }
            });

            mPickupLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
                    DialogPickUp dialogPickUp = new DialogPickUp();
                    dialogPickUp.show(fragmentManager, DIALOG_PICKUP);
                }
            });
        }

        public void setLocation(RecyQLocation location) {
            mRecyQLocation = location;
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(getContext());
            mMap = googleMap;

            if (mRecyQLocation.getLatitude() != null && mRecyQLocation.getLongitude() != null) {
                LatLng location = new LatLng(mRecyQLocation.getLatitude(), mRecyQLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14));
                MarkerOptions marker = new MarkerOptions().position(location);
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                mMap.addMarker(marker);
            }
        }

    }

    private void checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getActivity());

        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
        }

    }

}
