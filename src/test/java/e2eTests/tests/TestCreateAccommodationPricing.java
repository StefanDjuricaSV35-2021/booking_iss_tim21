package e2eTests.tests;

import e2eTests.pages.AccommodationCreationPage;
import e2eTests.pages.HomePage;
import e2eTests.pages.LoginPage;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;

public class TestCreateAccommodationPricing extends TestBase{

    private String email = "stefandjurica420@gmail.com";
    private String password = "1234";

    private String good_start_date = "01-02-2024";
    private String good_end_date = "01-05-2024";
    private String good_price = "123.3";

    private String bad_price = "fjsfjsjfksjfk";


    private String second_good_start_date = "01-06-2024";
    private String second_good_end_date = "01-09-2024";

    @Test
    public void test() {
        HomePage homePage = new HomePage(driver);
        Assertions.assertTrue(homePage.isLoaded());
        homePage.login();

        LoginPage loginPage = new LoginPage(driver);
        Assertions.assertTrue(loginPage.isLoaded());

        loginPage.inputEmail(email);
        loginPage.inputPassword(password);
        loginPage.login();

        Assertions.assertTrue(homePage.isLoaded());
        homePage.clickProfileImg();
        homePage.clickCreateAccommodation();

        AccommodationCreationPage accommodationCreationPage = new AccommodationCreationPage(driver);
        Assertions.assertTrue(accommodationCreationPage.isLoaded());
        accommodationCreationPage.scrollDown();

        accommodationCreationPage.selectPerNight();
        accommodationCreationPage.selectPerGuest();

        accommodationCreationPage.addPricing();
        Assertions.assertFalse(accommodationCreationPage.elementAdded());

        accommodationCreationPage.inputStartDate(good_start_date);
        accommodationCreationPage.inputEndDate(good_end_date);
        accommodationCreationPage.inputPricing(bad_price);
        accommodationCreationPage.addPricing();
        Assertions.assertFalse(accommodationCreationPage.elementAdded());

        accommodationCreationPage.inputStartDate(good_start_date);
        accommodationCreationPage.inputEndDate(good_end_date);
        accommodationCreationPage.inputPricing(good_price);
        accommodationCreationPage.addPricing();
        Assertions.assertTrue(accommodationCreationPage.elementAdded());

        accommodationCreationPage.inputStartDate(good_start_date);
        accommodationCreationPage.inputEndDate(good_end_date);
        accommodationCreationPage.inputPricing(good_price);
        accommodationCreationPage.addPricing();
        Assertions.assertFalse(accommodationCreationPage.elementAdded());

        accommodationCreationPage.inputStartDate(second_good_end_date);
        accommodationCreationPage.inputEndDate(second_good_start_date);
        accommodationCreationPage.inputPricing(good_price);
        accommodationCreationPage.addPricing();
        Assertions.assertFalse(accommodationCreationPage.elementAdded());

        accommodationCreationPage.inputStartDate(second_good_start_date);
        accommodationCreationPage.inputEndDate(second_good_end_date);
        accommodationCreationPage.inputPricing(good_price);
        accommodationCreationPage.addPricing();
        Assertions.assertTrue(accommodationCreationPage.elementAdded());

//        accommodationCreationPage.removePricing();
//        Assertions.assertTrue(accommodationCreationPage.elementRemoved());

    }
}
