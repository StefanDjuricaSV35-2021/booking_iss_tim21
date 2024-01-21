package e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReservationsPage {
    private WebDriver driver;
    @FindBy(css = ".mat-mdc-menu-trigger.profileImg")
    private WebElement profileImg;

    @FindBy(css = "#logOutButton")
    private WebElement logOutButton;
    public ReservationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoadedGuest() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".content app-guests-reservations-page")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoadedOwner() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".content app-owners-reservations-page")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int countCancelledReservations() {
        List<WebElement> reservations = driver.findElements(By.cssSelector(".bar"));
        int cancelledCounter = 0;
        for (WebElement reservation : reservations) {
            WebElement status = reservation.findElement(By.cssSelector("#status"));
            if(status.getText().equals("Cancelled")){
                cancelledCounter++;
            }
        }
        return cancelledCounter;
    }

    public int countReservations() {
        List<WebElement> reservations = driver.findElements(By.cssSelector(".bar"));
        return reservations.size();
    }

    public boolean cancelOneReservation() {
        List<WebElement> reservations = driver.findElements(By.cssSelector(".bar"));
        for (WebElement reservation : reservations) {
            WebElement status = reservation.findElement(By.cssSelector("#status"));
            if(status.getText().equals("Active")){
                WebElement cancelButton = reservation.findElement(By.cssSelector("#cancelButton"));
                cancelButton.click();
                (new WebDriverWait(driver, Duration.ofSeconds(10)))
                        .until(ExpectedConditions.textToBePresentInElement(status, "Cancelled"));
                return true;
            }

        }
        return false;
    }

    public void logOut() {
        logOutButton.click();
    }
}
