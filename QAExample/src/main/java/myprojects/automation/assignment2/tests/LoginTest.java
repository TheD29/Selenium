package myprojects.automation.assignment2.tests;

import myprojects.automation.assignment2.BaseScript;
import myprojects.automation.assignment2.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class LoginTest extends BaseScript {

    public static void main(String[] args) throws InterruptedException {
        // TODO Script to execute login and logout steps

        WebDriver driver = getDriver();

        By uEmail = By.name("email");
        By uPass = By.name("passwd");
        By passEnter = By.name("submitLogin");
        By ddBox = By.id("header_employee_box");
        By logOut = By.id("header_logout");
        String openNewTab = Keys.chord(Keys.CONTROL, "T");

        //Test A
        driver.get(Properties.getBaseAdminUrl());
        Thread.sleep(2500);
        driver.findElement(uEmail).sendKeys("webinar.test@gmail.com");
        driver.findElement(uPass).sendKeys("Xcg7299bnSmMuRLp9ITw");
        driver.findElement(passEnter).click();
        Thread.sleep(2500);
        driver.findElement(ddBox).click();
        Thread.sleep(500);
        driver.findElement(logOut).click();
        Thread.sleep(1000);
        driver.close();
        // ...
    }
}
