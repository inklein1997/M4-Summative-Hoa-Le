package com.company.GameStore.controller;

import com.company.GameStore.DTO.Invoice;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {
    @MockBean
    ServiceLayer serviceLayer;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    Invoice expectedInvoice1;
    Invoice expectedInvoice2;
    Invoice inputtedInvoice;
    List<Invoice> invoiceList;
    String expectedJson;
    String inputtedJson;

    @Before
    public void setUp() {
        serviceLayer.clearDatabase();
        setUpMocksForInvoiceRoutes();
    }

    private void setUpMocksForInvoiceRoutes() {
        expectedInvoice1 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        expectedInvoice2 = new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 29.98, 1109.96);

        inputtedInvoice = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);

        invoiceList = Arrays.asList(expectedInvoice1, expectedInvoice2);

        when(serviceLayer.getAllInvoices()).thenReturn(invoiceList);
        when(serviceLayer.getInvoiceById(1)).thenReturn(Optional.of(expectedInvoice1));
        when(serviceLayer.createInvoice(inputtedInvoice)).thenReturn(expectedInvoice1);
    }

    /* ============================= TESTING GET ROUTES ============================= */
    /* --------------------------------- HAPPY PATHS -------------------------------- */
    @Test
    public void shouldReturnListOfAllInvoicesAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(invoiceList);

        mockMvc.perform(get("/invoices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnInvoiceByIdAndStatus200() throws Exception {
        expectedJson = mapper.writeValueAsString(expectedInvoice1);

        mockMvc.perform(get("/invoices/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    /* ---------------------------------- SAD PATHS --------------------------------- */
    @Test
    public void shouldReturn404StatusCodeIfInvoiceIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/invoices/123546"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /* ============================= TESTING POST ROUTES ============================ */
    /* --------------------------------- HAPPY PATHS -------------------------------- */
    @Test
    public void shouldReturnInvoiceOnPostRequestAndStatus201() throws Exception {
        inputtedJson = mapper.writeValueAsString(inputtedInvoice);
        expectedJson = mapper.writeValueAsString(expectedInvoice1);

        mockMvc.perform(post("/invoices")
                .content(inputtedJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    /* ---------------------------------- SAD PATHS --------------------------------- */
    @Test
    public void shouldReturnStatus422ForInvalidRequestBodyOnPostRequest() throws Exception {
        HashMap<String, Object> invalidRequestBody = new HashMap();
        invalidRequestBody.put("id", Integer.parseInt("136"));
        invalidRequestBody.put("title", "FakeGameTitle1223");
        invalidRequestBody.put("releaseData", "2022-10-12");

        inputtedJson = mapper.writeValueAsString(invalidRequestBody);

        mockMvc.perform(post("/invoices")
                .content(inputtedJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}