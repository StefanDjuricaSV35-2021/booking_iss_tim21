package e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    @FindBy(css = "input[type='email']")
    private WebElement emailInputElement;
    @FindBy(css = "input[type='password']")
    private WebElement passwordInputElement;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='email']")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void inputEmail(String email){
        emailInputElement.sendKeys(email);
    }

    public void inputPassword(String password){
        passwordInputElement.sendKeys(password);
    }

    public void login(){
        loginButton.click();
    }
}
