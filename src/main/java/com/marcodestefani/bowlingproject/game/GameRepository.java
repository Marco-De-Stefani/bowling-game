package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.game.dao.GameDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<GameDao, Long> {
}
