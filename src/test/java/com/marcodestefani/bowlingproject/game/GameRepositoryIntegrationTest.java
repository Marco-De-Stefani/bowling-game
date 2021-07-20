package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.player.PlayerRepository;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GameRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void gameIsActuallySavedOnDatabase() {
        PlayerDao insertedPlayer = createPlayerForGame();
        GameDao gameDao = GameDao.builder()
                .ended(false)
                .playerDao(insertedPlayer)
                .build();

        GameDao gameInserted = gameRepository.save(gameDao);

        List<GameDao> allGames = gameRepository.findAll();
        assertEquals(1, allGames.size());
        assertFalse(allGames.get(0).isEnded());
        assertEquals(gameInserted.getId(), allGames.get(0).getId());
    }

    @Test
    void gameIsFoundOnDatabase() {
        PlayerDao insertedPlayer = createPlayerForGame();
        GameDao gameDao = GameDao.builder()
                .playerDao(insertedPlayer)
                .build();
        GameDao gameInserted = gameRepository.save(gameDao);

        Optional<GameDao> gameFound = gameRepository.findById(gameInserted.getId());
        assertTrue(gameFound.isPresent());
        assertEquals(gameInserted.getId(),gameFound.get().getId());
    }

    private PlayerDao createPlayerForGame() {
        return playerRepository.save(PlayerDao.builder().build());
    }


}
