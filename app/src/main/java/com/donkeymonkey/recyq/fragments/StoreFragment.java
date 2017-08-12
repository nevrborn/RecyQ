package com.donkeymonkey.recyq.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.StoreItem;
import com.donkeymonkey.recyq.model.StoreItems;
import com.donkeymonkey.recyq.model.User;

import java.util.List;

public class StoreFragment extends Fragment {

    private User mUser;
    private List<StoreItem> mStoreItems;

    private RecyclerView mRecyclerView;
    private StoreItemAdapter mStoreItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        mUser = User.getInstance();

        // Initialising UI
        TextView tokens_avaliable = (TextView) view.findViewById(R.id.store_textview_tokens_available);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.store_recyclerview);

        // Setting fonts
        Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
        Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

        tokens_avaliable.setTypeface(pt_mono_font);

        if (mUser.getTokens() == 0) {
            tokens_avaliable.setText(R.string.store_no_tokens);
        } else if (mUser.getTokens() == 1) {
            tokens_avaliable.setText(R.string.store_token_to_exchange);
        } else  if (mUser.getTokens() > 1){
            tokens_avaliable.setText(getString(R.string.store_tokens_to_exchange, mUser.getTokens()));
        }

        mStoreItems = StoreItems.getInstance();

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoreItemAdapter = new StoreItemAdapter(mStoreItems);
        mRecyclerView.setAdapter(mStoreItemAdapter);

        return view;
    }


    // MARK - Recycler view methods

    public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemHolder> {

        List<StoreItem> mStoreItems;

        public StoreItemAdapter(List<StoreItem> storeItemsToShow) {
            mStoreItems = storeItemsToShow;
        }

        @Override
        public StoreItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.storeitem_viewholder, parent, false);
            return new StoreItemHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StoreItemHolder holder, int position) {
            StoreItem storeItem = mStoreItems.get(position);
            holder.setStoreItem(storeItem);
            holder.itemView.setOnClickListener(holder);

            holder.mStoreName.setText(storeItem.getShopName());
            holder.mDescription.setText(storeItem.getDetailDescription());

            if (storeItem.getTokenAmount() > 1) {
                holder.mTokens.setText(getString(R.string.store_tokens, storeItem.getTokenAmount()));
            } else {
                holder.mTokens.setText(getString(R.string.store_token));
            }

            byte[] imageByteArray = Base64.decode(storeItem.getImageString(), Base64.DEFAULT);

            Glide.with(getActivity())
                    .load(imageByteArray)
                    .asBitmap()
                    .into(holder.mImage);
        }

        @Override
        public int getItemCount() {
            return mStoreItems.size();
        }
    }

    public class StoreItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private StoreItem mStoreItem;

        private TextView mStoreName;
        private TextView mTokens;
        private TextView mDescription;
        private ImageView mImage;

        public StoreItemHolder(View itemView) {
            super(itemView);
            mStoreName = (TextView) itemView.findViewById(R.id.storeItem_textview_store_name);
            mTokens = (TextView) itemView.findViewById(R.id.storeItem_textview_token);
            mDescription = (TextView) itemView.findViewById(R.id.storeItem_textView_description);
            mImage = (ImageView) itemView.findViewById(R.id.storeItem_imageview);

            // Setting fonts
            Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
            Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

            mStoreName.setTypeface(volvobroad_font);
            mTokens.setTypeface(pt_mono_font);
            mDescription.setTypeface(pt_mono_font);

            mStoreName.setTextSize(22);
            mTokens.setTextSize(18);
        }

        public void setStoreItem(StoreItem storeItem) {
            mStoreItem = storeItem;
        }

        @Override
        public void onClick(View view) {

        }
    }


}
