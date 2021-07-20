package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameMapperTest extends UnitTest {
    @InjectMocks
    private GameMapper gameMapper;

    @Test
    void gameMapperMapsAllFields() {
        GameDao gameDao = GameDao.builder()
                .id(1L)
                .playerDao(PlayerDao.builder().id(123L).build())
                .build();
        Game game = gameMapper.fromDao(gameDao);

        assertEquals(1L, game.getId());
        assertEquals(123L,game.getPlayerId());
    }
}
