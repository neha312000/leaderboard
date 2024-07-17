package com.games.leaderboard.utils;

import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.service.LeaderBoardService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Component
public class PlayerScoreConsumer {

    @Autowired
    LeaderBoardService leaderBoardService;


    @PostConstruct
    public void init() {
        // Initial load of scores if needed
        readScoresFromFile();
    }

    @Scheduled(fixedRate = 1000)
    public void readScoresFromFile() {
        LeaderBoardRecord leaderBoardRecord = new LeaderBoardRecord();
        try (Stream<String> stream = Files.lines(Paths.get("D:\\leaderboard\\leaderboard\\src\\main\\resources\\playerScoreInfoEvent.txt"))) {
            stream.forEach(line -> {
                String[] parts = line.split(",");
                leaderBoardRecord.setPlayerID(Integer.parseInt(parts[0]));
                leaderBoardRecord.setScore(Long.parseLong(parts[1]));
                leaderBoardRecord.setPlayerName(parts[2]);
                leaderBoardRecord.setTs(Timestamp.valueOf(LocalDateTime.now()));
                leaderBoardService.saveLeaderBoardRecord(leaderBoardRecord);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
