package e2eTests.tests;

import e2eTests.pages.*;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestSearch extends TestBase {
    @Test
    public void test() {
        HomePage homePage = new HomePage(driver);
        Assertions.assertTrue(homePage.isLoaded());

        //log in as guest
        homePage.inputLocation("Beograd");
        homePage.inputDateFrom("2/13/2024");
        homePage.inputDateTo("2/14/2024");
        homePage.inputNoGuests(3);
        homePage.searchButtonClick();

        SearchPage searchPagePage = new SearchPage(driver);
        Assertions.assertTrue(searchPagePage.isLoaded());


//        ReservationsPage reservationsPageGuest = new ReservationsPage(driver);
//        Assertions.assertTrue(reservationsPageGuest.isLoadedGuest());
//
//        //cancel one reservation
//        int cancelledCounter = reservationsPageGuest.countCancelledReservations();
//        boolean wasCancelled = reservationsPageGuest.cancelOneReservation();
//
//        int newCancelledCounter = reservationsPageGuest.countCancelledReservations();
//        if (wasCancelled) {
//            assertEquals(newCancelledCounter, cancelledCounter + 1);
//        } else {
//            assertEquals(newCancelledCounter, cancelledCounter);
//        }
//
//        reservationsPageGuest.logOut();
//        assertTrue(homePage.isLoaded());
//        homePage.login();
//        Assertions.assertTrue(loginPage.isLoaded());
//
//        //log in as owner
//        loginPage.inputEmail("owner@example.com");
//        loginPage.inputPassword("admin");
//        loginPage.login();
//
//        //count current reservations
//        Assertions.assertTrue(homePage.isLoaded());
//        homePage.clickProfileImg();
//        homePage.clickViewReservationsOwner();
//        ReservationsPage reservationsPageOwner = new ReservationsPage(driver);
//        Assertions.assertTrue(reservationsPageOwner.isLoadedOwner());
//        int reservationsCounter = reservationsPageOwner.countReservations();
//
//        //accept reservation request, one gets declined
//        homePage.clickProfileImg();
//        homePage.clickViewReservationRequests();
//        ReservationRequestsPage reservationRequestsPage = new ReservationRequestsPage(driver);
//        Assertions.assertTrue(reservationRequestsPage.isLoaded());
//        int waitingCounter = reservationRequestsPage.getWaitingCounter();
//
//        reservationRequestsPage.acceptOne();
//        reservationRequestsPage.waitToChangeStatus();
//        int newWaitingCounter = reservationRequestsPage.getWaitingCounter();
//
//        //verify that two requests were changed
//        assertEquals(newWaitingCounter, waitingCounter - 2);
//
//        homePage.clickProfileImg();
//        homePage.clickViewReservationsOwner();
//
//        Assertions.assertTrue(reservationsPageOwner.isLoadedOwner());
//        int newReservationsCounter = reservationsPageOwner.countReservations();
//        assertEquals(newReservationsCounter, reservationsCounter + 1);
    }
}
