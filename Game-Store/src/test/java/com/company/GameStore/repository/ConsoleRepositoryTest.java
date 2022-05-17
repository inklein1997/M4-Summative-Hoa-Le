package com.company.GameStore.repository;


import com.company.GameStore.DTO.Console;
import com.company.GameStore.DTO.Game;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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

        console = consoleRepository.save(new Console("PS4", ));
        anotherConsole = consoleRepository.save(new Console(3, "PS4", "Sony", "1TB", "Dual Shock", "579.00", 55));

        expectedConsoleList.clear();
    }


}
