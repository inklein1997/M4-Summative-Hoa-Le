package com.company.GameStore.repository;

import com.company.GameStore.DTO.Game;
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
public class GameRepositoryTest {
    @Autowired
    GameRepository gameRepository;

    private Game game;
    private Game anotherGame;
    private List<Game> expectedGameList = new ArrayList<>();

    @Before
    public void setUp() {
        gameRepository.deleteAll();

        game = gameRepository.save(new Game("Nintendo Switch Sports", "E (Everyone)", "Class sports simulation video game", 49.99, "Nintendo", 15));
        anotherGame = gameRepository.save(new Game(2, "Miitopia", "M (Mature)", "An adventure with a Mii character cast of your choosing", 39.99, "Nintendo", 7));

        expectedGameList.clear();
    }

    @Test
    public void addGetDeleteGame() {
        Optional<Game> game1 = gameRepository.findById(game.getGame_id());

        assertEquals(game1.get(), game);

        gameRepository.deleteById(game.getGame_id());
        game1 = gameRepository.findById(game.getGame_id());

        assertFalse(game1.isPresent());
    }

    @Test
    public void updateGame() {
        game.setDescription("New description");
        gameRepository.save(game);
        Optional<Game> game1 = gameRepository.findById(game.getGame_id());

        assertEquals(game1.get(), game);
    }

    @Test
    public void getAllGames() {
        expectedGameList.add(game);
        expectedGameList.add(anotherGame);

        List<Game> actualGameList = gameRepository.findAll();

        assertEquals(actualGameList.size(), 2);
        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void getGamesByEsrbRating() {
        expectedGameList.add(game);

        List<Game> actualGameList = gameRepository.findByEsrbRating("E (Everyone)");

        assertEquals(expectedGameList, actualGameList);

    }

    @Test
    public void getGamesByStudio() {
        expectedGameList.add(game);
        expectedGameList.add(anotherGame);

        List<Game> actualGameList = gameRepository.findByStudio("Nintendo");

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void getGamesByEsrbRatingAndStudio() {
        expectedGameList.add(game);

        List<Game> actualGameList = gameRepository.findByStudioAndEsrbRating("Nintendo", "E (Everyone)");

        assertEquals(expectedGameList, actualGameList);
    }

    @Test
    public void getGameByTitle() {
        Optional<Game> actualGame = gameRepository.findByTitle("Nintendo Switch Sports");

        assertEquals(game, actualGame.get());
    }

}