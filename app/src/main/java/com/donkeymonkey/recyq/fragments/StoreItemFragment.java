package com.donkeymonkey.recyq.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.StoreItem;
import com.donkeymonkey.recyq.model.StoreItems;

import java.util.ArrayList;

public class StoreItemFragment extends Fragment {

    private static String mStoreItemKey;
    private StoreItem mStoreItem = new StoreItem();
    private StoreItems mStoreItems;

    public static StoreItemFragment newInstance(String storeItemKey) {
        mStoreItemKey = storeItemKey;

        return new StoreItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_storeitem, container, false);

        mStoreItems = StoreItems.getInstance();
        mStoreItem = mStoreItems.findStoreItemWithKey(mStoreItemKey);

        TextView storeName = (TextView) view.findViewById(R.id.storeItem_textview_store_name);
        ImageView imageView = (ImageView) view.findViewById(R.id.storeItem_imageview);
        TextView description = (TextView) view.findViewById(R.id.storeItem_textView_description);
        TextView detail_description = (TextView) view.findViewById(R.id.storeItem_textView_detail_description);
        TextView tokens = (TextView) view.findViewById(R.id.storeItem_textView_token);
        Button buy_button = (Button) view.findViewById(R.id.storeItem_button_buy);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono_bold.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

        storeName.setTypeface(volvobroad_font);
        description.setTypeface(pt_mono_bold_font);
        detail_description.setTypeface(pt_mono_font);
        tokens.setTypeface(pt_mono_font);
        buy_button.setTypeface(pt_mono_bold_font);

        storeName.setText(mStoreItem.getShopName());
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

        return view;
    }

}
