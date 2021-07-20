package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerServiceTest extends UnitTest {
    @InjectMocks
    private PlayerService service;

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PlayerMapper playerMapper;

    @Test
    void serviceCallsRepositoryForAddingPlayer() {
        Player player = Player.builder().build();
        PlayerDao playerDao = PlayerDao.builder().build();
        when(playerMapper.fromPlayer(player)).thenReturn(playerDao);

        service.add(player);

        verify(playerRepository).save(playerDao);
    }

    @Test
    void serviceCallsRepositoryForFindingPlayers() {
        PlayerDao firstPlayer = PlayerDao.builder().build();
        PlayerDao secondPlayer = PlayerDao.builder().build();
        when(playerRepository.findAll()).thenReturn(Arrays.asList(firstPlayer,secondPlayer));

        Player firstPlayerMapped = Player.builder().build();
        Player secondPlayerMapped = Player.builder().build();
        when(playerMapper.fromPlayerDao(firstPlayer)).thenReturn(firstPlayerMapped);
        when(playerMapper.fromPlayerDao(secondPlayer)).thenReturn(secondPlayerMapped);

        List<Player> players = service.getAllPlayers();
        assertEquals(2,players.size());
    }

    @Test
    void playerFoundIsTheOneReturnedByMapper() {
        PlayerDao player = PlayerDao.builder().build();
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player));

        Player firstPlayerMapped = Player.builder().build();
        when(playerMapper.fromPlayerDao(player)).thenReturn(firstPlayerMapped);

        List<Player> players = service.getAllPlayers();
        assertEquals(1,players.size());
        assertSame(firstPlayerMapped,players.get(0));
    }
}
