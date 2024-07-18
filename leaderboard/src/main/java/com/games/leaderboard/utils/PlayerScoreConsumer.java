package com.games.leaderboard.utils;

import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.service.LeaderBoardService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class PlayerScoreConsumer {

    @Autowired
    LeaderBoardService leaderBoardService;

    private long lastKnownPosition = 0;
    private static final String FILE_PATH = "D:\\leaderboard\\leaderboard\\src\\main\\resources\\playerScoreInfoEvent.txt";

    @PostConstruct
    public void init() {
        // Initial load of scores if needed
        readScoresFromFile();
    }

    @Scheduled(fixedRate = 1000)
    public void readScoresFromFile() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(Paths.get(FILE_PATH).toFile(), "r")) {
            randomAccessFile.seek(lastKnownPosition);

            String line;
            while ((line = randomAccessFile.readLine()) != null) {
                line = line.trim(); // Trim any leading or trailing whitespace
                if (!line.isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && !parts[0].isEmpty() && !parts[1].isEmpty() && !parts[2].isEmpty()) {
                        try {
                            LeaderBoardRecord leaderBoardRecord = new LeaderBoardRecord();
                            leaderBoardRecord.setPlayerID(Integer.parseInt(parts[0]));
                            leaderBoardRecord.setScore(Long.parseLong(parts[1]));
                            leaderBoardRecord.setPlayerName(parts[2]);
                            leaderBoardRecord.setTs(Timestamp.valueOf(LocalDateTime.now()));
                            leaderBoardService.saveLeaderBoardRecord(leaderBoardRecord);
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing line: " + line + ". Skipping this line.");
                        }
                    } else {
                        System.err.println("Invalid line format: " + line + ". Skipping this line.");
                    }
                    lastKnownPosition = randomAccessFile.getFilePointer(); // Update last known position after processing the line
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
