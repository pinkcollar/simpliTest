


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SimliTestLogin {
    WebDriver driver = new ChromeDriver();

    @BeforeAll
    public static void setProperty() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\SimpliTest\\chromedriver_win32 (2)\\chromedriver.exe");
    }

    @Test
    public void test1() {

        driver.get("https://qa-test-simpli.herokuapp.com/#/register");

        closePopup(driver);

        String uniqueLogin = getUniqueLogin();

        insertLogin(uniqueLogin);
        insertPassword("somePassword");
        repeatPassword("somePassword");
        insertSecurityAnswer(" Your eldest siblings middle name? ", "test");
        clickButton("registerButton");

        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='Login']"))));

        checkUrl("https://qa-test-simpli.herokuapp.com/#/login");

        loginUser(uniqueLogin, "somePassword");

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[text()='All Products']"))));

        checkUrl("https://qa-test-simpli.herokuapp.com/#/search");

        driver.close();
        driver.quit();
    }

    @Test
    public void test2() {

        driver.get("https://qa-test-simpli.herokuapp.com/#/register");

        closePopup(driver);

        driver.findElement(By.id("emailControl")).sendKeys("some.faileduser@gmail.com");
        driver.findElement(By.id("passwordControl")).sendKeys("somePassword");
        driver.findElement(By.id("repeatPasswordControl")).sendKeys("somePassword1");

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.id("securityAnswerControl")).sendKeys("test");
        driver.findElement(By.id("mat-select-0")).click();
        driver.findElement(By.xpath("//*[text()=' Your eldest siblings middle name? ']")).click();

        Assertions.assertTrue(!driver.findElement(By.id("registerButton")).isEnabled());
        Assertions.assertTrue(driver.findElement(By.xpath("//*[text()=' Passwords do not match ']")).isDisplayed());

        driver.close();
        driver.quit();
    }

    private void closePopup(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-dialog-0")));
        driver.findElement(By.id("mat-dialog-0")).findElements(By.tagName("button")).get(1).click();
    }

    private String getUniqueLogin() {
        String unique = UUID.randomUUID().toString();
        return unique + "some.user@gmail.com";

    }

    private void insertLogin(String login) {


        driver.findElement(By.id("emailControl")).sendKeys(login);
    }

    private void insertPassword(String password) {
        driver.findElement(By.id("passwordControl")).sendKeys(password);
    }

    private void repeatPassword(String password) {
        driver.findElement(By.id("repeatPasswordControl")).sendKeys(password);
    }

    private void insertSecurityAnswer(String question, String answer) {
        driver.findElement(By.id("mat-select-0")).click();
        driver.findElement(By.xpath("//*[text()='" + question + "']")).click();
        driver.findElement(By.id("securityAnswerControl")).sendKeys(answer);

    }

    private void clickButton(String id) {
        driver.findElement(By.id(id)).click();
    }

    private void checkUrl(String expectedUrl) {
        String actualURL = driver.getCurrentUrl();
        Assertions.assertEquals(actualURL, expectedUrl);
    }

    private void loginUser(String login, String password) {
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }
}
