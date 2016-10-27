package BlogTest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;

    /*public static WebDriver getWebDriverInstance(){
        if(driver == null){
            WebDriver driver = getDriver(getBrowserTypeFromProperty());
        }
        return driver;
    }*/

    public BaseTest(){
        driver = getDriver(getBrowserTypeFromProperty());
    }


    public enum BrowserType {
        FIREFOX("firefox"),
        CHROME("chrome");

        private String value;
        BrowserType(String value) {
            this.value = value;
        }

        public String getBrowserName() {
            return this.value;
        }
    }

    // Take enum BrowserType (firefox or chrome) as input parameter and return initialized WebDriver
    public static WebDriver getDriver(BrowserType browserType) {
        switch (browserType) {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            default:
                driver = new FirefoxDriver();
                break;
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    // Read the properties file and return enum BrowserType (firefox or chrome) from it
    public static BrowserType getBrowserTypeFromProperty() {
        BrowserType type = null;
        try {
            FileInputStream file = new FileInputStream("wordpress.properties");
            Properties properties = new Properties();
            properties.load(file);
            String browserName = properties.getProperty("browser");
            for (BrowserType bType : BrowserType.values()) {
                if (bType.getBrowserName().equalsIgnoreCase(browserName)) {
                    type = bType;
                    System.out.println("Browser is " + bType);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }

    // Take a screenshot in case of failure and save it
    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss");
        Date date = new Date();
        if (testResult.getStatus() == ITestResult.FAILURE) {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("D:\\ScreenShots\\ScreenShot_"+ dateFormat.format(date)+".jpg"));
            System.out.println("Test failed. Taking a screenshot...");
        }
    }
}
