package helpers.customerHelper;
import helpers.Setup.Setup;
import helpers.loginHelper.LoginHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class CustomerHelper {
    public static void clickOnAddCustomer(WebDriver driver){
        WebElement addCustomer = driver.findElement(By.cssSelector("[ng-click='addCust()']"));
        addCustomer.click();
    }

    public static void addCustomer(WebDriver driver,String fname, String lname, String postcode){
        WebElement firstName = driver.findElement(By.cssSelector("[ng-model='fName']"));
        firstName.sendKeys(fname);
        WebElement lastName = driver.findElement(By.cssSelector("[ng-model='lName']"));
        lastName.sendKeys(lname);
        WebElement postCode = driver.findElement(By.cssSelector("[ng-model='postCd']"));
        postCode.sendKeys(postcode);
        WebElement addCust = driver.findElement(By.className("btn-default"));
        addCust.click();
    }
    public static boolean checkAddedCustomer(WebDriver driver, String fname, String lname, String postcode){
        try{
            String message = driver.switchTo().alert().getText();
            if(message.equals("Please check the details. Customer may be duplicate.")) {
                driver.switchTo().alert().accept();
                return true;
            }
            String msg=message.substring(0,46);
            driver.switchTo().alert().accept();
            return LoginHelper.loginAs(driver,fname,lname) && msg.equals("Customer added successfully with customer id :");
        }catch (Exception e) {
            return LoginHelper.loginAs(driver, fname, lname);
        }
    }
}
