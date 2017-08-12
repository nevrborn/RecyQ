package com.donkeymonkey.recyq.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.Community;
import com.donkeymonkey.recyq.model.User;

public class CommunityFragment extends Fragment {

    private Community mCommunity;

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        mCommunity = Community.getInstance();

        // Initialising UI
        TextView recycled_total = (TextView) view.findViewById(R.id.community_textview_total_recycled_total);
        TextView recycled_kilos = (TextView) view.findViewById(R.id.community_textview_total_recycled_kg);
        TextView recycled_saved = (TextView) view.findViewById(R.id.community_textview_total_recycled_saved);

        TextView co2_total = (TextView) view.findViewById(R.id.community_textview_total_co2_total);
        TextView co2_kilos = (TextView) view.findViewById(R.id.community_textview_total_co2_kg);
        TextView co2_saved = (TextView) view.findViewById(R.id.community_textview_total_co2_saved);

        TextView tokens_total = (TextView) view.findViewById(R.id.community_textview_total_tokens_total);
        TextView tokens_kilos = (TextView) view.findViewById(R.id.community_textview_total_tokens_tokens);
        TextView tokens_saved = (TextView) view.findViewById(R.id.community_textview_total_tokens_saved);

        TextView trees_total = (TextView) view.findViewById(R.id.community_textview_total_trees_total);
        TextView trees_kilos = (TextView) view.findViewById(R.id.community_textview_total_trees_trees);
        TextView trees_saved = (TextView) view.findViewById(R.id.community_textview_total_trees_saved);

        Button whatAreWeDoingButton = (Button) view.findViewById(R.id.community_button_whatAreWeDoing);
        Button whoIsOnTopButton = (Button) view.findViewById(R.id.community_button_whoISOnTop);
        Button communityPartnersButton = (Button) view.findViewById(R.id.community_button_partners);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

        recycled_total.setTypeface(pt_mono_font);
        recycled_kilos.setTypeface(volvobroad_font);
        recycled_saved.setTypeface(pt_mono_font);

        co2_total.setTypeface(pt_mono_font);
        co2_kilos.setTypeface(volvobroad_font);
        co2_saved.setTypeface(pt_mono_font);

        tokens_total.setTypeface(pt_mono_font);
        tokens_kilos.setTypeface(volvobroad_font);
        tokens_saved.setTypeface(pt_mono_font);

        trees_total.setTypeface(pt_mono_font);
        trees_kilos.setTypeface(volvobroad_font);
        trees_saved.setTypeface(pt_mono_font);

        whatAreWeDoingButton.setTypeface(pt_mono_font);
        whoIsOnTopButton.setTypeface(pt_mono_font);
        communityPartnersButton.setTypeface(pt_mono_font);

        // Setting values
        recycled_kilos.setText(getString(R.string.community_kg, mCommunity.getTotalKilosDelivered()));
        co2_kilos.setText(getString(R.string.community_kg, mCommunity.getTotalCO2Saved()));
        tokens_kilos.setText(getString(R.string.community_totat_token, mCommunity.getTotalTokensEarned()));
        trees_kilos.setText(getString(R.string.community_trees_number, mCommunity.getTotalTreesSaved()));


        // Button Actions
        whatAreWeDoingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        whoIsOnTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        communityPartnersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        return view;
    }


}
