package com.company.GameStore.controller;

import com.company.GameStore.DTO.Invoice;
import com.company.GameStore.DTO.ProcessingFee;
import com.company.GameStore.DTO.SalesTaxRate;
import com.company.GameStore.service.ProcessingFeeLayer;
import com.company.GameStore.service.ServiceLayer;
import com.company.GameStore.service.TaxServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    @MockBean
    TaxServiceLayer taxServiceLayer;

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
        expectedInvoice1 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 1.49, 554.8);
        expectedInvoice2 = new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 14.49, 1109.96);

        inputtedInvoice = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10);

        invoiceList = Arrays.asList(expectedInvoice1, expectedInvoice2);

        SalesTaxRate salesTaxRate = new SalesTaxRate("TX",.03);

        when(serviceLayer.getAllInvoices()).thenReturn(invoiceList);
        when(serviceLayer.getInvoiceById(1)).thenReturn(Optional.of(expectedInvoice1));
        when(serviceLayer.addInvoice(inputtedInvoice)).thenReturn(expectedInvoice1);
        when(taxServiceLayer.findSalesTaxRateByState("TX")).thenReturn(salesTaxRate);
        when(taxServiceLayer.findSalesTaxRateByState("NOTASTATECODE")).thenReturn(null);
        when(serviceLayer.applyProcessingFee(inputtedInvoice)).thenReturn(1.49);
        when(serviceLayer.getItemQuantity(inputtedInvoice)).thenReturn(100);
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

    @Test
    public void shouldReturnStatus404ForInvalidStateCode() throws Exception {
        inputtedInvoice.setState("AA");

        inputtedJson = mapper.writeValueAsString(inputtedInvoice);

        mockMvc.perform(post("/invoices")
                .content(inputtedJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnStatus422ForQuantityThatIsLessThen1() throws Exception {
        inputtedInvoice.setQuantity(0);
        System.out.println(inputtedInvoice);
        inputtedJson = mapper.writeValueAsString(inputtedInvoice);

        mockMvc.perform(post("/invoices")
                        .content(inputtedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldReturnStatus406WithQuantityGreaterThanAvailable() throws Exception {
        inputtedInvoice.setQuantity(101);

        inputtedJson = mapper.writeValueAsString(inputtedInvoice);
        expectedJson = mapper.writeValueAsString(expectedInvoice1);

        mockMvc.perform(post("/invoices")
                        .content(inputtedJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
}