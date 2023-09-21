package helpers.depositHelper;

import helpers.loginHelper.LoginHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class DepositHelper {
    public static void ResetTransactions(WebDriver driver){
        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();
        try {
            driver.findElement(By.cssSelector("[ng-click='reset()']")).click();
        }catch (Exception ignored){

        }
        driver.findElement(By.cssSelector("[ng-click='back()']")).click();
    }
    public static boolean VerifyDepositValue(WebDriver driver,String value){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        //System.out.println(balanceBefore);

        WebElement depositMenu = driver.findElement(By.cssSelector("[ng-click='deposit()']"));
        depositMenu.click();
        WebElement depositValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        depositValue.sendKeys(value);
        WebElement depositSubmit = driver.findElement(By.className("btn-default"));
        depositSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        String confirmation=driver.findElement(By.cssSelector("[ng-show='message']")).getText();
        boolean testRes=confirmation.equals("Deposit Successful");
        testRes&=((balanceAfter-balanceBefore)==Integer.parseInt(value));

        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.navigate().refresh();
        driver.navigate().refresh();
        driver.navigate().refresh();

        String transaction=driver.findElement(By.id("anchor0")).getText().strip();
        String[] temp = transaction.split(" ");
        String substring=temp[temp.length-2]+" "+temp[temp.length-1];

        testRes&=(substring.equals(value+" Credit"));

        return testRes;
    }
    public static boolean VerifyDepositOfZero(WebDriver driver){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        //System.out.println(balanceBefore);

        WebElement depositMenu = driver.findElement(By.cssSelector("[ng-click='deposit()']"));
        depositMenu.click();
        WebElement depositValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        depositValue.sendKeys("0");
        WebElement depositSubmit = driver.findElement(By.className("btn-default"));
        depositSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        boolean testRes=driver.findElement(By.cssSelector("[ng-show='message']")).isDisplayed();
        testRes|=((balanceAfter-balanceBefore)!=0);

        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.navigate().refresh();
        driver.navigate().refresh();
        boolean transactionVisible=false;
        try {
            transactionVisible=driver.findElement(By.id("anchor0")).isDisplayed();
        }catch (Exception ignored){}

        testRes|=transactionVisible;
        return testRes;
    }
    public static boolean VerifyDepositOfNegativeValue(WebDriver driver){
        WebElement info=driver.findElement(By.className("center"));
        int balanceBefore=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        String accountNo=info.getText().split(",")[0].strip().split(":")[1].strip();
        String currency=info.getText().split(",")[2].strip().split(":")[1].strip();
        //System.out.println(balanceBefore);

        WebElement depositMenu = driver.findElement(By.cssSelector("[ng-click='deposit()']"));
        depositMenu.click();
        WebElement depositValue = driver.findElement(By.cssSelector("[ng-model='amount']"));
        depositValue.sendKeys("-5");
        WebElement depositSubmit = driver.findElement(By.className("btn-default"));
        depositSubmit.click();

        //System.out.println(info.getText());
        int balanceAfter=Integer.parseInt(info.getText().split(":")[2].strip().split(",")[0].strip());
        //System.out.println(balanceAfter);

        boolean testRes=driver.findElement(By.cssSelector("[ng-show='message']")).isDisplayed();
        testRes|=((balanceAfter-balanceBefore)!=0);

        WebElement transactionsButton = driver.findElement(By.cssSelector("[ng-click='transactions()']"));
        transactionsButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.navigate().refresh();
        driver.navigate().refresh();
        boolean transactionVisible=false;
        try {
            transactionVisible=driver.findElement(By.id("anchor0")).isDisplayed();
        }catch (Exception ignored){}

        testRes|=transactionVisible;
        return testRes;
    }
}
