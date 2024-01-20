package e2eTests.tests;

import e2eTests.pages.HomePage;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
public class TestCancelReservation extends TestBase {
    @Test
    public void test() {
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());
        homePage.logIn("john.doe@example.com", "admin");
    }
}
