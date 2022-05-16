package com.company.GameStore.service;

import com.company.GameStore.DTO.Game;
import com.company.GameStore.repository.GameRepository;
import com.company.GameStore.repository.TshirtRepository;
import org.junit.Before;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class ServiceLayerTest {
    ServiceLayer serviceLayer;
    GameRepository gameRepository;
    TshirtRepository tshirtRepository;


    @Before
    public void setUp() throws Exception {
        setUpGameRepositoryMock();

        serviceLayer = new ServiceLayer();
    }

    private void setUpGameRepositoryMock() {
        Game game = new Game(1, "Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15);
        Game anotherGame = new Game(3, "Halo Infinite", "T (Teen)", "Experience the ultimate gameplay and explore a stunning sci-fi world in this riveting, first person shooter video game.", 39.99, "Xbox Game Studios", 5);

        List<Game> gameList =Arrays.asList(game);
        List<Game> gameListByEsrbRating = Arrays.asList(anotherGame);
        List<Game> gameListByStudio = Arrays.asList(game);
        List<Game> gameListByStudioAndEsrbRating = Arrays.asList(anotherGame);

        doReturn(gameList).when(gameRepository).findAll();
        doReturn(gameListByStudio).when(gameRepository).findByStudio("Nintendo");
        doReturn(gameListByEsrbRating).when(gameRepository).findByEsrbRating("T (Teen)");
        doReturn(gameListByStudioAndEsrbRating).when(gameRepository).findByStudioAndEsrbRating("Xbox Game Studios", "T (Teen)");
        doReturn(game).when(gameRepository).findByTitle("Nintendo Switch Sports");
        doReturn(game).when(gameRepository).findById(1);
        doReturn(game).when(gameRepository).save(game);
    }

}