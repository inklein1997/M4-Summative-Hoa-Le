package com.company.GameStore.controller;

import com.company.GameStore.DTO.ProcessingFee;
import com.company.GameStore.DTO.Tshirt;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@WebMvcTest(TshirtController.class)
public class TshirtControllerTest {

    @MockBean
    ServiceLayer service;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    public List<Tshirt> expectedTshirtList;
    private List<Tshirt> expectedTshirtListByColor;
    private List<Tshirt> expectedTshirtListBySize;
    private List<Tshirt> expectedTshirtListByColorAndSize;
    private Tshirt expectedTshirt;
    private Tshirt inputtedTshirt;
    private String expectedJson;
    private String inputtedJson;

    @Before
    public void setUp() {
        service.clearDatabase();
        setUpMocksForGetRoutesForTshirt();
    }

    private void setUpMocksForGetRoutesForTshirt(){
        Tshirt smallRedTshirtNoId = new Tshirt("small","red","A lovely red T-shirt",9.99,10);
        Tshirt smallRedTshirt = new Tshirt(1,"small","red","A lovely red T-shirt",9.99,10);
        Tshirt mediumBlueTshirt = new Tshirt(2,"medium","blue","A lovely blue T-shirt",9.99,10);
        Tshirt largeGreenTshirt = new Tshirt(3,"large","green","A lovely green T-shirt",9.99,10);

        expectedTshirtList = Arrays.asList(
                smallRedTshirt,
                mediumBlueTshirt,
                largeGreenTshirt
        );

        expectedTshirtListByColor = Arrays.asList(smallRedTshirt);

        expectedTshirtListBySize = Arrays.asList(mediumBlueTshirt);

        expectedTshirtListByColorAndSize = Arrays.asList(largeGreenTshirt);

        expectedTshirt = smallRedTshirt;
        inputtedTshirt = smallRedTshirt;

        when(service.getAllTshirt()).thenReturn(expectedTshirtList);
        when(service.getTshirtByColor("red")).thenReturn(expectedTshirtListByColor);
        when(service.getTshirtBySize("medium")).thenReturn(expectedTshirtListBySize);
        when(service.getTshirtByColorAndSize("green","large")).thenReturn(expectedTshirtListByColorAndSize);
        when(service.getSingleTshirt(1)).thenReturn(Optional.of(expectedTshirt));
        when(service.addTshirt(inputtedTshirt)).thenReturn(expectedTshirt);
    }
    // ---------------------- Working GET paths 200s ---------------------
    @Test
    public void shouldReturnListOfAllTshirtsWithStatus200()throws Exception{
        expectedJson = mapper.writeValueAsString(expectedTshirtList);

        mockMvc.perform(get("/tshirts"))
                .andDo(print())
                .andExpect(content().json(expectedJson))//result matcher
                .andExpect(status().isOk());

    }
    @Test
    public void shouldReturnListOfAllTshirtsSearchedByColorWithStatus200()throws Exception{
        expectedJson = mapper.writeValueAsString(expectedTshirtListByColor);

        mockMvc.perform(get("/tshirts?color=red"))
                .andDo(print())
                .andExpect(content().json(expectedJson))//result matcher
                .andExpect(status().isOk());

    }
    @Test
    public void shouldReturnListOfAllTshirtsSearchedBySizeWithStatus200()throws Exception{
        expectedJson = mapper.writeValueAsString(expectedTshirtListBySize);

        mockMvc.perform(get("/tshirts?size=medium"))
                .andDo(print())
                .andExpect(content().json(expectedJson))//result matcher
                .andExpect(status().isOk());

    }
    @Test
    public void shouldReturnListOfAllTshirtsSearchedByColorAndSizeWithStatus200()throws Exception{
        expectedJson = mapper.writeValueAsString(expectedTshirtListByColorAndSize);

        mockMvc.perform(get("/tshirts?color=green&size=large"))
                .andDo(print())
                .andExpect(content().json(expectedJson))//result matcher
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnTshirtByIdWithStatus200()throws Exception {
        expectedJson = mapper.writeValueAsString(expectedTshirt);

        mockMvc.perform(get("/tshirts/1"))
                .andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isOk());
    }
    //--------------------- Failing GET Path 400s ------------------------
    @Test
    public void shouldReturn404WhenFindTshirtByInvalidId() throws Exception{
        mockMvc.perform(get("/tshirts/1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //---------------------- Working POST Paths ------------------------------

    @Test
    public void shouldCreateTshirtOnPostRequest() throws Exception{
        expectedJson = mapper.writeValueAsString(expectedTshirt);
        inputtedJson = mapper.writeValueAsString(inputtedTshirt);

        mockMvc.perform(post("/tshirts")
                .content(inputtedJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(content().json(expectedJson))
                .andExpect(status().isCreated());
    }
    //---------------------- Failing POST POST ------------------------------

    @Test
    public void shouldReturnStatus422ForInvalidRequestBodyOnPostRequest() throws Exception{
        HashMap<String, Object> invalidRequestBody = new HashMap<>();
        invalidRequestBody.put("id",Integer.parseInt("1000"));
        invalidRequestBody.put("color","silver");
        invalidRequestBody.put("size","Very Big");
        inputtedJson = mapper.writeValueAsString(invalidRequestBody);

        mockMvc.perform(post("/tshirts")
                        .content(inputtedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
    //------------------------------ Working PUT Paths ----------------------
    @Test
    public void shouldRespondWithStatus204WithValidPutRequest() throws  Exception{
        inputtedJson = mapper.writeValueAsString(inputtedTshirt);

        mockMvc.perform(put("/tshirts/1")
                .content(inputtedJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isNoContent());
    }

    //------------------------------ Failing PUT Paths ----------------------
    @Test
    public void shouldRetrun422StatusIfTshirtIdDoNotMatch() throws Exception{
        inputtedJson = mapper.writeValueAsString(inputtedTshirt);

        mockMvc.perform(put("/tshirts/1000")
                        .content(inputtedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    // review
    @Test public void shouldReturn422StatusCodeIfRequestBodyIsInvalidForPutRequest() throws Exception {
        HashMap<String, Object> invalidRequestBody = new HashMap<>();
        invalidRequestBody.put("id",Integer.parseInt("333"));
        invalidRequestBody.put("color",null);
        invalidRequestBody.put("price","55");
        inputtedJson = mapper.writeValueAsString(invalidRequestBody);

        mockMvc.perform(put("/tshirts/333")
                        .content(inputtedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    //------------------------------- Working DELETE Paths -----------------

    @Test
    public void shouldRespondWithStatus204WithValidDeleteRequest() throws Exception {
        mockMvc.perform(delete("/tshirts/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
    //------------------------------- Failing DELETE Paths -----------------

    @Test
    public void shouldRespondWithStatus404WithTshirtIdNotFoundDeleteRequest() throws Exception {
        mockMvc.perform(delete("/tshirts/1000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }





}

