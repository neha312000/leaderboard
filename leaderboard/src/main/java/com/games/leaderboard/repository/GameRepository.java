package com.games.leaderboard.repository;

import com.games.leaderboard.pojos.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAll();
}
