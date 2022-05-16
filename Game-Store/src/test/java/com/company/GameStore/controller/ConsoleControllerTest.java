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
        // This is the standard set up method that runs before each test. It's typically used for instantiating test
        // objects. We don't have to do anything special for mockMvc since it's Autowired. We will however be using
        // setUp() in the future.
    }


    // testing GET /consoles
    @Test
    public void shouldReturnAllConsoles() throws Exception {

        // ARRANGE
        Console outputConsole = new Console();
        outputConsole.setConsole_id(1);
        outputConsole.setModel();
        outputConsole.setManufacturer();
        outputConsole.setMemory_amount();
        outputConsole.setProcessor();
        outputConsole.setPrice();
        outputConsole.setQuantity();

        String outputJson = mapper.writeValueAsString(outputConsole);

        // ARRANGE and ACT
        mockMvc.perform(get("/consoles"))       // Perform the GET request.
                .andDo(print())                          // Print results to console.
                .andExpect(status().isOk())              // ASSERT (status code is 200)

                // ASSERT that the JSON array is present and not empty. We will test GET all endpoints deeper in the
                // future but this is good enough for now.
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }


    //testing GET /consoles/{id}
    @Test
    public void shouldReturnConsoleById() throws Exception {

        String outputJson = mapper.writeValueAsString(outputConsole);

        // ACT
        mockMvc.perform(get("/consoles/1"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(outputJson));         // ASSERT that what we're expecting is what we got back.
    }

    // testing GET /consoles/manufacturer/{manufacturer}
    @Test
    public void shouldReturnConsoleByManufacturer() throws Exception {

        String outputJson = mapper.writeValueAsString(outputConsole);

        // ACT
        mockMvc.perform(get("/consoles/manufacturer/{manufacturer}"))
                .andDo(print())
                .andExpect(status().isOk())                     // ASSERT that we got back 200 OK.
                .andExpect(content().json(outputJson));         // ASSERT that what we're expecting is what we got back.
    }


}
