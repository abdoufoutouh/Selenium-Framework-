package org.example.tests;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.example.Listners.Retry;
import org.example.TestComponents.BaseTest;
import org.example.pageobject.CartPage;
import org.example.pageobject.OrderPage;
import org.example.pageobject.ProductCatalogue;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class ErrorValidations extends BaseTest {

    String productName ="ZARA COAT 3" ;
    //Testing failed login checking if the login message is shown
    @Test(groups = "{Error handling}" , retryAnalyzer = Retry.class )
    public void Loginapplication() throws IOException , InterruptedException {

        //------------------ Test Extent Reports ----------------//

        String path = System.getProperty("user.dir")+"\\repots\\index.html";
        // the extendSparkReporter here create a report means  HTML File a partir of the file
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        // to config  the reporting file
        reporter.config().setReportName("Web automation Results ");
        reporter.config().setDocumentTitle("Test Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setSystemInfo("Tester","Abdou");


        // ---------- Test implementation ------------------------//
        ProductCatalogue productCatalogue =
                landingPage.loginApplication("foutouhabderrahman8@gmail.com", "nN1Q41]pXV2r");
        // ng-dirty ng-touched ng-valid ng-submitted

        System.out.println(landingPage.getErreurMessage());
//        Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());

    }

     //correct password : nN1Q41]pXV2r
     @Test(groups = {"Error handling"})
    public void submitorder() throws IOException,InterruptedException{
        ProductCatalogue productCatalogue = landingPage.loginApplication("abdou@gmail.com", "aUsAEC2Z8GsntPRz");
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 3");
        Assert.assertTrue(match);
    }

    // how to use Test Strategy to connect dependency lets take we have test A and test B
    // here we want to verify that after the submit order action if we navigate to orders the
    // output product is displayed on the orders page for that we will use the concept of dependency
    // to connect the two test cases

    @Test(dependsOnMethods = {"submitorder"})
    public void OrderHistoryTest(){
        ProductCatalogue productCatalogue = landingPage.loginApplication("abdou@gmail.com"
                , "aUsAEC2Z8GsntPRz");
        OrderPage orderPage =productCatalogue.goToOrderPage();
        org.testng.Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
    }


}
