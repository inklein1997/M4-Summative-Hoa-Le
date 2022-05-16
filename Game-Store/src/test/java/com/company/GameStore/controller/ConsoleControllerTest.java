package com.company.GameStore.controller;


import com.company.GameStore.DTO.Console;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {

    // Wiring in the MockMvc object
    @Autowired
    private MockMvc mockMvc;

    // ObjectMapper used to convert Java objects to JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {

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
    public void shouldReturnNewRecordOnPostRequest() throws Exception {

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
        inputConsole.setManufacturer("Samsung");
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
