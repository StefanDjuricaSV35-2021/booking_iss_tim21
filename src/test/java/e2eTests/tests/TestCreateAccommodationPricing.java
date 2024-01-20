package e2eTests.tests;

import e2eTests.pages.HomePage;
import e2eTests.pages.LoginPage;
import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;

public class TestCreateAccommodationPricing extends TestBase{
    @Test
    public void test() {
        HomePage homePage = new HomePage(driver);
        Assertions.assertTrue(homePage.isLoaded());
        homePage.login();

        LoginPage loginPage = new LoginPage(driver);
        Assertions.assertTrue(loginPage.isLoaded());

        loginPage.inputEmail("bob.jones@example.com");
        loginPage.inputPassword("admin");
        loginPage.login();

        Assertions.assertTrue(homePage.isLoaded());
        homePage.clickProfileImg();
        homePage.clickCreateAccommodation();

    }
}