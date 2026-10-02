package org.example.Listners;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.opentelemetry.sdk.internal.ExtendedOpenTelemetrySdk;
import lombok.SneakyThrows;
import org.example.Data.ExtentReportNG;
import org.example.TestComponents.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;
// Provide methods help that control or tests
// for example for every start test we want to invoke a extent report
// and give examples after that ....... Of methods provided by the ITest Listener interface methods
public class Listners extends BaseTest implements ITestListener {

    // Thread locator is used to solve the problem of the parallel tests
    // the idea is to convert the test cases to a Hash map of test cases
    // where each test case have his id unique like that a la place de parcourir
    // tout Les test case en parraléle Et ca va fusionner on va parcourrir test
    // par test , utilisatiion de listener de part unique et pas fusioner une
    // a la fois comme ca on aura pas de confusion de detection erreur et rapport
    // d'erreur avec extent reports qui est pas précise est correcte

    ThreadLocal<ExtentTest> extents = new ThreadLocal();

    // rapport d'un cas de test
    ExtentTest test;
    ExtentReports extent = ExtentReportNG.getReportObject();
    @Override
    public void onTestStart(ITestResult result ) {

        // the variable results hold all the information about the test case
        // because every method here hold and pass the test case name as a parameter
        // It happens dynamic every test will use this and will be invoked in the report created

        // créer un test entré au rapport
        test = extent.createTest(result.getMethod().getMethodName());

        extents.set(test);// Thread local crate a Hashmap  of test cases with every ID unique and value
        // fo exemple here for the erreur  validation test he will create id-> value equals to Erreur validation
        // get() & set() methods

          }
          // the test variable hna like you say it's the report of a specific test it's
          // represented by the class ExtentTest
    @Override
    public void onTestSuccess(ITestResult result ) {

    }

    // on each failure test we used : .addScreenCaptureFromPath(path ,"Error handling " );
    // we call the screenshot method and we add it to the extentreport
    @Override
    public void onTestFailure(ITestResult result) {
//        test.log(Status.FAIL,"TestFailed");

        extents.get().fail(result.getThrowable()); // here we want to get the fail result not just to know that
        // the test failed so we use the method getThrowable()
        // After Failing the test and getting the result : here the result after using getThrowable()
        // will surely be the error log message so after doing that we need to take a screenshot
        // then I suppose we need to attach the screenshot into the report of the test

        // making life into the driver
        try {
            driver= (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        //------------ Screenshot the Failing message ---------------------//
        String path = null;
        try {
            path = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //-------------- Attach the screenshot to the report---------//
        // here u need to send the path of the screenshot and the name we want
        // to be displayed on the report fot the screenshot
        extents.get().addScreenCaptureFromPath(path ,"Error handling " );

        //---------- It's important to know this is a one time thing --------------//
        // il suffit de apprendre plus que comprendre puisque avec la pratique ca devient
        // des étapes et réflexe par défaut pour conceptualisé un framework
        // permet de assimiler  nos test avec maintenabilité
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

    }
}
