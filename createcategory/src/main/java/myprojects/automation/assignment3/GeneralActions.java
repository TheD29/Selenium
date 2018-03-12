package myprojects.automation.assignment3;

import myprojects.automation.assignment3.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private EventFiringWebDriver driver;
    private WebDriverWait wait;
    /*LogIn variables*/
    private By uLogin = By.name("email");
    private By uPassword = By.name("passwd");
    private By submitButton = By.name("submitLogin");

    /*Variebles and Parameters of create category*/
    private String successfulMessage = "Настройки обновлены.";
    private By categoryItem = By.xpath("//*[@id=\"subtab-AdminCatalog\"]/a/span");
    private By addNewCategory = By.xpath("//*[@id=\"page-header-desc-configuration-add\"]/span");
    private By productNameItem = By.id("form_step1_name_1");
    private By saveButton = By.xpath("//div[@class =\"btn-group hide dropdown pull-right\"]/button");
    private By fadeMessage = By.className("growl-message");

    //    /*find by name*/
    private By performButton = By.name("products_filter_submit");
    private By resetButton = By.name("products_filter_reset");
    private By emptyClick = By.className("column-filters");
    private By searchField = By.name("filter_column_name");


    public GeneralActions getProductName(String categoryName) {
        driver.findElement(productNameItem).sendKeys(categoryName);
        return this;
    }


    public GeneralActions(EventFiringWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }


    public GeneralActions getuLogin(String login) {
        driver.findElement(uLogin).sendKeys(login);
        return this;
    }

    public GeneralActions getuPassword(String password) {
        driver.findElement(uPassword).sendKeys(password);
        return this;
    }

    public GeneralActions getSubmitButton() {
        driver.findElement(submitButton).click();
        return this;
    }


    /**
     * Logs in to Admin Panel.
     */
    public void login(String userName, String userPassword) {
        openSite();
        getuLogin(userName);
        getuPassword(userPassword);
        getSubmitButton();

    }

    /**
     * Adds new category in Admin Panel.
     */
    public void createCategory(String categoryName) {
        waitForContentLoad(categoryItem);
        waitForContentLoad(addNewCategory);
        getProductName(categoryName);
        driver.findElement(saveButton).click();
        waitForAMessage(fadeMessage);

    }

    public void waitForContentLoad(By element) {
        // TODO implement generic method to wait until page content is loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
    }

    public void waitForAMessage(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
//        boolean getMessage = false;
        String testtext = "";
        try {
            testtext = driver.findElement(element).getText();
//            getMessage = true;
        } catch (Exception conditions) {
//            getMessage = false;
        }

        if (successfulMessage.equals(testtext))
            System.out.println("Passed: new category is saved" + " - " + testtext);
        else
            System.out.println("Done:new category isn't saved" + " - " + "Настройки не обновлены.");
    }

    public void performFilterCategory(String categoryName) {
        boolean checkButton = false;
        try {
            driver.findElement(resetButton).click();
            checkButton = true;
        } catch (Exception e) {
            driver.findElement(searchField).sendKeys(categoryName);
            driver.findElement(emptyClick).click();
            driver.findElement(performButton).click();
        }
        if (checkButton == true) {
            driver.findElement(searchField).sendKeys(categoryName);
            driver.findElement(emptyClick).click();
            driver.findElement(performButton).click();

        }
        /*Checking the category creation*/
        List<WebElement> elements = driver.findElements(By.xpath("//tr/td[3]/a"));
        for (WebElement ele : elements) {
            if (ele.getText().equals(categoryName)){
                System.out.println("Passed: new category has been created with name" + " - " + ele.getText());}
            else
                System.out.println("Done: new category wasn't created");
        }
    }


    public void openSite() {
        driver.get(Properties.getBaseAdminUrl());
    }

}
