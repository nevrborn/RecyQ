package com.donkeymonkey.recyq.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.fragments.StoreItemFragment;
import com.donkeymonkey.recyq.model.StoreItem;
import com.donkeymonkey.recyq.model.StoreItems;

public class DialogStoreItem extends DialogFragment {

    private static final String STORE_ITEM_KEY = "store_item_key";

    private static String mStoreItemKey;
    private StoreItem mStoreItem = new StoreItem();
    private StoreItems mStoreItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStoreItemKey = getArguments().getString(STORE_ITEM_KEY);
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_storeitem, null);

        mStoreItems = StoreItems.getInstance();
        mStoreItem = mStoreItems.findStoreItemWithKey(mStoreItemKey);

       // TextView storeName = (TextView) view.findViewById(R.id.storeItem_textview_store_name);
        ImageView imageView = (ImageView) view.findViewById(R.id.storeItem_imageview);
        TextView description = (TextView) view.findViewById(R.id.storeItem_textView_description);
        TextView detail_description = (TextView) view.findViewById(R.id.storeItem_textView_detail_description);
        TextView tokens = (TextView) view.findViewById(R.id.storeItem_textView_token);
        Button buy_button = (Button) view.findViewById(R.id.storeItem_button_buy);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

        //storeName.setTypeface(volvobroad_font);
        description.setTypeface(pt_mono_bold_font);
        detail_description.setTypeface(pt_mono_font);
        tokens.setTypeface(pt_mono_font);
        buy_button.setTypeface(pt_mono_bold_font);

        TextView mTitle = new TextView(getActivity());
        mTitle.setText(mStoreItem.getShopName());
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        //storeName.setText(mStoreItem.getShopName());
        description.setText(mStoreItem.getItemName());
        detail_description.setText(mStoreItem.getDetailDescription());

        if (mStoreItem.getTokenAmount() > 1) {
            tokens.setText(getString(R.string.storeitem_price_more, mStoreItem.getTokenAmount()));
        } else {
            tokens.setText(getString(R.string.storeitem_price_one));
        }

        byte[] imageByteArray = Base64.decode(mStoreItem.getImageString(), Base64.DEFAULT);

        Glide.with(getActivity())
                .load(imageByteArray)
                .asBitmap()
                .into(imageView);

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setNegativeButton(R.string.store_not_now, null)
                .setCustomTitle(mTitle)
                .create();
    }


}
