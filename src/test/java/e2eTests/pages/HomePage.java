package e2eTests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    @FindBy(css = ".booking")
    private WebElement bookingTitle;

    @FindBy(css = "#login")
    private WebElement loginNavBar;

    @FindBy(css = "#rememberMe")
    private WebElement rememberMe;

    @FindBy(css = "#loginButton")
    private WebElement submitLoginButton;

    @FindBy(css = "#emailInput")
    private WebElement emailInput;

    @FindBy(css = "#passwordInput")
    private WebElement passwordInput;

    @FindBy(css = "#profileButton")
    private WebElement profileButton;

    private static String PAGE_URL = "http://localhost:4200/homePage";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(bookingTitle, "Booʞing"));
    }
    public void logIn(String email, String password) {
        loginNavBar.click();
        (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(rememberMe, "Remember me"));

        emailInput.click();
        emailInput.sendKeys(email);

        passwordInput.click();
        passwordInput.sendKeys(password);

        submitLoginButton.click();
    }
}
