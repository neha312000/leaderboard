package com.games.leaderboard.controller;

import com.games.leaderboard.pojos.Game;
import com.games.leaderboard.service.GameService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
@Log4j2
@CrossOrigin("*")
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("/getGames")
    public ResponseEntity<List<Game>> getGames(){
        try {
            return ResponseEntity.ok(gameService.getAllGames());
        } catch(Exception exception){
            ResponseEntity.badRequest();
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }


}
