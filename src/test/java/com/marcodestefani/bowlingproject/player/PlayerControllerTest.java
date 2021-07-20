package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayerControllerTest extends UnitTest {

    @InjectMocks
    private PlayerController controller;

    @Mock
    private PlayerService playerService;

    @Test
    void controllerCallsServiceForAddingPlayer() {
        Player player = Player.builder()
                .name("Mario")
                .lastname("Rossi")
                .build();
        Player insertedPlayer = Player.builder()
                .id(123L)
                .name("Mario")
                .lastname("Rossi")
                .build();
        when(playerService.add(player)).thenReturn(insertedPlayer);

        Player responsePlayer = controller.addPlayer(player);

        assertSame(responsePlayer,insertedPlayer);
    }

    @Test
    void notStartingGameIfPlayerWithoutName() {
        Player player = Player.builder()
                .name("Mario")
                .build();

        assertThrows(RuntimeException.class,
                () -> controller.addPlayer(player),
                "Player name not specified");
    }

    @Test
    void notStartingGameIfPlayerWithoutLastname() {
        Player player = Player.builder()
                .lastname("Rossi")
                .build();

        assertThrows(RuntimeException.class,
                () -> controller.addPlayer(player),
                "Player last name not specified");
    }

    @Test
    void controllerCallsServiceToGetAllPlayers() {
        Player player = Player.builder()
                .name("Mario")
                .lastname("Rossi")
                .build();
        when(playerService.getAllPlayers()).thenReturn(Arrays.asList(player));

        List<Player> allPlayers = controller.getAllPlayers();

        assertEquals(1,allPlayers.size());
        assertSame(player,allPlayers.get(0));
    }
}
