package myprojects.automation.assignment2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogIn {
    WebDriver driver;
    By uEmail = By.name("email");
    By uPass = By.name("passwd");
    By passEnter = By.name("submitLogin");
    By ddBox = By.id("header_employee_box");
    By logOut = By.id("header_logout");
    private String userName = "webinar.test@gmail.com";
    private String userPassword = "Xcg7299bnSmMuRLp9ITw";


    public LogIn(WebDriver driver) {
        this.driver = driver;
    }

    public void userLogIn() {
        driver.findElement(uEmail).sendKeys(userName);
        driver.findElement(uPass).sendKeys(userPassword);
        driver.findElement(passEnter).click();
    }

    public void userLogOut() throws InterruptedException {
        driver.findElement(ddBox).click();
        Thread.sleep(500);
        driver.findElement(logOut).click();
    }
}
