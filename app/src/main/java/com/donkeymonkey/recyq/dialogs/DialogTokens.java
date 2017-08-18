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

public class DialogTokens  extends DialogFragment {

    private User mUser;

    private TextView mTitle;
    private CircleProgressView mProgressView;
    private TextView mTokensEarned;
    private TextView mExplanation;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_tokens, null);

        mUser = User.getInstance();

        mTitle = new TextView(getActivity());
        mProgressView = (CircleProgressView) view.findViewById(R.id.tokens_earned_tree_progress);
        mTokensEarned = (TextView) view.findViewById(R.id.tokens_earned_tokens);
        mExplanation = (TextView) view.findViewById(R.id.tokens_earned_text);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

        mTitle.setTypeface(volvobroad_font);
        mTokensEarned.setTypeface(pt_mono_bold_font);
        mExplanation.setTypeface(pt_mono_font);

        mTokensEarned.setTextSize(16);

        mTitle.setText(R.string.tokens_earned_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        mProgressView.setValueAnimated(mUser.getRemainingKGToEarnOneToken());

        if (mUser.getTokens() > 1 || mUser.getTokens() == 0) {
            mTokensEarned.setText(getString(R.string.tokens_earned_total_tokens, mUser.getTokens()));
        } else {
            mTokensEarned.setText(getString(R.string.tokens_earned_total_token));
        }


        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNegativeButton(R.string.ok, null)
                .create();
    }

}
