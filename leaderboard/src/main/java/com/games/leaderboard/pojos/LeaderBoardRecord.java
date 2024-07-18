package com.games.leaderboard.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(LeaderBoardRecordKey.class)
public class LeaderBoardRecord {

    @Id
    Integer playerID;
    long score;
    String playerName;
    @Id
    Timestamp ts;

}
