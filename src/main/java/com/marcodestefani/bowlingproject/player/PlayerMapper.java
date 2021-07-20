package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDao fromPlayer(Player player) {
        return PlayerDao.builder()
                .name(player.getName())
                .lastname(player.getLastname())
                .build();
    }

    public Player fromPlayerDao(PlayerDao playerDao) {
        return Player.builder()
                .id(playerDao.getId())
                .name(playerDao.getName())
                .lastname(playerDao.getLastname())
                .build();
    }
}
