package ua.com.qatestlab.prestashopAutomation;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ua.com.qatestlab.prestashopAutomation.page.HomeContent;
import ua.com.qatestlab.prestashopAutomation.page.SearchContent;
import ua.com.qatestlab.prestashopAutomation.util.Util;

import java.util.List;
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest extends WebDriverSettings {

    private static final String PAGE_TITLE = "prestashop-automation";
    private static final String SORTING_PARAMS = "product.price.desc";

    @Test
    @Order(1)
    @Description("In this test we open page for testing.")
    @Step("Open page")
    void openPage() {
        HomeContent page = PageFactory.initElements(driver, HomeContent.class);
        page.open();
        Assertions.assertEquals(PAGE_TITLE, page.getPageTitle());
    }

    @Test
    @Order(2)
    @Description("In this test we check if all products have correct currency.")
    @Step("Check products currency.")
    void checkFeaturedProductsCurrency() {
        HomeContent page = PageFactory.initElements(driver, HomeContent.class);
        List<WebElement> productsList = page.getProductsList();
        long count = productsList.stream().filter(e -> page.getProductCurrencySign(e) == page.getCurrencySign()).count();
        Assertions.assertEquals(productsList.size(), count);
    }

    @Test
    @Order(3)
    @Description("In this test we choose USD currency.")
    @Step("Set currency to USD.")
    void setCurrencyToUSD() {
        HomeContent page = PageFactory.initElements(driver, HomeContent.class);
        page.setCurrencyToUSD();
        Assertions.assertEquals(Util.USD_SIGN, page.getCurrencySign());
    }

    @Test
    @Order(4)
    @Description("In this test we search for dress.")
    @Step("Search for dress.")
    void searchForDress() {
        SearchContent page = PageFactory.initElements(driver, SearchContent.class);
        page.search("dress");
        Assertions.assertTrue(page.isSortDivDisplayed());
    }

    @Test
    @Order(5)
    @Description("In this test we check searched products counter.")
    @Step("Check products counter value.")
    void checkSearchCounter() {
        SearchContent page = PageFactory.initElements(driver, SearchContent.class);
        Assertions.assertEquals(page.getTotalCount(), page.getProductsList().size());
    }

    @Test
    @Order(6)
    @Description("In this test we check if searched products have proper currency.")
    @Step("Check products currency.")
    void checkSearchedProductsCurrency() {
        SearchContent page = PageFactory.initElements(driver, SearchContent.class);
        long count = page.getProductsList().stream().filter(e -> page.getProductCurrencySign(e) == Util.USD_SIGN).count();
        Assertions.assertEquals(page.getProductsList().size(), count);
    }

    @Test
    @Order(7)
    @Description("In this test we sort product by price (from high to low).")
    @Step("Sort products.")
    void sort() throws InterruptedException {
        SearchContent page = PageFactory.initElements(driver, SearchContent.class);
        page.setHighToLowPriceSorting();
        Thread.sleep(1000); // TODO
        Assertions.assertTrue(page.getUrlString().contains(SORTING_PARAMS));
    }

    @Test
    @Order(8)
    @Description("In this test we check if products are in right order after sorting.")
    @Step("Check sorting order.")
    void checkSortingOrder() {
        SearchContent page = PageFactory.initElements(driver, SearchContent.class);
        List<Float> priceList = page.getProductsList().stream().map(page::getProductPrice).collect(Collectors.toList());

        int size = priceList.size();
        boolean isOrderCorrect = priceList.stream().allMatch(e -> {
            if (priceList.indexOf(e) < (size - 1)) {
                return e.compareTo(priceList.get(priceList.indexOf(e) + 1)) <= 0;
            }
            return true;
        });
        Assertions.assertTrue(isOrderCorrect, "Sorting order");
    }

    @Test
    @Order(9)
    @Description("In this test we check if discount price is calculated correctly.")
    @Step("Check discount price.")
    void checkDiscount() {
        SearchContent page = PageFactory.initElements(driver, SearchContent.class);
        WebElement firstDiscountProduct = page.getFirstDiscountProduct();
        float productPrice = page.getProductPrice(firstDiscountProduct);
        float productRegularPrice = page.getProductRegularPrice(firstDiscountProduct);
        int productDiscount = page.getProductDiscount(firstDiscountProduct);
        Assertions.assertEquals(productPrice, productRegularPrice * (1 - ((double) productDiscount / (double) 100)), 0.005);
    }

}
