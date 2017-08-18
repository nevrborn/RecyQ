package com.donkeymonkey.recyq.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.User;

import at.grabner.circleprogress.CircleProgressView;

public class DialogCO2 extends DialogFragment {

    private User mUser;

    private TextView mTitle;
    private CircleProgressView mProgressView;
    private TextView mTreeCounter;
    private TextView mCO2Saved;
    private TextView mExplanation;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_co2, null);

        mUser = User.getInstance();

        mTitle = new TextView(getActivity());
        mProgressView = (CircleProgressView) view.findViewById(R.id.co2_saved_tree_progress);
        mTreeCounter = (TextView) view.findViewById(R.id.co2_saved_textview_tree_count);
        mCO2Saved = (TextView) view.findViewById(R.id.co2_saved_kg);
        mExplanation = (TextView) view.findViewById(R.id.co2_saved_text);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

        mTitle.setTypeface(volvobroad_font);
        mTreeCounter.setTypeface(pt_mono_font);
        mCO2Saved.setTypeface(pt_mono_bold_font);
        mExplanation.setTypeface(pt_mono_font);

        mCO2Saved.setTextSize(16);

        mTitle.setText(R.string.co2_saved_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        mProgressView.setValueAnimated(mUser.getRemainingCO2ToSaveOneTree());
        mTreeCounter.setText(String.valueOf(mUser.getTreesSaved()));
        mCO2Saved.setText(getString(R.string.co2_saved_total_co2, mUser.getTotalCo2Saved()));


        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNegativeButton(R.string.ok, null)
                .create();
    }

}
