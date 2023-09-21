import java.time.Duration;
import java.util.Set;

import helpers.Setup.Setup;
import helpers.accountsHelper.AccountsHelper;
import helpers.customerHelper.CustomerHelper;
import helpers.depositHelper.DepositHelper;
import helpers.loginHelper.LoginHelper;

import helpers.manageCustomers.ManageCustomers;
import helpers.withdrawHelper.WithdrawHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class XYZBankQA {
    WebDriver driver;

    @BeforeClass(groups = { "login", "deposit", "withdraw", "customers", "accounts", "customer-control" })
    public void setup(){
        driver= Setup.OpenChromeDriver();
        Setup.OpenWebsite(driver);
    }

    @Test(priority=1,groups = { "login" })
    public void LoginWithRandomUser(){
        Assert.assertTrue(LoginHelper.LoginAsRandomUser(driver));
    }

    @Test(priority=2,groups = { "login" })
    public void LogoutCorrectly(){
        Setup.OpenWebsite(driver);
        LoginHelper.LoginAsRandomUser(driver);
        Assert.assertFalse(LoginHelper.VerifyLogout(driver));
    }

    @Test(priority = 3,groups = {"deposit"})
    public void DepositCorrectly(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        Assert.assertTrue(DepositHelper.VerifyDepositValue(driver,"20"));
    }

    @Test(priority = 4,groups = {"deposit"})
    public void DepositZero(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        Assert.assertFalse(DepositHelper.VerifyDepositOfZero(driver));
    }

    @Test(priority = 4,groups = {"deposit"})
    public void DepositNegative(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        Assert.assertFalse(DepositHelper.VerifyDepositOfNegativeValue(driver));
    }

    @Test(priority = 5,groups = {"withdraw"})
    public void WithdrawCorrectly(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        DepositHelper.VerifyDepositValue(driver,"100");
        LoginHelper.Login(driver);
        Assert.assertTrue(WithdrawHelper.VerifyWithdraw(driver));
    }

    @Test(priority = 6,groups = {"withdraw"})
    public void WithdrawZero(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        Assert.assertFalse(WithdrawHelper.VerifyWithdrawZero(driver));
    }

    @Test(priority = 6,groups = {"withdraw"})
    public void WithdrawNegative(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        Assert.assertFalse(WithdrawHelper.VerifyWithdrawNegative(driver));
    }

    @Test(priority = 6,groups = {"withdraw"})
    public void WithdrawMore(){
        LoginHelper.Login(driver);
        DepositHelper.ResetTransactions(driver);
        Assert.assertTrue(WithdrawHelper.VerifyWithdrawMore(driver));
    }

    @Test(priority = 7,groups = {"customers"})
    public void addCustomer(){
        String fname="Sary",lname="Hammad",postcode="90631";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        Assert.assertTrue(CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode));
    }

    @Test(priority = 7,groups = {"customers"})
    public void addCustomerNoFirstName(){
        String fname="",lname="Hammad",postcode="90631";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        Assert.assertFalse(CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode));
    }

    @Test(priority = 7,groups = {"customers"})
    public void addCustomerNoLastName(){
        String fname="Sary",lname="",postcode="90631";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        Assert.assertFalse(CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode));
    }

    @Test(priority = 7,groups = {"customers"})
    public void addCustomerNoPostCode(){
        String fname="Ahmad",lname="Hammad",postcode="";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        Assert.assertFalse(CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode));
    }

    @Test(priority = 7,groups = {"customers"})
    public void addCustomerThatExists(){
        String fname="Leon",lname="Tolaj",postcode="99931A";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        Assert.assertTrue(CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode));
    }

    @Test(priority = 8,groups = {"accounts"})
    public void openAccount(){
        String fname="Mark",lname="Henry",postcode="MH12421",currency="Dollar";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        AccountsHelper.clickOnOpenAccount(driver);
        AccountsHelper.selectAccountDetails(driver,fname+" "+lname,currency);
        Assert.assertTrue(AccountsHelper.verifyNewAccount(driver,fname+" "+lname,currency));
    }

    @Test(priority = 8,groups = {"accounts"})
    public void openAccountWithSameCurrency(){
        String fname="Sewe",lname="Lewis",postcode="SW13003",currency="Dollar";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        AccountsHelper.clickOnOpenAccount(driver);
        AccountsHelper.selectAccountDetails(driver,fname+" "+lname,currency);
        boolean res=AccountsHelper.verifyNewAccount(driver,fname+" "+lname,currency);

        LoginHelper.loginAsManager(driver);
        AccountsHelper.clickOnOpenAccount(driver);
        AccountsHelper.selectAccountDetails(driver,fname+" "+lname,currency);
        res&=AccountsHelper.verifyNewAccount(driver,fname+" "+lname,currency);
        Assert.assertTrue(res);
    }

    @Test(priority = 8,groups = {"accounts"})
    public void openAccountWithoutCustomer(){
        String fname="George",lname="Dann",postcode="G17421",currency="Dollar";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        AccountsHelper.clickOnOpenAccount(driver);
        AccountsHelper.selectAccountDetails(driver,"",currency);
        Assert.assertFalse(AccountsHelper.verifyNewAccount(driver,"",currency));
    }

    @Test(priority = 8,groups = {"accounts"})
    public void openAccountWithoutCurrency(){
        String fname="Carl",lname="Jacob",postcode="00412K",currency="";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        AccountsHelper.clickOnOpenAccount(driver);
        AccountsHelper.selectAccountDetails(driver,fname+" "+lname,currency);
        Assert.assertFalse(AccountsHelper.verifyNewAccount(driver,fname+" "+lname,currency));
    }

    @Test(priority = 9,groups = {"customer-control"})
    public void searchCustomerByFirstName(){
        String fname="Meran",lname="Barghouthi",postcode="O9912";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        ManageCustomers.clickOnShowCustomers(driver);
        ManageCustomers.search(driver,fname.substring(0,Math.min(2,fname.length())));

        Assert.assertTrue(ManageCustomers.verifySearchResult(driver,fname,lname,postcode));
    }

    @Test(priority = 9,groups = {"customer-control"})
    public void searchCustomerByLastName(){
        String fname="Meran",lname="Barghouthi",postcode="O9912";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        ManageCustomers.clickOnShowCustomers(driver);
        ManageCustomers.search(driver,lname.substring(0,Math.min(2,lname.length())));

        Assert.assertTrue(ManageCustomers.verifySearchResult(driver,fname,lname,postcode));
    }

    @Test(priority = 9,groups = {"customer-control"})
    public void searchCustomerByPostCode(){
        String fname="Meran",lname="Barghouthi",postcode="O9912";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        ManageCustomers.clickOnShowCustomers(driver);
        ManageCustomers.search(driver,postcode.substring(0,Math.min(2,postcode.length())));

        Assert.assertTrue(ManageCustomers.verifySearchResult(driver,fname,lname,postcode));
    }

    @Test(priority = 10,groups = {"customer-control"})
    public void deleteCustomer(){
        String fname="Basil",lname="Hawash",postcode="873122";
        LoginHelper.loginAsManager(driver);
        CustomerHelper.clickOnAddCustomer(driver);
        CustomerHelper.addCustomer(driver,fname,lname,postcode);
        CustomerHelper.checkAddedCustomer(driver,fname,lname,postcode);

        LoginHelper.loginAsManager(driver);
        ManageCustomers.clickOnShowCustomers(driver);
        ManageCustomers.search(driver,fname.substring(0,Math.min(2,fname.length())));
        ManageCustomers.deleteSpecificUser(driver,fname,lname,postcode);

        Assert.assertFalse(LoginHelper.loginAs(driver,fname,lname));
    }

    @AfterClass(groups={"login", "deposit", "withdraw", "customers", "accounts"})
    public void exit(){driver.close();}

}