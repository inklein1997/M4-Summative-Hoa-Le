package com.company.GameStore.repository;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.DTO.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    InvoiceRepository invoiceRepository;

    Invoice invoice1;
    Invoice invoice2;
    private List<Invoice> expectedInvoiceList = new ArrayList<>();


    @Before
    public void setUp() {
        invoiceRepository.deleteAll();

        invoice1 = invoiceRepository.save(new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8));
        invoice2 = invoiceRepository.save(new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 29.98, 1109.96));

        expectedInvoiceList.clear();
    }


    @Test
    public void addGetDeleteGame() {
        Optional<Invoice> invoice = invoiceRepository.findById(invoice1.getInvoice_id());

        assertEquals(invoice1, invoice.get());

        invoiceRepository.deleteById(invoice1.getInvoice_id());
        invoice = invoiceRepository.findById(invoice1.getInvoice_id());

        assertFalse(invoice.isPresent());
    }

    @Test
    public void updateGame() {
        invoice1.setCity("Dallas");
        invoiceRepository.save(invoice1);
        Optional<Invoice> invoice = invoiceRepository.findById(invoice1.getInvoice_id());

        assertEquals(invoice.get(), invoice1);
    }

    @Test
    public void getAllGames() {
        expectedInvoiceList.add(invoice1);
        expectedInvoiceList.add(invoice2);

        List<Invoice> actualInvoiceList = invoiceRepository.findAll();

        assertEquals(actualInvoiceList.size(), 2);
        assertEquals(expectedInvoiceList, actualInvoiceList);
    }
}