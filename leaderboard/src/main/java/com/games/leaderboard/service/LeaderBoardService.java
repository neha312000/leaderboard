package com.games.leaderboard.service;

import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.pojos.Player;
import com.games.leaderboard.repository.LeaderBoardRepository;
import com.games.leaderboard.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderBoardService {

    @Autowired
    LeaderBoardRepository leaderBoardRepository;

    @Autowired
    PlayerRepository playerRepository;

    public List<LeaderBoardRecord> getLeaderBoard(){
        List<LeaderBoardRecord> leaderBoardRecords = leaderBoardRepository.findAllByOrderByScoreDescTsAsc();
        //LeaderBoard leaderBoard= new LeaderBoard();
        //leaderBoard.setLeaderBoardRecords(leaderBoardRecords);
        return leaderBoardRecords;
    }
    public List<LeaderBoardRecord> getLeaderBoard(int pageNumber, int batchSize, String sortKey){
        Pageable pageable = PageRequest.of(pageNumber, batchSize);
        Page<LeaderBoardRecord> leaderBoardRecords;
        if(sortKey.equals("ASC")){
            leaderBoardRecords = leaderBoardRepository.findAllByOrderByScoreAscTsAsc(pageable);

        } else {
            leaderBoardRecords = leaderBoardRepository.findAllByOrderByScoreDescTsAsc(pageable);
        }
        //LeaderBoard leaderBoard= new LeaderBoard();
        //leaderBoard.setLeaderBoardRecords(leaderBoardRecords.getContent());
        return leaderBoardRecords.getContent();
    }
    public Player getPlayerDetails(Integer playerID){

        return playerRepository.findByPlayerID(playerID);
    }
    public LeaderBoardRecord getRankAndScore(Integer playerID){
        return leaderBoardRepository.findByPlayerID(playerID);
    }

    public void saveLeaderBoardRecord(LeaderBoardRecord leaderBoardRecord){
         leaderBoardRepository.save(leaderBoardRecord);
    }
}
