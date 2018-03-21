package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.GeneralActions;
import myprojects.automation.assignment5.utils.Properties;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {
    @DataProvider(name = "testParams")
    public static Object[][] params() {
        return new Object[][]{{"MyFirstNameIs", "MyLastNameIs",
                GeneralActions.generateRandomLogin(), "Xcg7299bnSmMuRLp9ITw",
                "MyAddressIs 81", 77300, "MyCityNameIs"}};
    }

    @Test(enabled = true)
    public void checkSiteVersion() {
        driver.get(Properties.getBaseUrl());

    }

    @Test(dataProvider = "testParams", dependsOnMethods = "checkSiteVersion")
    public void createNewOrder(String firstName, String lastName, String login, String password,
                               String address, int postIndex, String cityName) {
        // TODO open main page and validate website version
        driver.get(Properties.getBaseUrl());
        // TODO implement order creation test
        actions.openRandomProduct();

        actions.personalData(firstName,
                lastName,
                login,
                password,
                address,
                postIndex,
                cityName);

        actions.checkOrderDetails();
        // place new order and validate order summary

        // check updated In Stock value
        actions.backToProducePage();
    }


}
