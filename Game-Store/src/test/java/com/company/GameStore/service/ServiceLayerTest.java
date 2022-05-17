package com.company.GameStore.service;

import com.company.GameStore.DTO.*;
import com.company.GameStore.repository.*;
import com.company.GameStore.repository.ConsoleRepository;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.InvoiceRepository;
import com.company.GameStore.repository.TshirtRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {
    ServiceLayer serviceLayer;
    GameRepository gameRepository;
    ConsoleRepository consoleRepository;
    TshirtRepository tshirtRepository;
    InvoiceRepository invoiceRepository;
    SalesTaxRateRepository salesTaxRateRepository;
    ProcessingFeeRepository processingFeeRepository;

    private Game expectedGame;
    private Optional<Game> actualGame;
    private List<Game> actualGameList;
    private List<Game> expectedGameList;

    private Invoice invoice1;
    private Invoice invoice2;
    private List<Invoice> invoiceList;

    private double expectedTax;
    private double actualTax;

    private List<Tshirt> tshirtList;
    private List<Tshirt> tshirtListByColorRed;
    private List<Tshirt> tshirtListBySizeSmall;
    private List<Tshirt> tshirtListByColorAndSizeRedAndSmall;

    private List<Tshirt> actualTshirt;
    private List<Tshirt> expectedTshirt;

    @Before
    public void setUp() throws Exception {
        setUpGameRepositoryMock();
        setUpInvoiceRepositoryMock();
        setUpSalesTaxRateRepositoryMock();
        setUpTshirtRepositoryMock();
        setUpProcessingFeeRepositoryMock();
    serviceLayer = new ServiceLayer(gameRepository, consoleRepository, tshirtRepository, invoiceRepository,salesTaxRateRepository,processingFeeRepository);
    }


    private void setUpGameRepositoryMock() {
        gameRepository = mock(GameRepository.class);

        Game game = new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game anotherGame = new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5);


        List<Game> gameList =Arrays.asList(game, anotherGame);
        List<Game> gameListByEsrbRating = Arrays.asList(anotherGame);
        List<Game> gameListByStudio = Arrays.asList(game);
        List<Game> gameListByStudioAndEsrbRating = Arrays.asList(anotherGame);

        doReturn(gameList).when(gameRepository).findAll();
        doReturn(gameListByStudio).when(gameRepository).findByStudio("Nintendo");
        doReturn(gameListByEsrbRating).when(gameRepository).findByEsrbRating("T (Teen)");
        doReturn(gameListByStudioAndEsrbRating).when(gameRepository).findByStudioAndEsrbRating("Xbox Game Studios", "T (Teen)");
        doReturn(Optional.of(game)).when(gameRepository).findByTitle("Nintendo Switch Sports");
        doReturn(Optional.of(game)).when(gameRepository).findById(1);
        doReturn(game).when(gameRepository).save(game);
    }

    private void setUpInvoiceRepositoryMock() {
        invoiceRepository = mock(InvoiceRepository.class);

        invoice1 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        invoice2 = new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 29.98, 1109.96);

        invoiceList = Arrays.asList(invoice1, invoice2);

        doReturn(invoiceList).when(invoiceRepository).findAll();
        doReturn(Optional.of(invoice1)).when(invoiceRepository).findById(1);
        doReturn(invoice1).when(invoiceRepository).save(invoice1);
    }

    private void setUpSalesTaxRateRepositoryMock() {
        salesTaxRateRepository = mock(SalesTaxRateRepository.class);

        invoice1 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        SalesTaxRate salesTaxRate = new SalesTaxRate("TX", .03);

        doReturn(salesTaxRate).when(salesTaxRateRepository).findByState("TX");

    }
    private void setUpProcessingFeeRepositoryMock() {
        processingFeeRepository = mock(ProcessingFeeRepository.class);

        invoice1 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        invoice2 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 11, 499.99, 40.00, 14.90, 554.8);
        ProcessingFee processingFee = new ProcessingFee("Games", 1.49);


        doReturn(processingFee).when(processingFeeRepository).findByProductType("Games");
    }

    //Ask About Optional.
    private void setUpTshirtRepositoryMock(){
        tshirtRepository = mock(TshirtRepository.class);
        Tshirt smallRedTshirt = new Tshirt(1,"small","red","A lovely red T-shirt",9.99,10);
        Tshirt mediumBlueTshirt = new Tshirt(2,"medium","blue","A lovely blue T-shirt",9.99,10);

        tshirtList = Arrays.asList(smallRedTshirt,mediumBlueTshirt);
        tshirtListByColorRed = Arrays.asList(smallRedTshirt);
        tshirtListBySizeSmall = Arrays.asList(smallRedTshirt);
        tshirtListByColorAndSizeRedAndSmall = Arrays.asList(smallRedTshirt);

        doReturn(tshirtList).when(tshirtRepository).findAll();
        doReturn(tshirtListByColorRed).when(tshirtRepository).findByColor("red");
        doReturn(tshirtListBySizeSmall).when(tshirtRepository).findBySize("small");
        doReturn(tshirtListByColorAndSizeRedAndSmall).when(tshirtRepository).findByColorAndSize("red","small");
        doReturn(Optional.of(smallRedTshirt)).when(tshirtRepository).findById(1);
        doReturn(smallRedTshirt).when(tshirtRepository).save(smallRedTshirt);

    }

    //---------------------------------- T-shirt service tests ------------------------------------
    @Test
    public void shouldFindAllTshirts(){
        List<Tshirt> expectedTshirts = tshirtList;
        List<Tshirt> actualTshirts = serviceLayer.getAllTshirt();
        assertEquals(expectedTshirts,actualTshirts);
    }
    @Test
    public void shouldFindTshirtByColor(){
        expectedTshirt = tshirtListByColorRed;
        actualTshirt = serviceLayer.getTshirtByColor("red");
        assertEquals(expectedTshirt,actualTshirt);
    }

    @Test
    public void shouldFindTshirtBySize(){
        expectedTshirt = tshirtListBySizeSmall;
        actualTshirt = serviceLayer.getTshirtBySize("small");
        assertEquals(expectedTshirt,actualTshirt);
    }
    @Test
    public void shouldFindTshirtByColorAndSize(){
        expectedTshirt = tshirtListByColorAndSizeRedAndSmall;
        actualTshirt = serviceLayer.getTshirtByColorAndSize("red","small");
        assertEquals(expectedTshirt,actualTshirt);
    }
    @Test
    public void shouldFindTshirtById(){
        Tshirt expectedTshirtById = new Tshirt (1,"small","red","A lovely red T-shirt",9.99,10);
        Optional<Tshirt> actualTshirtById = serviceLayer.getSingleTshirt(1);
        assertEquals(expectedTshirtById,actualTshirtById.get());
    }
    @Test
    public void shouldAddTshirt(){
        Tshirt expectedTshirt= new Tshirt (1,"small","red","A lovely red T-shirt",9.99,10);
        Tshirt addedTshirt = serviceLayer.addTshirt(expectedTshirt);
        assertEquals(expectedTshirt,addedTshirt);
    }
    /* --------------------------------- GAME SERVICE LAYER TESTS -------------------------------- */

    @Test
    public void shouldFindAllGames() {
        expectedGameList = Arrays.asList(
            new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15),
            new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5)
        );

        actualGameList = serviceLayer.getAllGames();

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void shouldFindAllGamesByStudio() {
        expectedGameList = Arrays.asList(
            new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15)
        );
        actualGameList = serviceLayer.getGamesByStudio("Nintendo");

        assertEquals(expectedGameList, actualGameList);

    }

    @Test
    public void shouldFindAllGamesByEsrbRating() {
        expectedGameList = Arrays.asList(
            new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5)
        );
        actualGameList = serviceLayer.getGamesByEsrbRating("T (Teen)");

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void shouldFindAllGamesByStudioAndEsrbRating() {
        expectedGameList = Arrays.asList(
                new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5)
        );
        actualGameList = serviceLayer.getGamesByStudioAndEsrbRating("Xbox Game Studios","T (Teen)");

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void shouldFindGameByTitle() {
        expectedGame = new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        actualGame = serviceLayer.getGameByTitle("Nintendo Switch Sports");

        assertEquals(expectedGame, actualGame.get());
    }

    @Test
    public void shouldFindGameById() {
        expectedGame = new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        actualGame = serviceLayer.getSingleGame(1);

        assertEquals(expectedGame, actualGame.get());
    }

    @Test
    public void shouldAddGame() {
        expectedGame = new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game savedGame = serviceLayer.addGame(new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15));

        assertEquals(expectedGame, savedGame);
    }

    /* --------------------------------- INVOICE SERVICE LAYER TESTS -------------------------------- */

    @Test
    public void shouldFindAllInvoices() {
        List<Invoice> expectedInvoiceList = Arrays.asList(
            new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8),
            new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 29.98, 1109.96)
        );

        List<Invoice> actualInvoiceList = serviceLayer.getAllInvoices();

        assertEquals(expectedInvoiceList, actualInvoiceList);
    }

    @Test
    public void shouldFindInvoiceById() {
        Invoice expectedInvoice = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        Optional<Invoice> actualInvoice = serviceLayer.getInvoiceById(1);

        assertEquals(expectedInvoice, actualInvoice.get());
    }

    @Test
    public void shouldCreateInvoice() {
        Invoice expectedInvoice = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "TX", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        Invoice savedInvoice = serviceLayer.addInvoice(new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8));

        assertEquals(expectedInvoice, savedInvoice);
    }

    @Test
    public void shouldCalculateSalesTax() {
        expectedTax = 15.00;
//        System.out.println(invoice1);
        actualTax = serviceLayer.applyTaxRate(invoice1);

        assertEquals(expectedTax, actualTax, .01);
    }

    @Test
    public void shouldCalculateProcessingFee() {
        double expectedFee = 1.49;
        double actualFee= serviceLayer.applyProcessingFee(invoice1);
        assertEquals(expectedFee, actualFee, .01);
    }

    @Test
    public void shouldCalculateProcessingFeeGreater10() {
        double expectedFee = 1.49 + 15.49;
        double actualFee= serviceLayer.applyProcessingFee(invoice2);
        assertEquals(expectedFee, actualFee, .01);
    }

    @Test
    public void shouldReturnQuantityChangeIfEnoughItemsInInventory() {
        int expectedReturn = 4;
        int actualReturn = serviceLayer.checkQuantity(4,8);
        assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void shouldCalculateSubtotal() {
        double expectedReturn = 499.90;
        double actualReturn = serviceLayer.calculateSubtotal(invoice1);
        assertEquals(expectedReturn, actualReturn, .01);
    }

    @Test
    public void shouldCalculateTotal() {
        double expectedReturn = 59.88;
        double actualReturn = serviceLayer.calculateTotal(50.00,8.88, 1.00);
        assertEquals(expectedReturn, actualReturn, .01);
    }

    @Test
    public void shouldThrowErrorIfNotEnoughInStock() {

    }

    @Test
    public void shouldDecreaseQuantityOfItemAfterPurchase() {

    }

}