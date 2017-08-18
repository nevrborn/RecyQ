package com.donkeymonkey.recyq.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
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
import com.donkeymonkey.recyq.model.Leaderboard;
import com.donkeymonkey.recyq.model.LeaderboardEntry;
import com.donkeymonkey.recyq.model.StoreItem;

import java.util.List;
import java.util.Map;

public class LeaderboardFragment extends Fragment {

    private Leaderboard mLeaderBoard;
    private List<LeaderboardEntry> mLeaderBoardList;

    private RecyclerView mRecyclerView;
    private LeaderboardAdapter mLeaderboardAdapter;

    public static LeaderboardFragment newInstance() {
        return new LeaderboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        mLeaderBoard = Leaderboard.getInstance();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_leaderboard);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLeaderboardAdapter = new LeaderboardAdapter(mLeaderBoard);
        mRecyclerView.setAdapter(mLeaderboardAdapter);

        return view;
    }

    // MARK - Recycler view methods

    public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardHolder> {


        public LeaderboardAdapter(Leaderboard leaderboard) {
            mLeaderBoardList = leaderboard.getLeaderBoardList();
        }

        @Override
        public LeaderboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.viewholder_leaderboard, parent, false);
            return new LeaderboardHolder(itemView);
        }

        @Override
        public void onBindViewHolder(LeaderboardHolder holder, int position) {
            LeaderboardEntry leaderboardEntry = mLeaderBoardList.get(position);
            holder.setLeaderboardEntry(leaderboardEntry);
            holder.itemView.setOnClickListener(holder);

            holder.mLeaderboardPosition.setText(String.valueOf(position+1));
            holder.mName.setText(leaderboardEntry.getName());
            holder.mKilos.setText(getString(R.string.leaderboard_kilos, leaderboardEntry.getTotalKilos()));
        }

        @Override
        public int getItemCount() {
            return mLeaderBoardList.size();
        }
    }

    public class LeaderboardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LeaderboardEntry mLeaderboardEntry;

        private TextView mLeaderboardPosition;
        private TextView mName;
        private TextView mKilos;

        public LeaderboardHolder(View itemView) {
            super(itemView);
            mLeaderboardPosition = (TextView) itemView.findViewById(R.id.leaderboard_textview_number);
            mName = (TextView) itemView.findViewById(R.id.leaderboard_textview_name);
            mKilos = (TextView) itemView.findViewById(R.id.leaderboard_textview_kilos);

            // Setting fonts
            Typeface pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
            Typeface volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

            mLeaderboardPosition.setTypeface(volvobroad_font);
            mName.setTypeface(pt_mono_font);
            mKilos.setTypeface(pt_mono_font);

            mLeaderboardPosition.setTextSize(22);
            mName.setTextSize(18);
            mKilos.setTextSize(18);
        }

        public void setLeaderboardEntry(LeaderboardEntry leaderboardEntry) {
            mLeaderboardEntry = leaderboardEntry;
        }

        @Override
        public void onClick(View view) {

        }
    }

}
