package helpers.withdrawHelper;
import helpers.Setup.Setup;
import helpers.loginHelper.LoginHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class WithdrawHelper {
    public static boolean VerifyWithdraw(WebDriver driver){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        System.out.println(balanceBefore);

        WebElement withdrawMenu = driver.findElement(By.cssSelector("[ng-click='withdrawl()']"));
        withdrawMenu.click();
        WebElement withdrawValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        withdrawValue.sendKeys("50");
        WebElement withdrawSubmit = driver.findElement(By.className("btn-default"));
        withdrawSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        String confirmation=driver.findElement(By.cssSelector("[ng-show='message']")).getText().strip();
        boolean testRes=confirmation.equals("Transaction successful");
        testRes&=((balanceAfter-balanceBefore)==-50);

        Setup.OpenWebsite(driver);
        LoginHelper.Login(driver);
        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        //driver.navigate().refresh();
        //driver.navigate().refresh();
        //driver.navigate().refresh();
        String transaction="";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        try {
            transaction = driver.findElement(By.id("anchor1")).getText();
        }catch (Exception ignored){
            return false;
        }
        String[] temp = transaction.split(" ");
        String substring=temp[temp.length-2]+" "+temp[temp.length-1];

        testRes&=(substring.equals("50 Debit"));
        return testRes;
    }
    public static boolean VerifyWithdrawZero(WebDriver driver){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        System.out.println(balanceBefore);

        WebElement withdrawMenu = driver.findElement(By.cssSelector("[ng-click='withdrawl()']"));
        withdrawMenu.click();
        WebElement withdrawValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        withdrawValue.sendKeys("0");
        WebElement withdrawSubmit = driver.findElement(By.className("btn-default"));
        withdrawSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        String confirmation=driver.findElement(By.cssSelector("[ng-show='message']")).getText().strip();
        boolean testRes=confirmation.equals("Transaction successful");
        testRes|=((balanceAfter-balanceBefore)!=0);

        Setup.OpenWebsite(driver);
        LoginHelper.Login(driver);
        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.navigate().refresh();
        //driver.navigate().refresh();
        //driver.navigate().refresh();

        boolean transactionVisible=false;
        try {
            transactionVisible=driver.findElement(By.id("anchor0")).isDisplayed();
        }catch (Exception ignored){}

        testRes|=transactionVisible;
        return testRes;
    }

    public static boolean VerifyWithdrawNegative(WebDriver driver){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        System.out.println(balanceBefore);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        WebElement withdrawMenu = driver.findElement(By.cssSelector("[ng-click='withdrawl()']"));
        withdrawMenu.click();
        WebElement withdrawValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        withdrawValue.sendKeys("-20");
        WebElement withdrawSubmit = driver.findElement(By.className("btn-default"));
        withdrawSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        String confirmation=driver.findElement(By.cssSelector("[ng-show='message']")).getText().strip();
        boolean testRes=confirmation.equals("Transaction successful");
        testRes|=((balanceAfter-balanceBefore)!=0);

        Setup.OpenWebsite(driver);
        LoginHelper.Login(driver);
        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.navigate().refresh();
        //driver.navigate().refresh();
        //driver.navigate().refresh();

        boolean transactionVisible=false;
        try {
            transactionVisible=driver.findElement(By.id("anchor0")).isDisplayed();
        }catch (Exception ignored){}

        testRes|=transactionVisible;
        return testRes;
    }

    public static boolean VerifyWithdrawMore(WebDriver driver){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        System.out.println(balanceBefore);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        WebElement withdrawMenu = driver.findElement(By.cssSelector("[ng-click='withdrawl()']"));
        withdrawMenu.click();
        WebElement withdrawValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        withdrawValue.sendKeys("20");
        WebElement withdrawSubmit = driver.findElement(By.className("btn-default"));
        withdrawSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        String confirmation=driver.findElement(By.cssSelector("[ng-show='message']")).getText().strip();
        boolean testRes=confirmation.equals("Transaction Failed. You can not withdraw amount more than the balance.");
        testRes&=((balanceAfter-balanceBefore)==0);

        Setup.OpenWebsite(driver);
        LoginHelper.Login(driver);
        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.navigate().refresh();
        //driver.navigate().refresh();
        //driver.navigate().refresh();

        boolean transactionVisible=false;
        try {
            transactionVisible=driver.findElement(By.id("anchor0")).isDisplayed();
        }catch (Exception ignored){}

        testRes&=!transactionVisible;
        return testRes;
    }

}
