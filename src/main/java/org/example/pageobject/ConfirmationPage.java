package org.example.pageobject;
import org.example.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage  extends AbstractComponent {
    // The design pattern used is the Page object pattern and page factory pattern
    // to create locators objects
    WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    // the "THANKYOU FOR THE ORDER." message on the confirmation page
    @FindBy(css = ".hero-primary")
    private WebElement confirmationMessage;

    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }
}
