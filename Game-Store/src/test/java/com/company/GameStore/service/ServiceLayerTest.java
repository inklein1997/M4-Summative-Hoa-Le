package com.company.GameStore.service;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.DTO.Invoice;
import com.company.GameStore.repository.ConsoleRepository;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.InvoiceRepository;
import com.company.GameStore.repository.TshirtRepository;
import org.junit.Before;
import org.junit.Test;

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


    private Game expectedGame;
    private Optional<Game> actualGame;
    private List<Game> actualGameList;
    private List<Game> expectedGameList;

    private Invoice invoice1;
    private Invoice invoice2;
    private List<Invoice> invoiceList;

    @Before
    public void setUp() throws Exception {
        setUpGameRepositoryMock();
        setUpInvoiceRepositoryMock();

        serviceLayer = new ServiceLayer(gameRepository, consoleRepository, tshirtRepository, invoiceRepository);
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

        invoice1 = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        invoice2 = new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 29.98, 1109.96);

        invoiceList = Arrays.asList(invoice1, invoice2);

        doReturn(invoiceList).when(invoiceRepository).findAll();
        doReturn(Optional.of(invoice1)).when(invoiceRepository).findById(1);
        doReturn(invoice1).when(invoiceRepository).save(invoice1);
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
            new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8),
            new Invoice(2, "Patrick Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Consoles", 1, 499.99, 2, 999.98, 80.00, 29.98, 1109.96)
        );

        List<Invoice> actualInvoiceList = serviceLayer.getAllInvoices();

        assertEquals(expectedInvoiceList, actualInvoiceList);
    }

    @Test
    public void shouldFindInvoiceById() {
        Invoice expectedInvoice = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        Optional<Invoice> actualInvoice = serviceLayer.getInvoiceById(1);

        assertEquals(expectedInvoice, actualInvoice.get());
    }

    @Test
    public void shouldCreateInvoice() {
        Invoice expectedInvoice = new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8);
        Invoice savedInvoice = serviceLayer.addInvoice(new Invoice(1, "Michael Klein", "12345 Big Oak Dr.", "Austin", "Tx", "78727", "Games", 1, 49.99, 10, 499.99, 40.00, 14.90, 554.8));

        assertEquals(expectedInvoice, savedInvoice);
    }


}