package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void repositorySavePlayerInDatabase() {
        PlayerDao playerDao = PlayerDao.builder()
                .name("Name")
                .lastname("Lastname")
                .build();
        playerRepository.save(playerDao);

        List<PlayerDao> allPlayers = playerRepository.findAll();

        assertEquals(1, allPlayers.size());
        PlayerDao insertedPlayer = allPlayers.get(0);
        assertEquals("Name", insertedPlayer.getName());
        assertEquals("Lastname", insertedPlayer.getLastname());
    }
}
