package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.EndToEndTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerEndToEndTest extends EndToEndTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldAddPlayer() throws Exception {
        Player player = Player.builder()
                .name("Mario")
                .lastname("Rossi")
                .build();
        MockHttpServletRequestBuilder postRequstBuilder = post("/players")
                .content(asJsonString(player))
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        this.mockMvc.perform(postRequstBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Mario\",\"lastname\":\"Rossi\"}"));
    }

    @Test
    public void shouldGetAllPlayers() throws Exception {
        PlayerDao player1 = PlayerDao.builder()
                .name("Paolo")
                .lastname("Bianchi")
                .build();
        PlayerDao player2 = PlayerDao.builder()
                .name("Giuseppe")
                .lastname("Verdi")
                .build();
        playerRepository.save(player1);
        playerRepository.save(player2);

        MockHttpServletRequestBuilder getRequestBuilder = get("/players")
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        this.mockMvc.perform(getRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Paolo\",\"lastname\":\"Bianchi\"},{\"name\":\"Giuseppe\",\"lastname\":\"Verdi\"}]"));
    }
}
