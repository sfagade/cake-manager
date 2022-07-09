package com.waracle.cakemgr.service.impl;

import com.waracle.cakemgr.model.Cake;
import com.waracle.cakemgr.service.CakeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CakeServiceImplTest {

    @Autowired
    private CakeService cakeService;

    @Test
    void fetchAllCakesDefault() {

        List<Cake> cakeList = this.cakeService.fetchAllCakes();

        assertAll(
                "Fetch All Cakes Default State Test",
                () -> assertNotNull(cakeList, "Record set  is null"),
                () -> assertNotNull(cakeList.get(0).getId(), "ID not set on first record"));
    }

    @Test
    void saveNewCakeDataDefaultState() {

        Cake cake = new Cake();
        cake.setTitle("Integration Test Coke");
        cake.setDescription("Integration Test Description");
        cake.setImageUrl("/image/integration_test.jpg");

        Cake newCakeData = this.cakeService.saveNewCakeData(cake);

        assertAll(
                "Save New Cake Default State Test",
                () -> assertNotNull(newCakeData, "Cake  is null"),
                () -> assertNotNull(newCakeData.getId(), "ID not set on new record"),
                () -> assertNotNull(newCakeData.getCreatedOn(), "Created date not set on new record"),
                () -> assertNotNull(newCakeData.getModifiedOn(), "Modified date not set on new record"));
    }

    @Test
    void saveNewCakeDataMissingTitle() {

        Cake cake = new Cake();
        cake.setDescription("Integration Test Description");
        cake.setImageUrl("/image/integration_test.jpg");

        Cake newCakeData = this.cakeService.saveNewCakeData(cake);
        assertNull(newCakeData, "New-Cake-data did not return null data for missing title");
    }

    @Test
    void saveNewCakeDataMissingImage() {

        Cake cake = new Cake();
        cake.setTitle("Integration Test Coke");
        cake.setDescription("Integration Test Description");

        Cake newCakeData = this.cakeService.saveNewCakeData(cake);
        assertNull(newCakeData, "New-Cake-data did not return null data for missing image url");
    }

    @Test
    void saveNewCakeDataMissingIDescription() {

        Cake cake = new Cake();
        cake.setTitle("Integration Test Coke");
        cake.setImageUrl("/image/integration_test.jpg");

        Cake newCakeData = this.cakeService.saveNewCakeData(cake);
        assertNull(newCakeData, "New-Cake-data did not return null data for missing description");
    }

    @Test
    void findCakeByIdDefaultState() {

        Long cakeId = 2L;
        Cake cake = this.cakeService.findCakeById(cakeId);

        assertAll(
                "Save New Cake Default State Test",
                () -> assertNotNull(cake, "Cake  is null"),
                () -> assertEquals(cake.getId(), 2L, "ID not equal to search ID"));
    }

    @Test
    void findCakeByIdMissingId() {

        Cake cake = this.cakeService.findCakeById(null);
        assertNull(cake, "Find by no ID did not return null");
    }

    @Test
    void findCakeByIdNegativeId() {

        Long cakeId = -1L;
        Cake cake = this.cakeService.findCakeById(cakeId);
        assertNull(cake, "Find by negative ID did not return null");
    }
}