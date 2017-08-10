package com.donkeymonkey.recyq.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.User;

public class StatsFragment extends Fragment {

    private User mUser;

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = User.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        LinearLayout totalKgDelivered = (LinearLayout) view.findViewById(R.id.stats_totalKGDelivered_linearview);
        LinearLayout tokens = (LinearLayout) view.findViewById(R.id.stats_tokens_linearview);

        TextView textile_recycled_text = (TextView) view.findViewById(R.id.stats_textile_recycled);
        TextView textile_co2_text = (TextView) view.findViewById(R.id.stats_textile_co2_saved);
        TextView paper_recycled_text = (TextView) view.findViewById(R.id.stats_paper_recycled);
        TextView paper_co2_text = (TextView) view.findViewById(R.id.stats_paper_co2_saved);
        TextView pmd_recycled_text = (TextView) view.findViewById(R.id.stats_pmd_recycled);
        TextView pmd_co2_text = (TextView) view.findViewById(R.id.stats_pmd_co2_saved);
        TextView bio_recycled_text = (TextView) view.findViewById(R.id.stats_bio_recycled);
        TextView bio_co2_text = (TextView) view.findViewById(R.id.stats_bio_co2_saved);
        TextView eWaste_recycled_text = (TextView) view.findViewById(R.id.stats_eWaste_recycled);
        TextView eWaste_co2_text = (TextView) view.findViewById(R.id.stats_eWaste_co2_saved);

        textile_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfTextile()));
        textile_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getAmountOfTextile()));
        paper_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfPaper()));
        paper_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getAmountOfPaper()));
        pmd_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfPlastic()));
        pmd_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getAmountOfPlastic()));
        bio_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfBioWaste()));
        bio_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getAmountOfBioWaste()));
        eWaste_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfEWaste()));
        eWaste_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getAmountOfEWaste()));

        totalKgDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tokens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }
}
