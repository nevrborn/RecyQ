package com.donkeymonkey.recyq.model;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {

    private static Leaderboard mLeaderboard;
    private static List<LeaderboardEntry> mLeaderBoardList = new ArrayList<>();

    public static Leaderboard getInstance() {
        if (mLeaderboard == null) {
            Leaderboard mLeaderBoardList = new Leaderboard();
            setInstance(mLeaderBoardList);
        }

        return mLeaderboard;
    }

    public static void setInstance(Leaderboard leaderboard) {
        mLeaderboard = leaderboard;
    }

    public List<LeaderboardEntry> getLeaderBoardList() {
        sortList(mLeaderBoardList);
        return mLeaderBoardList;
    }

    public void setLeaderBoardList(List<LeaderboardEntry> leaderBoardList) {
        mLeaderBoardList = leaderBoardList;
    }

    public static void sortList(List<LeaderboardEntry> list) {
        Collections.sort(list, new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry leaderboardEntry, LeaderboardEntry t1) {
                return t1.getTotalKilos().compareTo(leaderboardEntry.getTotalKilos());
            }
        });
    }

    public void addLeaderBoardEntry(LeaderboardEntry leaderboardEntry) {
        mLeaderBoardList.add(leaderboardEntry);
    }

    public void clearLeaderBoard() {
        mLeaderBoardList.clear();
    }
}
