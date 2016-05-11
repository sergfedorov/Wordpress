package BlogPOM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginPage {

    WebDriver driver;

    @FindBy(how= How.ID, using = "user_login")
            WebElement usernameField;
    @FindBy(how= How.ID, using = "user_pass")
            WebElement passwordField;
    @FindBy(how= How.ID, using = "wp-submit")
            WebElement submitLoginButton;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        driver.get("https://sergeywebdrivertest.wordpress.com/wp-login.php");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public void login(){
        usernameField.sendKeys("editorwebdrivertest");
        passwordField.sendKeys("EditorTest");
        submitLoginButton.click();
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.urlContains("wp-admin"));
    }

}
