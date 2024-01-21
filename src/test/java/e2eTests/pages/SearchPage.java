package e2eTests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.awt.SystemColor.window;

public class SearchPage {

    private WebDriver driver;

    @FindBy(css = "input[id='location-input']")
    private WebElement locationInputField;
    @FindBy(css = "input[id='start-date-input']")
    private WebElement dateFromInput;
    @FindBy(css = "input[id='end-date-input']")
    private WebElement dateToInput;
    @FindBy(css = "input[id='guests-number-input']")
    private WebElement noGuestsInput;
    @FindBy(css = "input[id='start-price']")
    private WebElement minPriceInputField;
    @FindBy(css = "input[id='end-price']")
    private WebElement maxPriceInputField;
    @FindBy(css = "button[id='search-btn']")
    private WebElement searchButton;


    private static String PAGE_URL = "http://localhost:4200/search";

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public SearchPage(WebDriver driver, String url) {
        this.driver = driver;
        driver.get(url);
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
        locationInputField.clear();
        locationInputField.sendKeys(location);
    }

    public void inputDateFrom(String date) {
        dateFromInput.sendKeys(date);
    }

    public void inputDateTo(String date) {
        dateToInput.sendKeys(date);
    }

    public void inputNoGuests(String number) {
        noGuestsInput.sendKeys(number);
    }

    public void searchButtonClick() {
        searchButton.click();
    }

    public void selectAmenity(String name) {

        WebElement amenityCheckBox = driver.findElement(By.cssSelector("#"+name+"-amenity"));
        amenityCheckBox.click();
    }

    public void selectType(String type) {

        WebElement typeRadioButton = driver.findElement(By.cssSelector("#"+type+"-type-input"));
        Actions actions = new Actions(driver);
        actions.moveToElement(typeRadioButton).click().perform();
        typeRadioButton.click();
    }

    public void setMinPrice(Double price) {

        minPriceInputField.clear();
        minPriceInputField.sendKeys(price.toString());
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", minPriceInputField);

    }

    public void setMaxPrice(Double price) {

        maxPriceInputField.clear();
        maxPriceInputField.sendKeys(price.toString());
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", maxPriceInputField);

    }


    public List<String> getLoadedAccommodations(){

        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        List<String> loadedAccommodations=new ArrayList<>();

        List<WebElement> names=null;

        try {
            names = driver.findElements(By.cssSelector("span[id='name']"));
        } catch (NoSuchElementException e) {
            return loadedAccommodations;
        }

        for (WebElement element : names) {

            loadedAccommodations.add(element.getText());
        }

        return loadedAccommodations;

    }

}
