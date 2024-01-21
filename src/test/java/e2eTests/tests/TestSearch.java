package e2eTests.tests;

import e2eTests.pages.HomePage;
import e2eTests.pages.SearchPage;
import org.testng.annotations.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.Assert.assertEquals;

public class TestSearch extends TestBase {
    @Test
    public void test_search() {
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isLoaded());

        //search wrong
        homePage.inputLocation("Begec");
        homePage.inputDateFrom("2/13/2024");
        homePage.inputDateTo("2/14/2024");
        homePage.inputNoGuests(3);
        homePage.searchButtonClick();



        SearchPage searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        assertTrue(searchPagePage.isLoaded());

        List<String> loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),0);

        //search right
        searchPagePage.inputLocation("Beograd");
        homePage.searchButtonClick();
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        assertTrue(searchPagePage.isLoaded());

        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),1);
        assertTrue(loadedAccommodations.contains("Cozy Cottage"));

        //filter amenity wrong
        searchPagePage.selectAmenity("Tv");
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),0);

        //filter amenities right
        searchPagePage.selectAmenity("Tv");
        searchPagePage.selectAmenity("WiFi");
        searchPagePage.selectAmenity("Parking");
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),1);

        //filter type wrong
        searchPagePage.selectType("House");
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),0);

        //filter type right
        searchPagePage.selectType("House");
        searchPagePage.selectType("Room");
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),1);

        //filter min wrong
        searchPagePage.setMinPrice(400.0);
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),0);

        //filter min right
        searchPagePage.setMinPrice(200.0);
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),1);

        //filter max wrong
        searchPagePage.setMaxPrice(250.0);
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),0);

        //filter max right
        searchPagePage.setMaxPrice(500.0);
        searchPagePage = new SearchPage(driver, driver.getCurrentUrl());
        loadedAccommodations=searchPagePage.getLoadedAccommodations();
        assertEquals(loadedAccommodations.size(),1);



    }
}
