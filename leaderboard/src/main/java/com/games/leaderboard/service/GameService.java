package com.games.leaderboard.service;

import com.games.leaderboard.pojos.Game;
import com.games.leaderboard.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }
}
