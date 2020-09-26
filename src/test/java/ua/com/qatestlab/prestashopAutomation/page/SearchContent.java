package ua.com.qatestlab.prestashopAutomation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ua.com.qatestlab.prestashopAutomation.util.Util;

import java.util.List;

public class SearchContent extends Page {

    public SearchContent(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "products")
    private WebElement productsSection;

    @FindBy(css = "#products article")
    private List<WebElement> productsArticles;

    @FindBy(css = ".sort-by-row")
    private WebElement sortDiv;

    private By productsArticleLocator = By.cssSelector("#products article");

    public boolean isSortDivDisplayed() {
        return sortDiv.isDisplayed();
    }

    public int getTotalCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("products")));
        return Util.getNumbersFromString(productsSection.findElement(By.cssSelector(".total-products")).getText());
    }

    public List<WebElement> getProductsList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsArticleLocator));
        return productsArticles;
    }

    public void setHighToLowPriceSorting() {
        sortDiv.findElement(By.cssSelector("a.select-title")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sort-by-row div.dropdown-menu")));
        sortDiv.findElement(By.cssSelector("div.dropdown-menu>a:nth-child(5)")).click();
    }

    public WebElement getFirstDiscountProduct() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsArticleLocator));
        List<WebElement> elements = productsSection.findElements(By.cssSelector("article span.discount-percentage"));
        return elements.get(0).findElement(By.xpath("../../../.."));
    }

}
