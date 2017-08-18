package com.donkeymonkey.recyq.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;

public class DialogPickUp extends DialogFragment {

    private TextView mTitle;
    private TextView mText;
    private TextView mPhoneNumber;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pickup, null);

        mTitle = new TextView(getActivity());
        mTitle.setText(R.string.locations_pick_up_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);

        mText = (TextView) view.findViewById(R.id.pickup_dialog_text);
        mPhoneNumber = (TextView) view.findViewById(R.id.pickup_dialog_phonenumber);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

        mTitle.setTypeface(volvobroad_font);
        mText.setTypeface(pt_mono_font);
        mPhoneNumber.setTypeface(pt_mono_font);

        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(getString(R.string.locations_pick_up_phone_number)));
                startActivity(intent);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNegativeButton(android.R.string.ok, null)
                .create();
    }
}
