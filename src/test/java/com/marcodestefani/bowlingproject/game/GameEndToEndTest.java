package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.frame.FrameRepository;
import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.player.PlayerRepository;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.EndToEndTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameEndToEndTest extends EndToEndTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private FrameRepository frameRepository;

    @Test
    void startBowlingGameTest() throws Exception {
        PlayerDao playerDao = PlayerDao.builder().build();
        PlayerDao playerSaved = playerRepository.save(playerDao);
        Game game = Game.builder()
                .playerId(playerSaved.getId())
                .build();

        MockHttpServletRequestBuilder postRequstBuilder = post("/bowling/game")
                .content(asJsonString(game))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        this.mockMvc.perform(postRequstBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"playerId\":"+playerSaved.getId()+"}"));
    }

    @Test
    void calculateScoreTest() throws Exception {
        PlayerDao playerDao = PlayerDao.builder().build();
        PlayerDao playerSaved = playerRepository.save(playerDao);
        GameDao gameDao = GameDao.builder()
                .playerDao(playerSaved)
                .build();
        GameDao gameInserted = gameRepository.save(gameDao);

        FrameDao firstFrame = FrameDao.builder()
                .firstShot(8)
                .secondShot(2)
                .gameDao(gameDao)
                .build();
        FrameDao secondFrame = FrameDao.builder()
                .firstShot(4)
                .secondShot(3)
                .gameDao(gameDao)
                .build();
        frameRepository.save(firstFrame);
        frameRepository.save(secondFrame);

        MockHttpServletRequestBuilder getRequstBuilder = get("/bowling/game/"+gameInserted.getId()+"/score");


        this.mockMvc.perform(getRequstBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("21"));
    }
}
