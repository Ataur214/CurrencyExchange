package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import pages.HomePage;



public class BaseTest {
    private static WebDriver driver=null;
    protected HomePage homepage;

   @BeforeTest
   public void setUp(){
       System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
       driver = new ChromeDriver();
       driver.get("https://www.paysera.bg/v2/en-LT/fees/currency-conversion-calculator");
       driver.manage().window().maximize();
       homepage = new HomePage(driver);
   }

   @AfterTest
   public void tearDown(){
        driver.quit();
   }

   @Test (priority=1)
   public void buySellAmountEmptiedTest(){
       var sellInputTrue = homepage.sellInputBoxEmptyCheck("200");
       Assert.assertTrue(sellInputTrue);
       driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
       var buyInputTrue = homepage.buyInputBoxEmptyCheck("300");
       Assert.assertTrue(buyInputTrue);
       //String loginText = homepage.varifyLoginSuccessText();
       //Assert.assertTrue(loginText.contains("You logged into a secure area!"));
   }

   @Test (priority=2)
   public void ratesAndCurrencyOptionUpdated(){
       var defaultCurrency = homepage.getSelectedCurrency();
       System.out.println("Default Selected Currency "+defaultCurrency);

       var defaultOfficialRate = homepage.getRates(1);
       System.out.println("Default Official Rates is: "+ defaultOfficialRate);
       var defaultCompanyRate = homepage.getRates(2);
       System.out.println("Default Company Rates is: "+ defaultCompanyRate);

       homepage.clickFooterDropdownButton();
       homepage.selectCountryFromDropdown();
       var updatedCurrency = homepage.getSelectedCurrency();
       System.out.println("Updated Currency is "+updatedCurrency);
       Assert.assertFalse(updatedCurrency.contains(defaultCurrency));

       var updatedOfficialRate = homepage.getRates(1);
       System.out.println("Updated Official Rates is: "+ updatedOfficialRate);
       var updatedCompanyRate = homepage.getRates(2);
       System.out.println("Updated Company Rates is: "+ updatedCompanyRate);
       Assert.assertFalse(updatedOfficialRate.contains(updatedCompanyRate));
   }

   @Test (priority=3)
   public void lossAmountCalculation(){
       var systemAmount = homepage.getSystemAmount();
       System.out.println("System amount is: " + systemAmount);
       var bankOneAmount = homepage.getBankAmount();
       System.out.println("Bank amount is: " + bankOneAmount);

       var lossAmount = homepage.getLossAmount();
       System.out.println("Loss amount is : " + lossAmount);

       var calculatedSubAmount = homepage.subtractCalculation(Double.parseDouble(bankOneAmount),Double.parseDouble(systemAmount));
       System.out.println("After Subtraction the value is : " + calculatedSubAmount);
       Assert.assertEquals("("+calculatedSubAmount+")",lossAmount);

   }
}
