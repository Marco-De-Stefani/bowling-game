package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.game.dao.GameDao;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public Game fromDao(GameDao gameDao) {
        return Game.builder()
                .id(gameDao.getId())
                .playerId(gameDao.getPlayerDao().getId())
                .build();
    }


}
