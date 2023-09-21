package helpers.loginHelper;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginHelper {

    public static void Login(WebDriver driver){
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        WebElement customerLoginButton = driver.findElement(By.cssSelector("[ng-click='customer()']"));
        customerLoginButton.click();

        WebElement nameDropList = driver.findElement(By.id("userSelect"));
        List<WebElement> options = nameDropList.findElements(By.tagName("option"));
        WebElement randomOption = options.get(2);
        randomOption.click();

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
    }

    public static boolean loginAs(WebDriver driver,String fname, String lname){
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        WebElement customerLoginButton = driver.findElement(By.cssSelector("[ng-click='customer()']"));
        customerLoginButton.click();

        String fullName=fname+" "+lname;

        WebElement nameDropList = driver.findElement(By.id("userSelect"));
        List<WebElement> options = nameDropList.findElements(By.tagName("option"));
        WebElement ourUser = null;
        for(WebElement w:options){
            if(w.getText().equals(fullName))
                ourUser=w;
        }
        try {
            ourUser.click();
        }catch(Exception e){
            return false;
        }

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        WebElement welcomeMessage = driver.findElement(By.cssSelector("span.fontBig.ng-binding"));
        return fullName.equalsIgnoreCase(welcomeMessage.getText());
    }

    public static boolean VerifyLogout(WebDriver driver){
        WebElement logoutButton = driver.findElement(By.cssSelector("button[ng-show='logout'][class='btn logout']"));
        System.out.println(logoutButton.getText());
        logoutButton.click();

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/account");

        WebElement welcomeMessage = driver.findElement(By.cssSelector("span.fontBig.ng-binding"));
        return welcomeMessage.isDisplayed();
    }

    public static boolean LoginAsRandomUser(WebDriver driver){
        WebElement customerLoginButton = driver.findElement(By.cssSelector("[ng-click='customer()']"));
        customerLoginButton.click();

        WebElement nameDropList = driver.findElement(By.id("userSelect"));
        List<WebElement> options = nameDropList.findElements(By.tagName("option"));
        options.remove(0);
        Random random = new Random();
        int randomIndex = random.nextInt(options.size());
        WebElement randomOption = options.get(randomIndex);
        String name=randomOption.getText();
        randomOption.click();

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        WebElement welcomeMessage = driver.findElement(By.cssSelector("span.fontBig.ng-binding"));
        return name.equalsIgnoreCase(welcomeMessage.getText());
    }

    public static void loginAsManager(WebDriver driver){
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        WebElement customerLoginButton = driver.findElement(By.cssSelector("[ng-click='manager()']"));
        customerLoginButton.click();
    }

}