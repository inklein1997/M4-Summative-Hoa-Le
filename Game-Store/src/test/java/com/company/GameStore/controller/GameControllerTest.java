package com.company.GameStore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

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

    }

    @Test
    public void shouldReturnListOfGamesFilteredByStudioAndStatus200() throws Exception {

    }

    @Test
    public void shouldReturnListOfGamesFilteredByEsrbRatingAndStatus200() throws Exception {

    }

    @Test
    public void shouldReturnListOfGamesFilteredByStudioAndEsrbRatingAndStatus200() throws Exception {

    }

    @Test
    public void shouldReturnGameByTitleAndStatus200() throws Exception {

    }

    @Test
    public void shouldReturnGameByIdAndStatus200() throws Exception {

    }

    /* ============================= TESTING POST ROUTES ============================= */
    @Test
    public void shouldReturnCreatedGameAndStatus201() throws Exception {

    }

    /* ============================= TESTING PUT ROUTES ============================= */
    @Test
    public void shouldRespondWithStatus204WithValidRequest() throws Exception {

    }

    /* ============================= TESTING PUT ROUTES ============================= */
    @Test
    public void shouldRespondWithStatus204WithValidRequest() throws Exception {
        
    }
}