package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.game.GameRepository;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.player.Player;
import com.marcodestefani.bowlingproject.player.PlayerRepository;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.EndToEndTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FrameEndToEndTest extends EndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void addFrameEndToEndTest() throws Exception {
        PlayerDao playerDao = PlayerDao.builder().build();
        PlayerDao playerSaved = playerRepository.save(playerDao);
        GameDao gameDao = GameDao.builder()
                .playerDao(playerSaved)
                .build();
        GameDao gameSaved = gameRepository.save(gameDao);

        Frame frame = Frame.builder()
                .firstShot(1)
                .secondShot(2)
                .thirdShot(3)
                .build();

        MockHttpServletRequestBuilder postRequstBuilder = post("/bowling/games/" + gameSaved.getId() + "/frames/")
                .content(asJsonString(frame))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        this.mockMvc.perform(postRequstBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"firstShot\":1,\"secondShot\":2,\"thirdShot\":3,\"strike\":false,\"spare\":false,\"frameEnded\":true,\"finalFrame\":true}"));

    }
}
