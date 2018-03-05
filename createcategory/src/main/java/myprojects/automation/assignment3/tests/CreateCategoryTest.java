package myprojects.automation.assignment3.tests;

import myprojects.automation.assignment3.BaseScript;
import myprojects.automation.assignment3.GeneralActions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

public class CreateCategoryTest extends BaseScript {
    public static void main(String[] args) throws InterruptedException {
        // TODO prepare driver object
        EventFiringWebDriver driver = getConfiguredDriver();
        GeneralActions generalActions = new GeneralActions(driver);
        // login
        generalActions.login();
        // create category
        generalActions.createCategory();
        driver.navigate().back();
        // check that new category appears in Categories table

        generalActions.orderCategory();
        driver.quit();
        // ...


    }
}
