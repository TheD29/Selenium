package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {

    @DataProvider(name = "Authentication")
    public static Object[][] params() {
        return new Object[][]{{"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}};
    }

    // Here we are calling the Data Provider object with its Name

    @Test(dataProvider = "Authentication", enabled = true)
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation
        actions.login(login, password);
        actions.createProduct(ProductData.generate());
    }


    @Test(dependsOnMethods = "createNewProduct",enabled = true)
    public void checkProductView() throws InterruptedException {
        // TODO implement logic to check product visibility on website
        actions.checkingProduct();
        Thread.sleep(1500);
    }
}
