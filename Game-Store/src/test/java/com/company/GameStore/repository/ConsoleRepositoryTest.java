package com.company.GameStore.repository;


import com.company.GameStore.DTO.Console;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConsoleRepositoryTest {
    @Autowired
    ConsoleRepository consoleRepository;

    private Console console;
    private Console anotherConsole;
    private List<Console> expectedConsoleList = new ArrayList<>();

    @Before
    public void setUp() {
        consoleRepository.deleteAll();

        console = consoleRepository.save(new Console());
        anotherConsole = consoleRepository.save(new Console(3, "PS4", "Sony", "1TB", "Dual Shock", 579.00, 55));

        expectedConsoleList.clear();
    }

    @Test
    public void addGetDeleteConsole() {
        Optional<Console> console1 = consoleRepository.findById(console.getConsole_id());

        assertEquals(console1.get(), console);

        consoleRepository.deleteById(console.getConsole_id());
        console1 = consoleRepository.findById(console.getConsole_id());

        assertFalse(console1.isPresent());
    }

    @Test
    public void updateConsole() {
        console.setDescription("New description");
        consoleRepository.save(console);
        Optional<Console> console1 = consoleRepository.findById(console.getConsole_id());

        assertEquals(console1.get(), console);
    }

    @Test
    public void getAllConsoles() {
        expectedConsoleList.add(console);
        expectedConsoleList.add(anotherConsole);

        List<Console> actualConsoleList = consoleRepository.findAll();

        assertEquals(actualConsoleList.size(), 2);
        assertEquals(expectedConsoleList, actualConsoleList);
    }

    @Test
    public void getConsolesByManufacturer() {
        expectedConsoleList.add(console);
        expectedConsoleList.add(anotherConsole);

        List<Console> actualConsoleList = consoleRepository.findByManufacturer("Microsoft");

        assertEquals(expectedConsoleList, actualConsoleList);
    }



}
