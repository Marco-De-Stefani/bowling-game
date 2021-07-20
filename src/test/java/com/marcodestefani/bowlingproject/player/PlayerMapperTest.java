package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerMapperTest {

    private PlayerMapper playerMapper = new PlayerMapper();

    @Test
    void mappingFromPlayerToDao() {
        Player player = Player.builder()
                .name("Mario")
                .lastname("Rossi")
                .build();

        PlayerDao playerDao = playerMapper.fromPlayer(player);

        assertEquals("Mario", playerDao.getName());
        assertEquals("Rossi", playerDao.getLastname());
    }

    @Test
    void mappingFromDaoToPlayer() {
        PlayerDao playerDao = PlayerDao.builder()
                .id(123L)
                .name("Mario")
                .lastname("Rossi")
                .build();

        Player player = playerMapper.fromPlayerDao(playerDao);

        assertEquals(123L, player.getId());
        assertEquals("Mario", player.getName());
        assertEquals("Rossi", player.getLastname());
    }

}
