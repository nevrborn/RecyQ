package com.donkeymonkey.recyq.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.model.Leaderboard;
import com.donkeymonkey.recyq.model.LeaderboardEntry;
import com.donkeymonkey.recyq.model.User;

import java.util.List;

public class DialogLeaderboard extends DialogFragment {

    private User mUser;
    private Leaderboard mLeaderBoard;
    private List<LeaderboardEntry> mLeaderBoardList;

    private RecyclerView mRecyclerView;
    private LeaderboardAdapter mLeaderboardAdapter;

    private TextView mLeaderboardPositionText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_leaderboard, null);

        mUser = User.getInstance();
        mLeaderBoard = Leaderboard.getInstance();
        mLeaderBoardList = mLeaderBoard.getLeaderBoardList();

        mLeaderboardPositionText = (TextView) view.findViewById(R.id.leaderboard_text);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_leaderboard);

        // Setting fonts
        Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");
        Typeface pt_mono_bold_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono_bold.ttf");

        mLeaderboardPositionText.setTypeface(pt_mono_bold_font);

        TextView mTitle = new TextView(getActivity());
        mTitle.setText(R.string.leaderboard_title);
        mTitle.setBackgroundColor(Color.DKGRAY);
        mTitle.setPadding(10, 10, 10, 10);
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextColor(Color.WHITE);
        mTitle.setTextSize(30);
        mTitle.setTypeface(volvobroad_font);

        for (int i = 0; i < mLeaderBoardList.size(); i++) {

            if (mLeaderBoardList.get(i).getKey().equals(mUser.getUid())) {
                mLeaderboardPositionText.setText(getString(R.string.leaderboard_placing, i+1, mLeaderBoardList.size()));
            }
        }

        mLeaderboardPositionText.setTextSize(20);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLeaderboardAdapter = new LeaderboardAdapter(mLeaderBoard);
        mRecyclerView.setAdapter(mLeaderboardAdapter);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCustomTitle(mTitle)
                .setNegativeButton(R.string.ok, null)
                .create();
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

            if (leaderboardEntry.getKey() == mUser.getUid()) {
                holder.mName.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRedDark));
                holder.mKilos.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRedDark));
            } else {
                holder.mName.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGrey));
                holder.mKilos.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGrey));
            }
        }

        @Override
        public int getItemCount() {
            return mLeaderBoardList.size();
        }
    }

    public class LeaderboardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private LeaderboardEntry mLeaderboardEntry;

        private LinearLayout mLeaderboardCell;

        private TextView mLeaderboardPosition;
        private TextView mName;
        private TextView mKilos;

        public LeaderboardHolder(View itemView) {
            super(itemView);
            mLeaderboardCell = (LinearLayout) itemView.findViewById(R.id.leaderboard_cell);

            mLeaderboardPosition = (TextView) itemView.findViewById(R.id.leaderboard_textview_number);
            mName = (TextView) itemView.findViewById(R.id.leaderboard_textview_name);
            mKilos = (TextView) itemView.findViewById(R.id.leaderboard_textview_kilos);

            // Setting fonts
            Typeface pt_mono_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pt_mono.ttf");
            Typeface volvobroad_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/volvobroad.ttf");

            mLeaderboardPosition.setTypeface(volvobroad_font);
            mName.setTypeface(pt_mono_font);
            mKilos.setTypeface(pt_mono_font);

            mLeaderboardPosition.setTextSize(60);
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
