package myprojects.automation.assignment2.tests;

import myprojects.automation.assignment2.BaseScript;
import myprojects.automation.assignment2.LogIn;
import myprojects.automation.assignment2.utils.Properties;
import org.openqa.selenium.WebDriver;

public class LoginTest extends BaseScript {

    public static void main(String[] args) throws InterruptedException {
        // TODO Script to execute login and logout steps

        WebDriver driver = getDriver();
        LogIn logIn = new LogIn(driver);

        //Test A
        driver.get(Properties.getBaseAdminUrl());
        Thread.sleep(2500);
        logIn.userLogIn();
        Thread.sleep(2500);
        logIn.userLogOut();
        Thread.sleep(1000);
        driver.quit();
        // ...
    }
}
