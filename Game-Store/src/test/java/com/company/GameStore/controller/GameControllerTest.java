package com.company.GameStore.controller;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {

    }

    /* ============================= TESTING GET ROUTES ============================= */
    @Test
    public void shouldReturnListOfAllGamesAndStatus200() throws Exception {

        List<Game> expectedGameList = Arrays.asList(
                new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15),
                new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7),
                new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5)
        );

        String expectedJson = mapper.writeValueAsString(expectedGameList);

        mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnListOfGamesFilteredByStudioAndStatus200() throws Exception {
        List<Game> expectedGameListByStudio = Arrays.asList(
                new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15),
                new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7)
        );

        String expectedJson = mapper.writeValueAsString(expectedGameListByStudio);

        mockMvc.perform(get("/games?studio=Nintendo"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnListOfGamesFilteredByEsrbRatingAndStatus200() throws Exception {
        List<Game> expectedGameListByEsrbRating = Arrays.asList(
                new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7)
        );

        String expectedJson = mapper.writeValueAsString(expectedGameListByEsrbRating);

        mockMvc.perform(get("/games?esrbRating=M (Mature)"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnListOfGamesFilteredByStudioAndEsrbRatingAndStatus200() throws Exception {
        List<Game> expectedGameListByStudioAndEsrbRating = Arrays.asList(
                new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15)
        );

        String expectedJson = mapper.writeValueAsString(expectedGameListByStudioAndEsrbRating);

        mockMvc.perform(get("/games?studio=Nintendo&esrbRating=E (Everyone)"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnGameByTitleAndStatus200() throws Exception {
        Game expectedGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/games/title/Nintendo Switch Sports"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnGameByIdAndStatus200() throws Exception {
        Game expectedGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        mockMvc.perform(get("/games/13"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    /* ============================= TESTING POST ROUTES ============================= */
    @Test
    public void shouldReturnCreatedGameAndStatus201() throws Exception {

    }

    /* ============================= TESTING PUT ROUTES ============================= */
    @Test
    public void shouldRespondWithStatus204WithValidPutRequest() throws Exception {

    }

    /* ============================= TESTING PUT ROUTES ============================= */
    @Test
    public void shouldRespondWithStatus204WithValidDeleteRequest() throws Exception {

    }
}