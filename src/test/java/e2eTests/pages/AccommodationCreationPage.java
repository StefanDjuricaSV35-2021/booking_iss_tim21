package e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AccommodationCreationPage {
    private WebDriver driver;

    private int previousElementNum = 0;
    private int currentElementNum = 0;

    @FindBy(css = "[id='startDate']")
    private WebElement startDateInput;
    @FindBy(css = "[id='endDate']")
    private WebElement endDateInput;

    @FindBy(css = "[id='price']")
    private WebElement priceInput;

    @FindBy(css = "[id='mat-radio-2-input']")
    private WebElement perNightRadio;
    @FindBy(css = "[id='mat-radio-3-input']")
    private WebElement perGuestRadio;

    @FindBy(css = "[id='submitPrice']")
    private WebElement addPricingButton;

    @FindBy(css = ".table.table-bordered.mt-3")
    private WebElement tableElement;

    public AccommodationCreationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id='startDate']")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollDown(){
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("arguments[0].scrollIntoView(true);", addPricingButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectPerNight(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id='mat-radio-2-input']")));

        perNightRadio.click();
    }

    public void selectPerGuest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id='mat-radio-3-input']")));

        perGuestRadio.click();
    }

    public void inputStartDate(String desiredDate){
        startDateInput.clear();
        startDateInput.sendKeys(desiredDate);
    }

    public void inputEndDate(String desiredDate){
        endDateInput.clear();
        endDateInput.sendKeys(desiredDate);
    }

    public void inputPricing(String price){
        priceInput.clear();
        priceInput.sendKeys(price);
    }

    public void addPricing(){
        previousElementNum = currentElementNum;

        addPricingButton.click();

        By tbodySelector = By.cssSelector(".table.table-bordered.mt-3 tbody");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(tbodySelector));

        List<WebElement> elements = tableElement.findElements(tbodySelector);
        currentElementNum = elements.size();
        System.out.println(previousElementNum);
        System.out.println(currentElementNum);
    }

    public boolean elementAdded(){
        return previousElementNum < currentElementNum;
    }
}
