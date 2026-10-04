package org.example.tests;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.example.TestComponents.BaseTest;
import org.example.pageobject.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v150.page.model.Screenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

// this is the main class test case scenario
// lets have a base test class that have all the thing we use on every test
// and call the method
public class SubmitOrderTest  extends BaseTest {

    String productName = "ZARA COAT 3";
    // to declare a test case in Selenium as we saw with TestNG we use it with the annotations
    // @Test -  @BeforeTest - @BeforeMethod ect
    // Not the main method
    // to attach the data to the method we use simply the dataProvider="getData"



    @Test(groups = {"Purchase"},dataProvider = "getData")
    // catch the data
    public void Loginapplication (HashMap<String,String> input) throws InterruptedException, IOException {

        // this will call the method that run the application
        // the method that run the application uses the method that invoke the browser
        // so  speaking two action invoke the browser that go to the page in question
        // and this method ca be used for every test case we simply need to call the method depends
        // on the webpage specifications
        // this is to call the method first
        // in order to optimise our code
        // lets use the @beforemethod
        // and this line will not need to be used
        // LandingPage  landingPage = lanchApplication();
        // Its not hardcoded after using the dataprovider we pass the variable names instead of the harcoded Value
        // and like that we ca not just test our test case with X content of data my with multiple content
        // of data

        ProductCatalogue productCatalogue =landingPage.loginApplication(input.get("email"),input.get("password"));
        List<WebElement> products = productCatalogue.getProductList();

        productCatalogue.addProductToCart(input.get("productName"));

        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");

        // the missing step : place the order
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

    }
    // ----------------------- DataSetProvider Imputs test case senarios ---------------//

    // the method getData help to get Data to use it to test the outputs
    // we will see how extract the data from a JSON File to use it as imput
    // we use a data provider : its like we declare a multidimension tableau
    // and we attach / pass the multidimension tableau with the imput required for our test case
    // to the test case senario method

    public File getSctreenShot(String testcasename) throws IOException{

        // this is how to take a screenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        // this is how to catch the screenshot and declare a file to catch it
        // with the method getScreenShotAs
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        // the idea is to use the Fileutils object that have the copyfile() method
        // this method take on 2 arguments source : the screenshot
        // and destination where we want to put our file : dest
        // here the destination is our project in the package reports
        // used to report the results od all test case senarios so each report
        // shoul mention also the test casse scenarion related

        File dest = new File(("user.dir")+"//reports//"+testcasename+".png");
        FileUtils.copyFile(src,dest);
        return dest;

    }
    // Data Importation By Hashmap

    @DataProvider
    public Object[][] getData() throws IOException {
        // Récupéragion des hasmap
        // et conversion on utilisant le TestNg en Obejc[][] qui est un tableau a deux dimensions
        List<HashMap<String,String>> data =
                getJasonToMap(new File(System.getProperty(("user.dir")
                        + "\\src\\test\\java\\org\\example\\Data\\PurchaseOrder.json")));
        return  new Object[][] { {data.get(0),data.get(1)}

    };
}
}