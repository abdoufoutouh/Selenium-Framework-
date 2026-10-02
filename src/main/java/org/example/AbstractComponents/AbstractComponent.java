package org.example.AbstractComponents;
import java.time.Duration;
import org.example.pageobject.CartPage;
import org.example.pageobject.OrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractComponent {
    WebDriver driver;
    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    //button[routerlink='/dashboard/myorders']
    @FindBy(css = "button[routerlink='/dashboard/myorders']")
    WebElement orderHeader ;

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;
    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToAppear(WebElement findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }
    public void waitForElementToDisappear(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(ele));
    }
    public void clickWithJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }
    public CartPage goToCartPage() {
        clickWithJS(cartHeader);   // avant : cartHeader.click();
        CartPage cartPage = new CartPage(driver);
        return cartPage;
    }
    public OrderPage goToOrderPage() {
        clickWithJS(orderHeader);   // avant : cartHeader.click();
        OrderPage cartPage = new OrderPage(driver);
        return cartPage;
    }
    // common to all pages => to make sure orders are displayed
    public OrderPage waitforOrderDisplayed(){
        orderHeader.click();
        OrderPage orderPage = new OrderPage(driver);
        return  orderPage;

    }
}