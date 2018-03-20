package myprojects.automation.assignment5;


import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    private String openNewTab = Keys.chord(Keys.CONTROL, "t");

    //    private By allProduceLink = By.xpath("//*[@id=\"content\"]/section/a");/*link to all produce list page*/
    private By allProduceLink = By.className("all-product-link");/*link to all produce list page*/
    /*current produce data*/
    private By someProduce = By.xpath("//*[@class='product-description']/h1/a");/*random produce*/
    private By currentPrice = By.xpath("//*[@class='current-price']/span");
    //    private By currentCount = By.name("qty");
    private By produceInStoke = By.xpath("//*[@class='product-quantities']/span");
    private By pNameElement = By.xpath("//*[@class='h1']");
    private By detailElement = By.xpath("//*[contains(text(),'Подробнее о товаре')]");
    boolean isPName = false;
    boolean isCurPrice = false;
    boolean isProduceInStock = false;
    private String pPrice;
    private String pInStock;
    private String pName;


    /*---------------------------------------------------------------*/
    private By cartBtn = By.xpath("//*[@class='add']/button");

    private By goToCart = By.xpath("//*[@class='cart-content']/a");
    private By cartProduceList = By.xpath("//*[@class='product-line-grid']/div/span/img");
    private By makeOrder = By.xpath("//*[@class='text-xs-center']/a");
    /*Personal Data Elements*/
    private By pFName = By.name("firstname");
    private By pLName = By.name("lastname");
    private By pLogin = By.name("email");
    private By pPassword = By.name("password");
    private By pBtn = By.xpath("//*[@class='form-footer clearfix']/button");
    /*Adress elements*/
    private By adAdress = By.name("address1");
    private By adPostIndex = By.name("postcode");
    private By adCity = By.name("city");
    private By adBtn = By.name("confirm-addresses");
    private By deliveryBtn = By.name("confirmDeliveryOption");
    /*Payment elements*/
    private By radioBtn = By.id("payment-option-2");
    private By checkFamiliar = By.xpath("//*[@class='custom-checkbox']/input");
    private By paymentConfirmBtn = By.xpath("//*[@id='payment-confirmation']/div/button");
    /*Order details elements*/
    private By orderPrice = By.xpath("//*[@class='order-line row']//div[3]/div/div[1]");
    private WebElement random;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public String getIsPName() {
        if (isPName == false) {
            pName = driver.findElement(pNameElement).getText();
            isPName = true;
        }
        return pName;
    }

    public String getCurrentPrice() {
        if (isCurPrice == false) {
//            pPrice = (driver.findElement(currentPrice).getAttribute("content"));
            pPrice = (driver.findElement(currentPrice).getText());
            isCurPrice = true;
        }
        return pPrice.substring(0, pPrice.length() - 3);
    }


    public String getProduceInStoke() {
        if (isProduceInStock == false) {
            driver.findElement(detailElement).click();
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pInStock = driver.findElement(produceInStoke).getText();
            isProduceInStock = true;
        }
        return pInStock;

    }


    public void openRandomProduct() {
        // TODO implement logic to open random product before purchase
        driver.findElement(allProduceLink).click();
        List<WebElement> links = driver.findElements(someProduce);
        random = links.get(new Random().nextInt(links.size()));
        random.click();
        getOpenedProductInfo();
        waitForClickElementLoad(cartBtn);
        waitForClickElementLoad(goToCart);
        //Check produce count in the cart
        List<WebElement> cartProduceList = driver.findElements(this.cartProduceList);
        driver.findElement(makeOrder).click();

    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public void getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
//        getCurrentCount();
        getIsPName();
        getCurrentPrice();
        getProduceInStoke();

    }

    public void personalData(String firstName, String lastName, String login, String password,
                             String address, int postIndex, String cityName) {
        /*Entered contact*/
        driver.findElement(pFName).sendKeys(firstName);
        driver.findElement(pLName).sendKeys(lastName);
        driver.findElement(pLogin).sendKeys(login);
        driver.findElement(pPassword).sendKeys(password);
        driver.findElement(pBtn).click();
        /*address*/
        driver.findElement(adAdress).sendKeys(address);
        driver.findElement(adPostIndex).sendKeys(String.valueOf(postIndex));
        driver.findElement(adCity).sendKeys(cityName);
        driver.findElement(adBtn).click();
        driver.findElement(deliveryBtn).click();

        //payment
        driver.findElement(radioBtn).click();
        driver.findElement(checkFamiliar).click();
        driver.findElement(paymentConfirmBtn).click();

    }

    public void checkOrderDetails() {
        /*Check Order Tittle*/
        String produceName;
        String title = driver.findElement(By.xpath("//*[@class='h1 card-title']")).getText();
        String price = driver.findElement(orderPrice).getText();
        Assert.assertEquals(title.substring(1, 22), "ВАШ ЗАКАЗ ПОДТВЕРЖДЁН", "Fail");
        /*Check Order params*/
        List<WebElement> orderElements = driver.findElements(By.xpath("//*[@class='order-confirmation-table']/div"));

//        System.out.println(getCurrentPrice() + " / " + getIsPName() +
//                "  /" +
//                getProduceInStoke());
        if (orderElements.size() <= 1) {
            produceName = orderElements.get(orderElements.size() - 1).getText().substring(0, getIsPName().length());
            Assert.assertEquals(produceName.toUpperCase(), getIsPName(), "Done: Name isn't equals");
            price = price.substring(0, getCurrentPrice().length());
            Assert.assertEquals(price, getCurrentPrice(), "Done: price isn't equals");
//            System.out.println(price);
        } else {
            System.out.println("Done: more 1 produce in the cart");
        }

    }

    public void backToProducePage() {
//        String linkName = getIsPName().toLowerCase();
//        linkName = linkName.replaceFirst("[a-z]{1}", linkName.substring(0, 1).toUpperCase());
//        System.out.println(linkName);
        String pInStockBefore = pInStock;
        driver.findElement(allProduceLink).click();
        List<WebElement> mainlinks = driver.findElements(someProduce);
        for (int i = 0; i < mainlinks.size(); i++) {
            if (mainlinks.get(i).getText().toUpperCase().equals(getIsPName())) {
                driver.findElement(By.linkText(mainlinks.get(i).getText())).click();
                break;
            }
        }

        waitForClickElementLoad(detailElement);
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String pInStockAfter = "";
        try {
            pInStockAfter = driver.findElement(produceInStoke).getText();
        } catch (Exception e) {
            System.out.println("Produce on stock < 1");
        }
        if (pInStockAfter != "") {
            Assert.assertEquals(checkCountOnStock(pInStockAfter), checkCountOnStock(pInStockBefore) - 1, "Done");
        } else {
            System.out.println("Null products in Stock");
        }

    }

    public void waitForClickElementLoad(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
    }

    public static String generateRandomLogin() {
        Random random = new Random();
        return ("webinar" + random.nextInt(1000) + 1) + ".test@gmail.comm ";
    }

    public int checkCountOnStock(String textDigits) {

        String str = textDigits.replaceAll("[^0-9]+", " ");
        str = str.replace(" ", "");

        return Integer.parseInt(str);
    }
}
