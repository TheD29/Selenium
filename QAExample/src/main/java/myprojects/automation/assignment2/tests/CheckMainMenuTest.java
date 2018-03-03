package myprojects.automation.assignment2.tests;

import myprojects.automation.assignment2.BaseScript;
import myprojects.automation.assignment2.LogIn;
import myprojects.automation.assignment2.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckMainMenuTest extends BaseScript {


    public static void main(String[] args) throws InterruptedException {
        // TODO Script to check Main Menu items
        WebDriver driver = getDriver();
        LogIn logIn = new LogIn(driver);
        By pageTitle3 = By.tagName("h2");
        String ttlTextBefore;
        String ttlTextAfter;


        driver.get(Properties.getBaseAdminUrl());
        logIn.userLogIn();
        Thread.sleep(1500);

//      when you click the first link, the page is reloaded, and the reference Selenium has to the page becomes stale
        //So we need to use 2 cycles for
        List<WebElement> elements = driver.findElements(By.className("maintab"));

        String[] linkText = new String[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            linkText[i] = elements.get(i).getText();
        }
//        boolean checkElement;
        for (int i = 0; i < linkText.length; i++) {
            driver.findElement(By.linkText(linkText[i])).click();

            System.out.println(driver.findElement(pageTitle3).getText());
            ttlTextBefore = driver.findElement(pageTitle3).getText();
            Thread.sleep(2500);
            driver.navigate().refresh();
            ttlTextAfter = driver.findElement(pageTitle3).getText();
            //Check section location and write title

            if (ttlTextBefore.equals(ttlTextAfter))
                System.out.println("Passed: The user is on the page" + " - " + driver.findElement(pageTitle3).getText());
            else
                System.out.println("Failed: The user isn't on the page" + " - " + driver.findElement(pageTitle3).getText());
        }
        driver.quit();
    }
}
