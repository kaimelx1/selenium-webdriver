package ua.com.qatestlab.prestashopAutomation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.com.qatestlab.prestashopAutomation.util.Util;

class Page {

    private WebDriver driver;
    WebDriverWait wait;

    Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    @FindBy(css = ".currency-selector")
    private WebElement currencySelector;

    @FindBy(id = "search_widget")
    private WebElement widget;

    public void open() {
        driver.get("http://prestashop-automation.qatestlab.com.ua/ru/");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getUrlString() {
        return driver.getCurrentUrl();
    }

    public char getCurrencySign() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".currency-selector")));
        return Util.getLastChar(currencySelector.findElement(By.cssSelector("span:nth-child(2)")).getText());
    }

    public void setCurrencyToUSD() {
        currencySelector.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#_desktop_currency_selector ul.dropdown-menu li a")));
        //currencySelector.findElement(By.cssSelector("ul>li:nth-child(3) a")).click(); // TODO element not interactable
        String link = currencySelector.findElement(By.cssSelector("ul>li:nth-child(3) a")).getAttribute("href");
        driver.get(link);
    }

    public void search(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name=\"s\"]")));
        widget.findElement(By.cssSelector("input[name=\"s\"]")).sendKeys(text);
        widget.findElement(By.cssSelector("button[type=\"submit\"]")).click();
    }

    public char getProductCurrencySign(WebElement element) {
        String currencyString = element.findElement(By.cssSelector("span.price")).getText();
        return Util.getLastChar(currencyString);
    }

    public float getProductPrice(WebElement element) {
        String priceString = element.findElement(By.cssSelector("span.price")).getText();
        return Util.getPriceFromPriceString(priceString);
    }

    public int getProductDiscount(WebElement element) {
        String discount = element.findElement(By.cssSelector("span.discount-percentage")).getText();
        return Util.getNumbersFromString(discount);
    }

    public float getProductRegularPrice(WebElement element) {
        String priceString = element.findElement(By.cssSelector("span.regular-price")).getText();
        return Util.getPriceFromPriceString(priceString);
    }
}
