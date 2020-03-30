


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SimliTestLogin {

    @Test
    public void test1(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\SimpliTest\\chromedriver_win32 (2)\\chromedriver.exe");
        WebDriver driver =  new ChromeDriver();
        driver.get("https://qa-test-simpli.herokuapp.com/#/register");
        closePopup(driver);
        driver.findElement(By.id("emailControl")).sendKeys("some.user@gmail.com");
        driver.findElement(By.id("passwordControl")).sendKeys("somePassword");
        driver.findElement(By.id("repeatPasswordControl")).sendKeys("somePassword");
        driver.findElement(By.id("securityAnswerControl")).sendKeys("test");
        driver.findElement(By.id("mat-select-0")).click();
        driver.findElement(By.xpath("//*[text()=' Your eldest siblings middle name? ']")).click();
        driver.findElement(By.id("registerButton")).click();
        driver.close();
        driver.quit();
    }

    @Test
    public void test2(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\admin\\SimpliTest\\chromedriver_win32 (2)\\chromedriver.exe");
        WebDriver driver =  new ChromeDriver();
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

    private void closePopup(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-dialog-0")));
        driver.findElement(By.id("mat-dialog-0")).findElements(By.tagName("button")).get(1).click();
    }
}
