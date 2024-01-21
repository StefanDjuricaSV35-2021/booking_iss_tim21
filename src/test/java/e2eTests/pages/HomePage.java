package e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;

    @FindBy(css = "a[routerLink='login']")
    private WebElement loginButton;

    @FindBy(css = "#locationInput")
    private WebElement locationInputField;

    @FindBy(css = "#start-date-input")
    private WebElement dateFromInput;
    @FindBy(css = "#end-date-input")
    private WebElement dateToInput;
    @FindBy(css = "#guests-number-input")
    private WebElement noGuestsInput;
    @FindBy(css = "#search-btn")
    private WebElement searchButton;

    @FindBy(css = ".mat-mdc-menu-trigger.profileImg")
    private WebElement profileImg;

    @FindBy(css = "[routerlink='/accommodation_create']")
    private WebElement accommodationCreateButton;

    @FindBy(css = "[routerlink='/reservationsGuest']")
    private WebElement reservationsButton;

    @FindBy(css = "[routerlink='/reservationsOwner']")
    private WebElement reservationsButtonOwner;

    @FindBy(css = "[routerlink='/reservationRequests']")
    private WebElement reservationRequestsButton;


    private static String PAGE_URL = "http://localhost:4200/homePage";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[routerLink='login']")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void inputLocation(String location){
        locationInputField.sendKeys(location);
    }
    public void inputDateFrom(String date){
        dateFromInput.sendKeys(date);
    }
    public void inputDateTo(String date){
        dateToInput.sendKeys(date);
    }
    public void inputNoGuests(Integer number){noGuestsInput.sendKeys(number.toString());}
    public void searchButtonClick(){searchButton.click();}
    public void login() {
        loginButton.click();
    }
    public void clickProfileImg() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".mat-mdc-menu-trigger.profileImg")));

        profileImg.click();
    }

    public void clickCreateAccommodation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[routerlink='/accommodation_create']")));

        accommodationCreateButton.click();
    }

    public void clickViewReservationsGuest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(reservationsButton, "View Reservations"));

        reservationsButton.click();
    }

    public void clickViewReservationsOwner() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(reservationsButtonOwner, "View Reservations"));

        reservationsButtonOwner.click();
    }

    public void clickViewReservationRequests() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElement(reservationRequestsButton, "View Reservation Requests"));

        reservationRequestsButton.click();
    }
}
