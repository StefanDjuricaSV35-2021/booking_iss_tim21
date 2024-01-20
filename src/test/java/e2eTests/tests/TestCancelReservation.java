package e2eTests.tests;

import e2eTests.pages.HomePage;
import e2eTests.pages.LoginPage;
import e2eTests.pages.ReservationRequestsPage;
import e2eTests.pages.ReservationsPage;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
public class TestCancelReservation extends TestBase {
    @Test
    public void test() {
        HomePage homePage = new HomePage(driver);
        Assertions.assertTrue(homePage.isLoaded());
        homePage.login();

        LoginPage loginPage = new LoginPage(driver);
        Assertions.assertTrue(loginPage.isLoaded());

        //log in as guest
        loginPage.inputEmail("john.doe@example.com");
        loginPage.inputPassword("admin");
        loginPage.login();

        Assertions.assertTrue(homePage.isLoaded());
        homePage.clickProfileImg();
        homePage.clickViewReservationsGuest();

        ReservationsPage reservationsPageGuest = new ReservationsPage(driver);
        Assertions.assertTrue(reservationsPageGuest.isLoadedGuest());

        //cancel one reservation
        int cancelledCounter = reservationsPageGuest.countCancelledReservations();
        boolean wasCancelled = reservationsPageGuest.cancelOneReservation();

        int newCancelledCounter = reservationsPageGuest.countCancelledReservations();
        if (wasCancelled) {
            assertEquals(newCancelledCounter, cancelledCounter + 1);
        } else {
            assertEquals(newCancelledCounter, cancelledCounter);
        }

        reservationsPageGuest.logOut();
        assertTrue(homePage.isLoaded());
        homePage.login();
        Assertions.assertTrue(loginPage.isLoaded());

        //log in as owner
        loginPage.inputEmail("owner@example.com");
        loginPage.inputPassword("admin");
        loginPage.login();

        //count current reservations
        Assertions.assertTrue(homePage.isLoaded());
        homePage.clickProfileImg();
        homePage.clickViewReservationsOwner();
        ReservationsPage reservationsPageOwner = new ReservationsPage(driver);
        Assertions.assertTrue(reservationsPageOwner.isLoadedOwner());
        int reservationsCounter = reservationsPageOwner.countReservations();

        //accept reservation request, one gets declined
        homePage.clickProfileImg();
        homePage.clickViewReservationRequests();
        ReservationRequestsPage reservationRequestsPage = new ReservationRequestsPage(driver);
        Assertions.assertTrue(reservationRequestsPage.isLoaded());
        int waitingCounter = reservationRequestsPage.getWaitingCounter();

        reservationRequestsPage.acceptOne();
        reservationRequestsPage.waitToChangeStatus();
        int newWaitingCounter = reservationRequestsPage.getWaitingCounter();

        //verify that two requests were changed
        assertEquals(newWaitingCounter, waitingCounter - 2);

        homePage.clickProfileImg();
        homePage.clickViewReservationsOwner();

        Assertions.assertTrue(reservationsPageOwner.isLoadedOwner());
        int newReservationsCounter = reservationsPageOwner.countReservations();
        assertEquals(newReservationsCounter, reservationsCounter + 1);
    }
}
