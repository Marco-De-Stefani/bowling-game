package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

class GameControllerTest extends UnitTest {
    @InjectMocks
    private GameController controller;

    @Mock
    private GameService gameService;


    @Test
    void gameControllerCallsServiceForStartingGame() {
        Game game = Game.builder().build();
        Game gameFromService = Game.builder().build();
        when(gameService.startGame(game)).thenReturn(gameFromService);

        Game gameReponse = controller.startBowlingGame(game);

        assertSame(gameFromService, gameReponse);
    }

    @Test
    void gameControllerCallsServiceForGettingScore() {
        when(gameService.calculateScore(123L)).thenReturn(100);

        int gameScore = controller.getGameScore(123L);

        assertEquals(100, gameScore);
    }
}
