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

public class ReservationRequestsPage {
    private WebDriver driver;

    public ReservationRequestsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean isLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".content app-reservation")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getWaitingCounter() {
        List<WebElement> reservationRequests = driver.findElements(By.cssSelector(".bar"));
        int waitingCounter = 0;
        for (WebElement reservationRequest : reservationRequests) {
            WebElement status = reservationRequest.findElement(By.cssSelector("#status"));
            if(status.getText().equals("Waiting")){
                waitingCounter++;
            }
        }
        return waitingCounter;
    }

    public void acceptOne() {
        List<WebElement> reservationRequests = driver.findElements(By.cssSelector(".bar"));
        for (WebElement reservationRequest : reservationRequests) {
            WebElement status = reservationRequest.findElement(By.cssSelector("#status"));
            if(status.getText().equals("Waiting")){
                WebElement acceptButton = reservationRequest.findElement(By.cssSelector("#acceptButton"));
                acceptButton.click();
                (new WebDriverWait(driver, Duration.ofSeconds(10)))
                        .until(ExpectedConditions.textToBePresentInElement(status, "Accepted"));
                return;
            }

        }
    }

    public void waitToChangeStatus() {
        List<WebElement> reservationRequests = driver.findElements(By.cssSelector(".bar"));
        for (WebElement reservationRequest : reservationRequests) {
            try {
                WebElement status = reservationRequest.findElement(By.cssSelector("#status"));
                if(status.getText().equals("Waiting")){
                    (new WebDriverWait(driver, Duration.ofSeconds(1)))
                            .until(ExpectedConditions.textToBePresentInElement(status, "Declined"));
                    return;
                }
            } catch (Exception e) {}
        }
    }

}
