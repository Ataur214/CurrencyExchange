package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;


public class HomePage {

    private static WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;

    private By notEmptyField = By.cssSelector(".rate-table-filter input.ng-not-empty");
    private By emptyField = By.cssSelector(".rate-table-filter input.ng-empty");
    private By footerButton = By.cssSelector(".js-localization-popover");
    private By countryDropDown = By.cssSelector("button#countries-dropdown");
    private By countryList = By.cssSelector("ul[aria-labelledby=\'countries-dropdown\'] li");
    private By currencyOption = By.cssSelector(".rate-table-filter .ui-select-match-text");
    private By tableRowsList = By.cssSelector("table tbody tr");
    private By tableData = By.cssSelector("td.ng-scope");
    private By tableDataBank = By.cssSelector("td.ng-scope .ng-binding");
    private By lossAmountData = By.cssSelector("td .other-bank-loss");


    public HomePage(WebDriver driver){
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, 10);
    }

    public boolean sellInputBoxEmptyCheck(String userInput){
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        actions.moveToElement(driver.findElement(notEmptyField));
        actions.perform();

        var sellBoxText = driver.findElement(notEmptyField).getAttribute("value");
        System.out.println("Sell box amount is "+ sellBoxText);

        //Take user input in empty field (BuyBox)
        driver.findElements(emptyField).get(1).sendKeys(userInput);

        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        var buyBoxText = driver.findElement(notEmptyField).getAttribute("value");
        System.out.println("Input Buy box amount is "+ buyBoxText);

        if(sellBoxText!=buyBoxText){
            System.out.println("Sell box amount is emptied");
            return true;
        }else{return false;}
    }

    public boolean buyInputBoxEmptyCheck(String userInput){
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        actions.moveToElement(driver.findElement(notEmptyField));
        actions.perform();

        var buyBoxText = driver.findElement(notEmptyField).getAttribute("value");
        System.out.println("Buy box amount is "+ buyBoxText);

        //Take user input in empty field (SellBox)
        driver.findElements(emptyField).get(0).sendKeys(userInput);

        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        var sellBoxText = driver.findElement(notEmptyField).getAttribute("value");
        System.out.println("Input Sell box amount is "+ sellBoxText);

        if(sellBoxText!=buyBoxText){
            System.out.println("Buy box amount is emptied");
            return true;
        }else{return false;}
    }

    public void clickFooterDropdownButton(){
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
        actions.moveToElement(driver.findElement(footerButton));
        actions.perform();
        driver.findElement(footerButton).click();
    }

    public void selectCountryFromDropdown(){
        wait.until(ExpectedConditions.elementToBeClickable(currencyOption));
        driver.findElement(countryDropDown).click();
        driver.findElements(countryList).get(3).click();
    }

    public String getSelectedCurrency(){
        wait.until(ExpectedConditions.elementToBeClickable(currencyOption));
        actions.moveToElement(driver.findElements(currencyOption).get(0));
        actions.perform();
        var selectedCurrency = driver.findElements(currencyOption).get(0).getText();
        return selectedCurrency;
    }

    public String getRates(int rate){
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRowsList));
        var currencyRate = driver.findElements(tableData).get(rate).getText();
        return currencyRate;
    }

    public String getSystemAmount(){
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRowsList));
        var amount = driver.findElements(tableData).get(3).getText();
        return amount;
    }

    public String getBankAmount(){
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRowsList));
        var amount = driver.findElements(tableDataBank).get(2).getText();
        return amount;
    }

    public String getLossAmount(){
        wait.until(ExpectedConditions.presenceOfElementLocated(tableRowsList));
        var amount = driver.findElements(lossAmountData).get(0).getText();
        return amount;
    }

    public String subtractCalculation(double bankAmount,double companyAmount){
        double subAmount  = (bankAmount - companyAmount);
        String formattedDouble = String.format("%.2f", subAmount);
        return formattedDouble;
    }



}
