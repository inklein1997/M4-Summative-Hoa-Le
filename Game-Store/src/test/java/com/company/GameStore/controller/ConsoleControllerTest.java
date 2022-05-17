package com.company.GameStore.controller;


import com.company.GameStore.DTO.Console;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {
    @MockBean
    ServiceLayer serviceLayer;

    // Wiring in the MockMvc object
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<Game> expectedGameList;
    private List<Game> expectedConsoleListByManufacturer;
    private Console expectedConsole;
    private Console inputtedConsole;
    private String expectedJson;
    private String inputtedJson;




    @Before
    public void setUp() {
        serviceLayer.clearDatabase();
        setUpMocksForGetRoutes();
    }

    private void setUpMocksForGetRoutes() {
        expectedGameList = Arrays.asList(
                new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15),
                new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7),
                new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5)
        );

        expectedGameListByStudio = Arrays.asList(
                new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15),
                new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7)
        );



        expectedGame = new Game(13,"Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        inputtedGame = new Game(13, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);

        when(serviceLayer.getAllGames()).thenReturn(expectedGameList);
        when(serviceLayer.getGamesByStudio("Nintendo")).thenReturn(expectedGameListByStudio);
        when(serviceLayer.getGameByTitle("Nintendo Switch Sports")).thenReturn(Optional.of(expectedGame));
        when(serviceLayer.getSingleGame(13)).thenReturn(Optional.of(expectedGame));
        when(serviceLayer.addGame(inputtedGame)).thenReturn(expectedGame);

    }


    // testing GET /consoles
    @Test
    public void shouldReturnAllConsoles() throws Exception {

        mockMvc.perform(get("/consoles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }


    //testing GET /consoles/{id}
    @Test
    public void shouldReturnConsoleById() throws Exception {
// ARRANGE
        Console outputConsole = new Console();
        outputConsole.setConsole_id(1);
        outputConsole.setModel("ipod");
        outputConsole.setManufacturer("Samsung");
        outputConsole.setMemory_amount("500GB");
        outputConsole.setProcessor("AMV rising 7");
        outputConsole.setPrice(59.0);
        outputConsole.setQuantity(80);

        String outputJson = mapper.writeValueAsString(outputConsole);

        // ACT
        mockMvc.perform(get("/consoles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    // testing GET /consoles/manufacturer/{manufacturer}
    @Test
    public void shouldReturnConsoleByManufacturer() throws Exception {
        // ARRANGE
        Console outputConsole = new Console();
        outputConsole.setConsole_id(1);
        outputConsole.setModel("ipod");
        outputConsole.setManufacturer("Samsung");
        outputConsole.setMemory_amount("500GB");
        outputConsole.setProcessor("AMV rising 7");
        outputConsole.setPrice(59.0);
        outputConsole.setQuantity(80);



        String outputJson = mapper.writeValueAsString(outputConsole);

        // ACT
        mockMvc.perform(get("/consoles/manufacturer/{manufacturer}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    // Testing POST /consoles
    @Test
    public void shouldReturnNewConsoledOnPostRequest() throws Exception {

        // ARRANGE
        Console inputConsole = new Console();
        inputConsole.setModel("ipod");
        inputConsole.setManufacturer("Samsung");
        inputConsole.setMemory_amount("900GB");
        inputConsole.setProcessor("AMV rising 7");
        inputConsole.setPrice(89.0);
        inputConsole.setQuantity(40);


        // Convert Java Object to JSON.
        String inputJson = mapper.writeValueAsString(inputConsole);

        Console outputConsole = new Console();
        outputConsole.setConsole_id(2);
        outputConsole.setModel("ipod");
        outputConsole.setManufacturer("Samsung");
        outputConsole.setMemory_amount("900GB");
        outputConsole.setProcessor("AMV rising 7");
        outputConsole.setPrice(89.0);
        outputConsole.setQuantity(40);

        String outputJson = mapper.writeValueAsString(outputConsole);

        // ACT
        mockMvc.perform(
                        post("/consoles")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    // testing PUT /consoles/{id}
    @Test
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception {

        // ARRANGE
        Console inputConsole = new Console();
        inputConsole.setModel("ipod");
        inputConsole.setManufacturer("Microsoft");
        inputConsole.setMemory_amount("900GB");
        inputConsole.setProcessor("AMV rising 7");
        inputConsole.setPrice(89.0);
        inputConsole.setQuantity(40);

        String inputJson = mapper.writeValueAsString(inputConsole);

        // ACT
        mockMvc.perform(
                        put("/consoles/2")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());

        // ACT
        mockMvc.perform(
                        get("/consoles/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(content().json(inputJson));
    }
    // testing DELETE /consoles/{id}
    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {

        mockMvc.perform(delete("/consoles/3"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }




}
