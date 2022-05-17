package com.company.GameStore.repository;

import com.company.GameStore.DTO.Tshirt;
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
// Make test for repository, use SpringJUnit4ClassRunner
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TshirtRepositoryTest {

    @Autowired
    TshirtRepository tshirtRepository;

    private Tshirt smallRedTshirt1;
    private Tshirt mediumBlueTshirt1;
    private List<Tshirt> expectedTshirtList = new ArrayList<>();

    //ask about Delete all setUp ? purpose
    @Before
    public void setUp() throws  Exception{
        tshirtRepository.deleteAll();
        Tshirt smallRedTshirt = new Tshirt(1,"small","red","A lovely red T-shirt",9.99,10);
        Tshirt mediumBlueTshirt = new Tshirt(2,"medium","blue","A lovely blue T-shirt",9.99,10);
        smallRedTshirt1 = tshirtRepository.save(smallRedTshirt);
        mediumBlueTshirt1 = tshirtRepository.save(mediumBlueTshirt);

//        expectedTshirtList.clear();
    }

    @Test
    public void addGetDeleteTshirt(){
        Optional<Tshirt> tshirtRed = tshirtRepository.findById(smallRedTshirt1.getId());
        //add/GET
        assertEquals(tshirtRed.get(),smallRedTshirt1);

        tshirtRepository.deleteById(smallRedTshirt1.getId());
        tshirtRed = tshirtRepository.findById(smallRedTshirt1.getId());
        //DELETE
        assertFalse(tshirtRed.isPresent());
    }

    @Test
    public void updateTshirt(){
//        Tshirt largeGreenTshirt = new Tshirt(3,"large","green","A lovely green T-shirt",9.99,10);
        smallRedTshirt1 = mediumBlueTshirt1;
        tshirtRepository.save(smallRedTshirt1);
        Optional<Tshirt> tshirtRedSetToBlue = tshirtRepository.findById(smallRedTshirt1.getId());
        assertEquals(tshirtRedSetToBlue.get(), mediumBlueTshirt1);
    }

    @Test
    public void getAllTshirts(){
        expectedTshirtList.add(smallRedTshirt1);
        expectedTshirtList.add(mediumBlueTshirt1);

        List<Tshirt> tshirtsList = tshirtRepository.findAll();
        assertEquals(tshirtsList.size(),2);
    }
    @Test
    public void getTshirtsByColor() {
        expectedTshirtList.add(smallRedTshirt1);

        List<Tshirt> actualTshirtList = tshirtRepository.findByColor("red");

        assertEquals(expectedTshirtList, actualTshirtList);

    }

    @Test
    public void getTshirtBySize() {
        expectedTshirtList.add(mediumBlueTshirt1);

        List<Tshirt> actualTshirtsList = tshirtRepository.findBySize("medium");

        assertEquals(expectedTshirtList, actualTshirtsList);
    }

    @Test
    public void getTshirtByColorAndSize() {
        expectedTshirtList.add(smallRedTshirt1);

        List<Tshirt> actualTshirtsList = tshirtRepository.findByColorAndSize("red", "small");

        assertEquals(expectedTshirtList, actualTshirtsList);
    }

}