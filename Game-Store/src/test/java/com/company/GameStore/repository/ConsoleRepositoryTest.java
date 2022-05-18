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

        console = consoleRepository.save(new Console("PS4", "Sony", "512GB", "Dual Shock", 579.00, 55));
        anotherConsole = consoleRepository.save(new Console(2, "PS4", "Sony", "512GB", "Dual Shock", 579.00, 55));
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
        console.setManufacturer("New manufacturer");
        consoleRepository.save(console);
        Optional<Console> console1 = consoleRepository.findById(console.getConsole_id());

        assertEquals(console1.get(), console);
    }

    @Test
    public void getAllConsoles() {
        expectedConsoleList.add(console);
        expectedConsoleList.add(anotherConsole);
        List<Console> actualConsoleList = consoleRepository.findAll();

        assertEquals(2, actualConsoleList.size());
        assertEquals(expectedConsoleList, actualConsoleList);
    }

    @Test
    public void getConsolesByManufacturer() {
        expectedConsoleList.add(console);
        expectedConsoleList.add(anotherConsole);
        List<Console> actualConsoleList = consoleRepository.findByManufacturer("Sony");

        assertEquals(expectedConsoleList, actualConsoleList);
    }
}
