import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestScript {

    public static void main(String[] args) {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Scenario 1: Login using standard user
        loginTest(driver, "standard_user", "secret_sauce");
        System.out.println("Scenario 1: Login successful");
        verifyHomePage(driver);
        System.out.println("Verified Home Page");
        logout(driver);
        System.out.println("Logged out successfully");
        verifyLoginPage(driver);
        System.out.println("Verified Login Page");

        // Scenario 2: Login using locked out user
        loginTest(driver, "locked_out_user", "secret_sauce");
        System.out.println("Scenario 2: Login attempt with locked out user");
        verifyErrorMessage(driver);
        System.out.println("Error message verified");

    }

    private static void loginTest(WebDriver driver, String username, String password) {
        driver.get("https://www.saucedemo.com/");
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        loginButton.click();
    }

    private static void verifyHomePage(WebDriver driver) {
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("shopping_cart_container")));
    	assert cartIcon.isDisplayed();

    }

    private static void logout(WebDriver driver) {
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));

        menuButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        
        logoutButton.click();
    }


    private static void verifyLoginPage(WebDriver driver) {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        assert usernameField.isDisplayed();
    }

    private static void verifyErrorMessage(WebDriver driver) {
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
        assert errorMessage.isDisplayed();
    }
}
