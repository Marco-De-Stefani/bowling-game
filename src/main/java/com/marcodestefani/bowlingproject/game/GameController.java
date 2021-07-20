package com.marcodestefani.bowlingproject.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/bowling/game")
    public Game startBowlingGame(@RequestBody Game game) {
        return gameService.startGame(game);
    }

    @GetMapping("/bowling/game/{gameId}/score")
    public int getGameScore(@PathVariable("gameId") Long gameId){
        return gameService.calculateScore(gameId);
    }


}
