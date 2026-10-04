package org.example.stepdefinitions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.TestComponents.BaseTest;
import org.example.pageobject.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.io.IOException;
import java.util.List;

public class StepDefintionImpl extends BaseTest {

    // declared at class level to share them between steps
    LandingPage landingPage;
    ProductCatalogue productCatalogue;
    ConfirmationPage confirmationPage;
    List<WebElement> products;

    @Given("I landed on Ecomerce Page")
    public void I_landed_on_e_commerce_page() throws IOException {
        landingPage = lanchApplication();
    }

    @Given("^Logging with (.+) and (.+)$")
    public void logged_username_password(String username, String password) {
        productCatalogue = landingPage.loginApplication(username, password);
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productName) throws InterruptedException {
        products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout (.+) is displayed$")
    public void checkout(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay(productName);
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        confirmationPage = checkoutPage.submitOrder(); // assigns the field, no new variable
    }

    @Then("^(.+) is diplayed$")
    public void message_display(String message) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertEquals(confirmMessage.trim().toUpperCase(), message.trim().toUpperCase());
        driver.close();
    }
}