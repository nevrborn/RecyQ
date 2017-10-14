package com.donkeymonkey.recyq.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.Partners;

import java.util.List;

public class DialogPartners extends DialogFragment {

    private Partners mPartners;
    private List<Partners> mPartnersList;

    private RecyclerView mRecyclerView;
    private PartnersAdapter mPartnersAdapter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_partners, null);

        mPartners = Partners.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.partners_recyclerview);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPartnersAdapter = new PartnersAdapter(mPartners);
        mRecyclerView.setAdapter(mPartnersAdapter);

        // Setting fonts
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

        TextView mTitle = new TextView(getActivity());
        mTitle.setText(R.string.partner_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNegativeButton(R.string.ok, null)
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partners, container, false);

        mPartners = Partners.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.partners_recyclerview);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPartnersAdapter = new PartnersAdapter(mPartners);
        mRecyclerView.setAdapter(mPartnersAdapter);

        return view;
    }


    // MARK - Recycler view methods

    public class PartnersAdapter extends RecyclerView.Adapter<PartnersHolder> {


        public PartnersAdapter(Partners partners) {
            mPartnersList = partners.getPartnerList();
        }

        @Override
        public PartnersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.viewholder_partner, parent, false);
            return new PartnersHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PartnersHolder holder, int position) {
            Partners partner = mPartnersList.get(position);
            holder.setPartner(partner);
            holder.itemView.setOnClickListener(holder);

            holder.mName.setText(partner.getPartnerName());

            int id = getActivity().getResources().getIdentifier(partner.getPartnerImage(), "drawable", getActivity().getPackageName());
            holder.mImageView.setImageResource(id);
        }

        @Override
        public int getItemCount() {
            return mPartnersList.size();
        }
    }

    public class PartnersHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Partners mPartner;

        private TextView mName;
        private ImageView mImageView;

        public PartnersHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.partners_textview_name);
            mImageView = (ImageView) itemView.findViewById(R.id.partners_imageview);

            // Setting fonts
            Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

            mName.setTypeface(volvobroad_font);
            mName.setTextSize(20);
        }

        public void setPartner(Partners partner) {
            mPartner = partner;
        }

        @Override
        public void onClick(View view) {
            if (!mPartner.getPartnerUrl().equals("")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPartner.getPartnerUrl()));
                startActivity(browserIntent);
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.partner_no_website), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
