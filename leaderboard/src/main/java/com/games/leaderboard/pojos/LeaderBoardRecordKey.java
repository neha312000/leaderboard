package com.games.leaderboard.pojos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class LeaderBoardRecordKey implements Serializable {
    private Integer playerID;
    private Timestamp ts;

    public LeaderBoardRecordKey() {}

    public LeaderBoardRecordKey(Integer playerID, Timestamp ts) {
        this.playerID = playerID;
        this.ts = ts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaderBoardRecordKey that = (LeaderBoardRecordKey) o;
        return Objects.equals(playerID, that.playerID) && Objects.equals(ts, that.ts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, ts);
    }

}
