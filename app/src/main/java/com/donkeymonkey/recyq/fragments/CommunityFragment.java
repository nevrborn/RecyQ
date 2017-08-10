package com.donkeymonkey.recyq.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donkeymonkey.recyq.R;

public class CommunityFragment extends Fragment {

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);



        return view;
    }

}
