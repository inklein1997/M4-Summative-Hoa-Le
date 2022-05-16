package com.company.GameStore.service;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.InvoiceRepository;
import com.company.GameStore.repository.TshirtRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {
    ServiceLayer serviceLayer;
    GameRepository gameRepository;
    TshirtRepository tshirtRepository;
    InvoiceRepository invoiceRepository;


    private Game expectedGame;
    private Optional<Game> actualGame;
    private List<Game> actualGameList;
    private List<Game> expectedGameList;


    @Before
    public void setUp() throws Exception {
        setUpGameRepositoryMock();

        serviceLayer = new ServiceLayer(gameRepository, tshirtRepository, invoiceRepository);
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

}