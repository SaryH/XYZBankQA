package helpers.manageCustomers;
import helpers.Setup.Setup;
import helpers.loginHelper.LoginHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ManageCustomers {
    public static void clickOnShowCustomers(WebDriver driver){
        WebElement openAcc = driver.findElement(By.cssSelector("[ng-click='showCust()']"));
        openAcc.click();
    }
    public static void search(WebDriver driver, String search){
        WebElement searchBar = driver.findElement(By.cssSelector("[ng-model='searchCustomer']"));
        searchBar.sendKeys(search);
    }
    public static boolean verifySearchResult(WebDriver driver, String fname, String lname, String postcode){
        WebElement resultTable=driver.findElement(By.className("table-bordered"));
        List<WebElement> rows = resultTable.findElements(By.tagName("tr"));
        rows.remove(0);
        String actual=fname+" "+lname+" "+postcode;
        for(WebElement row:rows){
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String current="";
            for(int i=0; i<3;i++)
                current+=cells.get(i).getText()+" ";
            current=current.strip();
            if(current.equalsIgnoreCase(actual))
                return true;
        }
        return false;
    }
    public static boolean deleteSpecificUser(WebDriver driver, String fname, String lname, String postcode){
        WebElement resultTable=driver.findElement(By.className("table-bordered"));
        List<WebElement> rows = resultTable.findElements(By.tagName("tr"));
        rows.remove(0);
        String actual=fname+" "+lname+" "+postcode;
        for(WebElement row:rows){
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String current="";
            for(int i=0; i<3;i++)
                current+=cells.get(i).getText()+" ";
            current=current.strip();
            if(current.equalsIgnoreCase(actual)) {
                //System.out.println(current);
                try {
                    cells.get(4).findElement(By.cssSelector("[ng-click='deleteCust(cust)']")).click();
                }catch(Exception e) {
                    return false;
                }
                return true;

            }
        }
        return false;
    }
}
