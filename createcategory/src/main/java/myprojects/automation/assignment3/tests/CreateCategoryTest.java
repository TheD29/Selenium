package myprojects.automation.assignment3.tests;

import myprojects.automation.assignment3.BaseScript;
import myprojects.automation.assignment3.GeneralActions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class CreateCategoryTest extends BaseScript {
    public static void main(String[] args) throws InterruptedException {
        // TODO prepare driver object
        String userName = "webinar.test@gmail.com";
        String userPassword = "Xcg7299bnSmMuRLp9ITw";
        String categoryName = "ABC createTestProduct";
        EventFiringWebDriver driver = getConfiguredDriver();
        GeneralActions generalActions = new GeneralActions(driver);
        // login
        generalActions.login(userName, userPassword);
        // create category
        generalActions.createCategory(categoryName);
        driver.navigate().back();
        // check that new category appears in Categories table
        generalActions.performFilterCategory(categoryName);
        driver.quit();
        // ...


    }
}
