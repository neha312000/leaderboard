package com.games.leaderboard.repository;

import com.games.leaderboard.pojos.LeaderBoardRecord;
import com.games.leaderboard.pojos.LeaderBoardRecordKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderBoardRepository extends JpaRepository<LeaderBoardRecord, LeaderBoardRecordKey>{

    List<LeaderBoardRecord> findAllByOrderByScoreDescTsAsc();
    Page<LeaderBoardRecord> findAllByOrderByScoreDescTsAsc(Pageable pageable);
    Page<LeaderBoardRecord> findAllByOrderByScoreAscTsAsc(Pageable pageable);

    LeaderBoardRecord findByPlayerID(Integer playerID);

    LeaderBoardRecord save(LeaderBoardRecord leaderBoardRecord);

}
