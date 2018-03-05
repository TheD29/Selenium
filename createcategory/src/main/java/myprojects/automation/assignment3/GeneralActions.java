package myprojects.automation.assignment3;

import myprojects.automation.assignment3.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private String userName = "webinar.test@gmail.com";
    private String userPassword = "Xcg7299bnSmMuRLp9ITw";
    /*Variebles and Parameters of create category*/
    private By categoryItem = By.xpath("//*[@id=\"subtab-AdminCatalog\"]/a/span");
    private By addNewCategory = By.xpath("//*[@id=\"page-header-desc-configuration-add\"]/span");
    private By productNameItem = By.id("form_step1_name_1");
    private String categoryName = "ABC createTestProduct";
    private By saveButton = By.xpath("//*[@id=\"form\"]/div[4]/div[2]/div/button[1]");
    private By fadeMessage = By.className("growl-message");

    /*Order category*/
    private By orderButton = By.xpath("//*[@id=\"product_catalog_list\"]/div[2]/div/table/thead/tr[1]/th[3]/span[1]");
    private By performButton = By.name("products_filter_submit");
    private By contentRow = By.xpath("//*[@id=\"product_catalog_list\"]/div[2]/div/table/tbody/tr[1]");
    /*find by name*/
    private By table = By.xpath("//*[@id=\"product_catalog_list\"]/div[2]/div/table/thead/tr[2]");
    private By filterField = By.xpath("//*[@id=\"product_catalog_list\"]/div[2]/div/table/thead/tr[2]/th[3]/input");
    private By resetButton = By.name("products_filter_reset");


    public GeneralActions getProductName() {
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
    public void login() {
        openSite();
        getuLogin(userName);
        getuPassword(userPassword);
        getSubmitButton();

    }

    /**
     * Adds new category in Admin Panel.
     */
    public void createCategory() {
        waitForContentLoad(categoryItem);
        waitForContentLoad(addNewCategory);
        getProductName();
        driver.findElement(saveButton).click();
        waitForAMessage(fadeMessage);

    }

    public void performFilterCategory() {
        driver.findElement(filterField).sendKeys(categoryName);
        driver.findElement(table).click();
        driver.findElement(performButton).click();
    }

    public void orderCategory() {
        driver.findElement(orderButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(categoryName)));
        driver.findElement(By.linkText(categoryName)).getText();
    }

    public void waitForContentLoad(By element) {
        // TODO implement generic method to wait until page content is loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
    }


    public void waitForOrderReload() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(categoryName)));
        driver.findElement(By.linkText(categoryName)).getText();
    }

    public void waitForAMessage(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        boolean getMessage = false;
        String testtext = "";
        try {
            testtext = driver.findElement(element).getText();
            getMessage = true;
        } catch (Exception conditions) {
            getMessage = false;
        }

        if (getMessage == true)
            System.out.println("Passed: new category is saved" + " - " + testtext);
        else
            System.out.println("Done:new category isn't saved" + " - " + getMessage);

    }

    public void openSite() {
        driver.get(Properties.getBaseAdminUrl());
    }

}
