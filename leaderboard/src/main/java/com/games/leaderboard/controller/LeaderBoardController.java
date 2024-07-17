package com.games.leaderboard.controller;

import com.games.leaderboard.enums.SortKey;
import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.pojos.Player;
import com.games.leaderboard.service.LeaderBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/leaderboard")
@Log4j2
@CrossOrigin("*")
public class LeaderBoardController {

    @Autowired
    LeaderBoardService leaderBoardService;

    @GetMapping("/getLeaderBoard")
    public ResponseEntity<List<LeaderBoardRecord>> getLeaderBoard(@RequestParam(defaultValue= "1") Optional<Integer> page, @RequestParam(defaultValue = "5") Optional<Integer> batchSize, @RequestParam(defaultValue = "DESC") Optional<String> sortKey){
        ResponseEntity<List<LeaderBoardRecord>> leaderBoardDetails;
        try{
            if(batchSize.isEmpty()) {
                leaderBoardDetails = new ResponseEntity<>(leaderBoardService.getLeaderBoard(), HttpStatus.OK);
            } else{
                leaderBoardDetails = new ResponseEntity<>(leaderBoardService.getLeaderBoard(page.isEmpty()?0:page.get()-1, batchSize.get(), sortKey.isEmpty() ? String.valueOf(SortKey.DESC) : sortKey.get()), HttpStatus.OK);
            }
        } catch(Exception e){
            log.info("Exception occurred while fetching leaderbaord");
            leaderBoardDetails = new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return leaderBoardDetails;
    }

    @GetMapping("/viewProfile/{playerID}")
    public ResponseEntity<Player> getPlayerProfile(@PathVariable Integer playerID){
        ResponseEntity<Player> playerDetails;
        try{
            playerDetails = new ResponseEntity<>(leaderBoardService.getPlayerDetails(playerID),HttpStatus.OK);

        } catch(IllegalArgumentException e){
            log.info("The player ID has not been passed or is invalid. Please try again.");
            playerDetails = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            log.info("Some error has occurred while fetching the details.");
            playerDetails = new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return playerDetails;

    }

    @GetMapping("/viewRankAndScore/{playerID}")
    public ResponseEntity<LeaderBoardRecord> getRankAndScore(@PathVariable Integer playerID){
        ResponseEntity<LeaderBoardRecord> leaderBoardRecord;
        try{
            leaderBoardRecord = new ResponseEntity<>(leaderBoardService.getRankAndScore(playerID),HttpStatus.OK);

        } catch(IllegalArgumentException e){
            log.info("The player ID has not been passed or is invalid. Please try again.");
            leaderBoardRecord = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            log.info("Some error has occurred while fetching the details.");
            leaderBoardRecord = new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return leaderBoardRecord;
    }
}
