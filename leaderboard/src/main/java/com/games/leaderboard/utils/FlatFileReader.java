package com.games.leaderboard.utils;

import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.service.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class FlatFileReader {
    @Autowired
    LeaderBoardService leaderBoardService;

    public void readFile(String filePath){
        LeaderBoardRecord leaderBoardRecord = new LeaderBoardRecord();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                leaderBoardRecord.setPlayerID(Integer.parseInt(parts[1]));
                leaderBoardRecord.setScore(Long.parseLong(parts[2]));
                leaderBoardService.saveLeaderBoardRecord(leaderBoardRecord);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
