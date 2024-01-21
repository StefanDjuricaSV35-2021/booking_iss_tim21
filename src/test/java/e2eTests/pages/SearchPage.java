package e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {

    private WebDriver driver;

    @FindBy(css = "#start-date-input")
    private WebElement dateFromInput;
    @FindBy(css = "#end-date-input")
    private WebElement dateToInput;
    @FindBy(css = "#guests-number-input")
    private WebElement noGuestsInput;
    @FindBy(css = "#search-btn")
    private WebElement searchButton;

    private static String PAGE_URL = "http://localhost:4200/search";

    public SearchPage(WebDriver driver) {
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

    public void inputDateFrom(String date){
        dateFromInput.sendKeys(date);
    }
    public void inputDateTo(String date){
        dateToInput.sendKeys(date);
    }
    public void inputNoGuests(String number){noGuestsInput.sendKeys(number);}
    public void searchButtonClick(){searchButton.click();}

}
