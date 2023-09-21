package helpers.accountsHelper;
import helpers.Setup.Setup;
import helpers.loginHelper.LoginHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountsHelper {
    public static void clickOnOpenAccount(WebDriver driver){
        WebElement openAcc = driver.findElement(By.cssSelector("[ng-click='openAccount()']"));
        openAcc.click();
    }
    public static void selectAccountDetails(WebDriver driver, String customer, String currency){
        WebElement nameDropList = driver.findElement(By.id("userSelect"));
        List<WebElement> options = nameDropList.findElements(By.tagName("option"));
        WebElement nameElement=null;
        for(WebElement w: options){
            if(w.getText().equalsIgnoreCase(customer)) {
                nameElement = w;
                nameElement.click();
            }
        }
        WebElement currencyDropList = driver.findElement(By.id("currency"));
        List<WebElement> optionsCurrency=currencyDropList.findElements(By.tagName("option"));
        WebElement currencyElement=null;
        for(WebElement w: optionsCurrency){
            if(w.getText().equalsIgnoreCase(currency)) {
                currencyElement = w;
                currencyElement.click();
            }
        }
        WebElement processButton = driver.findElement(By.xpath("//button[text()='Process']"));
        processButton.click();
    }
    public static boolean verifyNewAccount(WebDriver driver, String customer, String currency){
        boolean res=true;
        String accountNo=" ";
        try {
            String message = driver.switchTo().alert().getText();
            //System.out.println(message);
            driver.switchTo().alert().accept();
            res=message.split(":")[0].strip().equals("Account created successfully with account Number");
            accountNo=message.split(":")[1].strip();
        }catch (Exception e){
            return false;
        }
        res&=LoginHelper.loginAs(driver,customer.split(" ")[0],customer.split(" ")[1]);

        WebElement accountNoList = driver.findElement(By.id("accountSelect"));
        List<WebElement> accountOptions = accountNoList.findElements(By.tagName("option"));
        WebElement newAccount=null;
        for(WebElement w: accountOptions){
            if(w.getText().equalsIgnoreCase(accountNo)) {
                newAccount = w;
                newAccount.click();
            }
        }
        if(newAccount==null)
            return false;

        WebElement info=driver.findElement(By.className("center"));
        res&=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip())==0;
        res&=info.getText().split(",")[0].strip().split(":")[1].strip().equalsIgnoreCase(accountNo);
        res&=info.getText().split(",")[2].strip().split(":")[1].strip().equalsIgnoreCase(currency);

        return res;
    }
}
