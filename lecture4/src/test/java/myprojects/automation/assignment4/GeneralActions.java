package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    /*LogIn variables*/
    private By uLogin = By.name("email");
    private By uPassword = By.name("passwd");
    private By submitButton = By.name("submitLogin");
    /*Variebles and Parameters of create category*/
    private String successfulMessage = "Настройки обновлены.";
    private By categoryItem = By.xpath("//*[@id=\"subtab-AdminCatalog\"]/a");
    private By addNewCategory = By.xpath("//*[@id=\"page-header-desc-configuration-add\"]/span");
    private By productNameItem = By.id("form_step1_name_1");
    private By saveButton = By.xpath("//*[@class='product-footer']/div[2]/div/button/span");
    private By fadeMessage = By.className("growl-message");
    private By produceCountTab = By.id("tab_step3");
    private By produceCount = By.id("form_step3_qty_0");
    private By producePricetab = By.id("tab_step2");
    private By producePrice = By.id("form_step2_price");
    private By swichButton = By.className("switch-input");
    boolean isGetName = false;
    private String produceName;
    private String price;
    private int count;
    /*Checking product creation*/
    private By allProduceLink = By.xpath("//*[@id=\"content\"]/section/a");
    private By produceList = By.xpath("//*[contains(text(),'Показано')]");
    private By produceInStoke = By.xpath("//*[@class='product-quantities']/span");
    private boolean isGetPrice;
    private boolean isGetCount;


    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     *
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(uLogin).sendKeys(login);
        driver.findElement(uPassword).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public String getProduceName() {
        if (isGetName == false) {
            produceName = ProductData.generate().getName();
            isGetName = true;
        }
        return produceName;
    }

    public String getPrice() {
        if (isGetPrice == false) {
            price = ProductData.generate().getPrice();
            isGetPrice = true;
        }
        return price;
    }

    public int getCount() {
        if (isGetCount == false) {
            count = (ProductData.generate().getQty());
            isGetCount = true;
        }
        return count;
    }

    public void createProduct(ProductData newProduct) {
        waitForClick(categoryItem);
        waitForClick(addNewCategory);
        driver.findElement(productNameItem).sendKeys(getProduceName());
        driver.findElement(produceCountTab).click();
        driver.findElement(produceCount).sendKeys(String.valueOf(getCount()));
        driver.findElement(producePricetab).click();
        driver.findElement(producePrice).sendKeys(Keys.CONTROL + "a");
        driver.findElement(producePrice).sendKeys(Keys.DELETE);
        driver.findElement(producePrice).sendKeys(getPrice());
        driver.findElement(swichButton).click();
        waitForAMessageLoad(fadeMessage);
        driver.findElement(saveButton).click();
        waitForAMessageLoad(fadeMessage);


    }


    /**
     * Waits until page loader disappears from the page
     */
    public void waitForClick(By element) {
        // TODO implement generic method to wait until page content is loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
        // ...
    }

    public void waitForContentLoad(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void waitForAMessageLoad(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        String testtext = "";
        try {
            testtext = driver.findElement(element).getText();
        } catch (Exception conditions) {
            testtext = driver.findElement(element).getText();
        }
        Assert.assertEquals(successfulMessage, testtext, "Done:new category isn't refreshed" + " - " + testtext);
//        if (successfulMessage.equals(testtext))
//            System.out.println("Passed: new category is saved" + " - " + testtext);
//        else
//            System.out.println("Done:new category isn't saved" + " - " + testtext);
    }


    public void checkingProduct() {
        driver.get(Properties.getBaseUrl());
        driver.findElement(allProduceLink).click();
        getProducePage();
        getProduce();

    }

    public void getProducePage() {
        /*list of pagination pages for getting last page*/
        List<WebElement> elements = driver.findElements(By.xpath("//*[@class=\"page-list clearfix text-xs-center\"]/li/a"));
        By moveForward = By.xpath("//*[@class=\"next js-search-link\"]");
        System.out.println(elements.size());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1500)");

//        driver = new  StaleTolerantWebDriver();
        // checkif pagination link exists
        if (elements.size() > 3) {
            // click on pagination link
            for (int i = 1; i < elements.size() - 1; i++) {
                try {
                    System.out.println(elements.get(i).getText());
//                    driver.findElement(moveForward).click();
                    waitForClick(moveForward);
                    Thread.sleep(2000);
                    Thread.sleep(2000);

                } catch (Exception e) {
                    System.out.println("Stop");
                }

            }
        } else {
            System.out.println("pagination not exists");

        }


    }

    public void getProduce() {
        By currentPrice = By.xpath("//*[@class='current-price']/span");
        By currentCount = By.name("qty");
        Assert.assertEquals(driver.findElement(By.linkText(getProduceName())).getText(),
                getProduceName(),
                "Done: produce name isn't created");
        driver.findElement(By.linkText(getProduceName())).click();

        Assert.assertEquals(driver.findElement(currentPrice).getAttribute("content"),
                getPrice().replace(",", "."),
                "Done:price isn't eqals");

        Assert.assertEquals(getCount(),
                checkCountOnStrock(),
                "Done:count isn't eqals");
    }

    public int checkCountOnStrock() {
        String textDigits = driver.findElement(produceInStoke).getText();

        String str = textDigits.replaceAll("[^0-9]+", " ");
        str = str.replace(" ", "");

        return Integer.parseInt(str);
    }

}