package com.marcodestefani.bowlingproject.player;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping(value = "/players", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Player addPlayer(@RequestBody Player player) {
        checkInputPlayerData(player);
        return playerService.add(player);
    }

    @GetMapping(value = "/players")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }


    private void checkInputPlayerData(Player player) {
        if(player.getName() == null){
            throw new RuntimeException("Player name not specified");
        }
        if(player.getLastname() == null){
            throw new RuntimeException("Player last name not specified");
        }
    }
}
