package org.example.Data;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class ExtentReportNG {
    // method is to get the report object to use in Listeners important to manage run of the report
    // here we have the report creation + and configuration
    // the method return the reports object
    //----------------------- The creation of the extent report page -----------------------------//
    public static ExtentReports getReportObject(){
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        // the extendSparkReporter here create a report means  HTML File a partir of the file
        // permet de créer le fichier HTML
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        // to config  the reporting file
        reporter.config().setReportName("Web automation Results ");
        reporter.config().setDocumentTitle("Test Results");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setSystemInfo("Tester","Abdou");
        // retourner le extent test : testcase + son report spécifique
        return extent;
    }
}
