package com.company.GameStore.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.company.GameStore.DTO.Game;
import com.company.GameStore.service.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    @MockBean
    ServiceLayer serviceLayer;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        serviceLayer.clearDatabase();

    }

    /* ============================= TESTING GET ROUTES ============================= */
    /* --------------------------------- HAPPY PATHS -------------------------------- */

    @Test
    public void shouldReturnListOfAllGamesAndStatus200() throws Exception {

        List<Game> expectedGameList = Arrays.asList(
                new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15),
                new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7),
                new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5)
        );

        String expectedJson = mapper.writeValueAsString(expectedGameList);

        when(serviceLayer.getAllGames()).thenReturn(expectedGameList);

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

        when(serviceLayer.getGamesByStudio("Nintendo")).thenReturn(expectedGameListByStudio);

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

        when(serviceLayer.getGamesByEsrbRating("M (Mature)")).thenReturn(expectedGameListByEsrbRating);

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

        when(serviceLayer.getGamesByStudioAndEsrbRating("Nintendo", "E (Everyone)")).thenReturn(expectedGameListByStudioAndEsrbRating);

        mockMvc.perform(get("/games?studio=Nintendo&esrbRating=E (Everyone)"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnGameByTitleAndStatus200() throws Exception {
        Game expectedGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        when(serviceLayer.getGameByTitle("Nintendo Switch Sports")).thenReturn(Optional.of(expectedGame));

        mockMvc.perform(get("/games/title/Nintendo Switch Sports"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnGameByIdAndStatus200() throws Exception {
        Game expectedGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        String expectedJson = mapper.writeValueAsString(expectedGame);

        when(serviceLayer.getSingleGame(13)).thenReturn(Optional.of(expectedGame));

        mockMvc.perform(get("/games/13"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }

    /* ---------------------------------- SAD PATHS --------------------------------- */
    @Test
    public void shouldReturn404StatusCodeIfGameTitleDoesNotExist() throws Exception {
        mockMvc.perform(get("/games/title/FakeGame1231"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn404StatusCodeIfGameIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/games/10023"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /* ============================= TESTING POST ROUTES ============================= */
    /* --------------------------------- HAPPY PATHS -------------------------------- */
    @Test
    public void shouldReturnGameOnPostRequestAndStatus201() throws Exception {
        Game expectedGame = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game inputtedGame = new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        String expectedJson = mapper.writeValueAsString(expectedGame);
        String inputtedJson = mapper.writeValueAsString(inputtedGame);

        when(serviceLayer.addGame(inputtedGame)).thenReturn(expectedGame);

        mockMvc.perform(post("/games")
                    .content(inputtedJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isCreated());
    }

    /* ---------------------------------- SAD PATHS --------------------------------- */
    @Test
    public void ShouldReturnStatus422ForInvalidRequestBodyOnPostRequest() throws Exception {
        HashMap<String, Object> invalidRequestBody = new HashMap();
        invalidRequestBody.put("id", Integer.parseInt("136"));
        invalidRequestBody.put("title", "FakeGameTitle1223");
        invalidRequestBody.put("releaseData", "2022-10-12");

        String inputJson = mapper.writeValueAsString(invalidRequestBody);

        mockMvc.perform(post("/games")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    /* ============================= TESTING PUT ROUTES ============================= */
    /* --------------------------------- HAPPY PATHS -------------------------------- */
    @Test
    public void shouldRespondWithStatus204WithValidPutRequest() throws Exception {
        Game inputGame = new Game(13,"Nintendo Switch Sports", "M (Mature)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        String inputJson = mapper.writeValueAsString(inputGame);
        
        mockMvc.perform(put("/games/13")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    /* ---------------------------------- SAD PATHS --------------------------------- */
    @Test
    public void shouldReturn422StatusCodeIfGameIdsDoNotMatch() throws Exception {
        Game inputGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        String inputJson = mapper.writeValueAsString(inputGame);

        mockMvc.perform(put("/games/100")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test public void shouldReturn422StatusCodeIfRequestBodyIsInvalidForPutRequest() throws Exception {
        HashMap<String, Object> invalidRequestBody = new HashMap();
        invalidRequestBody.put("id", Integer.parseInt("136"));
        invalidRequestBody.put("title", "FakeGameTitle1223");
        invalidRequestBody.put("releaseData", "2022-10-12");

        String inputJson = mapper.writeValueAsString(invalidRequestBody);

        mockMvc.perform(put("/games/136")
                    .content(inputJson)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    /* ============================= TESTING PUT ROUTES ============================= */
    /* --------------------------------- HAPPY PATHS -------------------------------- */
    @Test
    public void shouldRespondWithStatus204WithValidDeleteRequest() throws Exception {
        mockMvc.perform(delete("/games/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    /* ---------------------------------- SAD PATHS --------------------------------- */
    @Test public void shouldResponseWithStatus404IfGameIdIsNotFoundForDelete() throws Exception {
        mockMvc.perform(delete("/games/1412"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}