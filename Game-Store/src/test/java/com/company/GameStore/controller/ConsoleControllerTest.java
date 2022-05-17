//package com.company.GameStore.controller;
//
//
//import com.company.GameStore.DTO.Console;
//import com.company.GameStore.DTO.Game;
//import com.company.GameStore.service.ServiceLayer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(ConsoleController.class)
//public class ConsoleControllerTest {
//    @MockBean
//    ServiceLayer serviceLayer;
//
//    // Wiring in the MockMvc object
//    @Autowired
//    private MockMvc mockMvc;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    private List<Game> expectedConsoleList;
//    private List<Game> expectedConsoleListByManufacturer;
//    private Console expectedConsole;
//    private Console inputtedConsole;
//    private String expectedJson;
//    private String inputtedJson;
//
//
//
//
//    @Before
//    public void setUp() {
//        serviceLayer.clearDatabase();
//        setUpMocksForGetRoutes();
//    }
//
//    private void setUpMocksForGetRoutes() {
//        expectedConsoleList = (List<Game>) Arrays.asList(
//                new Console(),
//                new Console(),
//                new Console()
//        );
//
//        expectedConsoleListByManufacturer = Arrays.asList(
//                new Console(),
//                new Console(2)
//        );
//
//
//
//        expectedConsole = new Console();
//
//        inputtedConsole = new Console();
//
//        when(serviceLayer.getAllConsoles()).thenReturn(expectedConsoleList);
//        when(serviceLayer.getConsolesByManufacturer()).thenReturn(expectedConsoleListByManufacturer);
//        when(serviceLayer.getSingleConsole()).thenReturn(Optional.of(expectedConsole));
//        when(serviceLayer.addGame(inputtedConsole)).thenReturn(expectedConsole);
//
//    }
//
//
//    //Testing GET Routes for Successful routing
//    @Test
//    public void shouldReturnListOfAllConsolesAndStatus200() throws Exception {
//        expectedJson = mapper.writeValueAsString(expectedConsoleList);
//
//        mockMvc.perform(get("/consoles"))
//                .andDo(print())
//                .andExpect(content().json(expectedJson))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void shouldReturnListOfGamesFilteredByStudioAndStatus200() throws Exception {
//        expectedJson = mapper.writeValueAsString(expectedConsoleListByManufacturer);
//
//        mockMvc.perform(get("/consoles/manufacturer/Sony"))
//                .andDo(print())
//                .andExpect(content().json(expectedJson))
//                .andExpect(status().isOk());
//    }
//
//
//    @Test
//    public void shouldReturnGameByIdAndStatus200() throws Exception {
//        expectedJson = mapper.writeValueAsString(expectedConsole);
//
//        mockMvc.perform(get("/consoles/3"))
//                .andDo(print())
//                .andExpect(content().json(expectedJson))
//                .andExpect(status().isOk());
//    }
//
//
//    // testing DELETE /consoles/{id}
//    @Test
//    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
//
//        mockMvc.perform(delete("/consoles/3"))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//    }
//
//
//
//
//}
