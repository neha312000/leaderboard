package com.games.leaderboard.repository;

import com.games.leaderboard.pojos.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByPlayerID(Integer playerID);
}
