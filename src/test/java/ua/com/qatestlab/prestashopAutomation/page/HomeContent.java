package ua.com.qatestlab.prestashopAutomation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomeContent extends Page {

    public HomeContent(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".products article")
    private List<WebElement> productsArticles;

    public List<WebElement> getProductsList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".products article")));
        return productsArticles;
    }

}
