package com.donkeymonkey.recyq.fragments;

import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.dialogs.DialogCO2;
import com.donkeymonkey.recyq.dialogs.DialogStoreItem;
import com.donkeymonkey.recyq.dialogs.DialogTokens;
import com.donkeymonkey.recyq.model.User;

import at.grabner.circleprogress.CircleProgressView;

public class StatsFragment extends Fragment {

    private static final String TAG = "StoreFragment";
    private static final String DIALOG_CO2 = "dialog_co2";
    private static final String DIALOG_TOKENS = "dialog_tokens";

    private User mUser;

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = User.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();

        mUser = User.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        LinearLayout co2_view = (LinearLayout) view.findViewById(R.id.stats_totalCO2Delivered_linearview);
        LinearLayout tokens_view = (LinearLayout) view.findViewById(R.id.stats_totalTokens_linearview);

        CircleProgressView co2_progress = (CircleProgressView) view.findViewById(R.id.stats_tree_progress);
        CircleProgressView tokens_progress = (CircleProgressView) view.findViewById(R.id.stats_tokens_progress);
        TextView co2_saved = (TextView) view.findViewById(R.id.stats_co2_saved);
        TextView trees_saved = (TextView) view.findViewById(R.id.stats_textview_tree_count);
        TextView tokens_earned = (TextView) view.findViewById(R.id.stats_tokens_earned);
        TextView kilos_delivered = (TextView) view.findViewById(R.id.stats_kg_delivered);

        TextView textile_title = (TextView) view.findViewById(R.id.stats_textview_textile_title);
        TextView textile_recycled_text = (TextView) view.findViewById(R.id.stats_textile_recycled);
        TextView textile_co2_text = (TextView) view.findViewById(R.id.stats_textile_co2_saved);
        TextView paper_title = (TextView) view.findViewById(R.id.stats_textview_paper_title);
        TextView paper_recycled_text = (TextView) view.findViewById(R.id.stats_paper_recycled);
        TextView paper_co2_text = (TextView) view.findViewById(R.id.stats_paper_co2_saved);
        TextView plastic_title = (TextView) view.findViewById(R.id.stats_textview_pmd_title);
        TextView plastic_recycled_text = (TextView) view.findViewById(R.id.stats_pmd_recycled);
        TextView plastic_co2_text = (TextView) view.findViewById(R.id.stats_pmd_co2_saved);
        TextView glass_title = (TextView) view.findViewById(R.id.stats_textview_glass_title);
        TextView glass_recycled_text = (TextView) view.findViewById(R.id.stats_glass_recycled);
        TextView glass_co2_text = (TextView) view.findViewById(R.id.stats_glass_co2_saved);
        TextView bio_title = (TextView) view.findViewById(R.id.stats_textview_bio_title);
        TextView bio_recycled_text = (TextView) view.findViewById(R.id.stats_bio_recycled);
        TextView bio_co2_text = (TextView) view.findViewById(R.id.stats_bio_co2_saved);
        TextView eWaste_title = (TextView) view.findViewById(R.id.stats_textview_eWaste_title);
        TextView eWaste_recycled_text = (TextView) view.findViewById(R.id.stats_eWaste_recycled);
        TextView eWaste_co2_text = (TextView) view.findViewById(R.id.stats_eWaste_co2_saved);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

        co2_saved.setTypeface(pt_mono_font);
        trees_saved.setTypeface(pt_mono_font);
        tokens_earned.setTypeface(pt_mono_font);
        kilos_delivered.setTypeface(pt_mono_font);
        textile_title.setTypeface(volvobroad_font);
        textile_recycled_text.setTypeface(pt_mono_font);
        textile_co2_text.setTypeface(pt_mono_font);
        paper_title.setTypeface(volvobroad_font);
        paper_recycled_text.setTypeface(pt_mono_font);
        paper_co2_text.setTypeface(pt_mono_font);
        plastic_title.setTypeface(volvobroad_font);
        plastic_recycled_text.setTypeface(pt_mono_font);
        plastic_co2_text.setTypeface(pt_mono_font);
        glass_title.setTypeface(volvobroad_font);
        glass_recycled_text.setTypeface(pt_mono_font);
        glass_co2_text.setTypeface(pt_mono_font);
        bio_title.setTypeface(volvobroad_font);
        bio_recycled_text.setTypeface(pt_mono_font);
        bio_co2_text.setTypeface(pt_mono_font);
        eWaste_title.setTypeface(volvobroad_font);
        eWaste_recycled_text.setTypeface(pt_mono_font);
        eWaste_co2_text.setTypeface(pt_mono_font);

        co2_progress.setValueAnimated(mUser.getRemainingCO2ToSaveOneTree());
        tokens_progress.setValueAnimated(mUser.getRemainingKGToEarnOneToken());

        co2_saved.setText(getString(R.string.stats_total_co2, mUser.getTotalCo2Saved()));
        trees_saved.setText(getString(R.string.stats_total_trees_saved, mUser.getTreesSaved()));
        kilos_delivered.setText(getString(R.string.stats_total_kg, mUser.getTotalKgRecycled()));
        textile_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfTextile()));
        textile_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getTextileC02Saved()));
        paper_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfPaper()));
        paper_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getPaperC02Saved()));
        plastic_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfPlastic()));
        plastic_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getPlasticsC02Saved()));
        glass_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfGlass()));
        glass_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getGlassC02Saved()));
        bio_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfBioWaste()));
        bio_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getBioWasteC02Saved()));
        eWaste_recycled_text.setText(getString(R.string.stats_recycled, mUser.getAmountOfEWaste()));
        eWaste_co2_text.setText(getString(R.string.stats_c02_saved, mUser.getEWasteC02Saved()));


        if (mUser.getTokens() > 1 || mUser.getTokens() == 0) {
            tokens_earned.setText(getString(R.string.stats_total_tokens, mUser.getTokens()));
        } else {
            tokens_earned.setText(getString(R.string.stats_total_token));
        }

        co2_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                DialogCO2 dialogCO2 = new DialogCO2();
                dialogCO2.show(fragmentManager, DIALOG_CO2);
            }
        });

        tokens_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                DialogTokens dialogTokens = new DialogTokens();
                dialogTokens.show(fragmentManager, DIALOG_TOKENS);
            }
        });


        return view;
    }
}
