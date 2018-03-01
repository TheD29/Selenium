package myprojects.automation.assignment2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */

public abstract class BaseScript {



    /**
     * @return New instance of {@link WebDriver} object.
     */
    public static WebDriver getDriver() {
        // TODO return  WebDriver instance

        System.setProperty("webdriver.chrome.driver", "c:\\automation\\QADriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);


    }


}
