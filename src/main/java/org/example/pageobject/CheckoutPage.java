package org.example.pageobject;
import org.example.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class CheckoutPage extends AbstractComponent {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(css = ".ngx-spinner-overlay")
    private WebElement spinner;

    @FindBy(css = ".action__submit")
    private WebElement submit;

    @FindBy(css = "[placeholder='Select Country']")
    private WebElement country;

    @FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
    private WebElement selectCountry;

    By results = By.cssSelector(".ta-results");

    public void selectCountry(String countryName) {
        waitForWebElementToAppear(country);
        country.click();
        country.sendKeys(countryName);
        waitForElementToAppear(results);
        clickWithJS(selectCountry);      // avant : selectCountry.click();
    }
    public ConfirmationPage submitOrder() {
        waitForElementToDisappear(spinner);
        clickWithJS(submit);
        return new ConfirmationPage(driver);
    }



}