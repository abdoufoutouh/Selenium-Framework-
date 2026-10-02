package org.example.TestComponents;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.example.pageobject.LandingPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
// this class represents all methods that are common among all test casses
// 1st example we saw is the init method => browser invoke
//  2  the get to the page method => called lunch application
public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver init() throws IOException {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
        // to use global properties we use the classe properties
        Properties p = new Properties();

        // used to get the file .properties the dynamic way because
        // it's not a good to give the path hardcoded => And it will not be local
        // it will run on every other system not only localy
        FileInputStream src = new FileInputStream(System.getProperty("user.dir")
                + "\\src\\main\\java\\org\\example\\ressources\\global.properties");

        p.load(src);
        // p.getProperty("browser) => means get the property from the global.properties
        // Driving from the System terminal
        // System.getProperty("browser") => get from the system level property li hiya
        // in our case from terminal

        //------------------ Handling global variable using terminal or .properties file ------------- //
//        String brwosername = "";
//
//        if(p.getProperty("browser")==null){
//            brwosername=System.getProperty("browser");
//        }else {
//            p.getProperty("browser");
//        }
        // her the else is ? its like used as separator but also the classic version is correct
        String browsername = System.getProperty("browser")!=null ? System.getProperty("browser"):
                p.getProperty("browser");

        // here we declare the class Properties
        // we get the file of properties
        // we use getProperty() => to extract the property want to use
        // here is the browser name
        // chrome + headless
        if (browsername.contains("chrome")) {
            // Chrome starts directly maximized => avoids the maximize() error
            // we use the ChromeOption to config the chrome  browser
            ChromeOptions options = new ChromeOptions();

            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");

            if(browsername.contains("headless")){
                options.addArguments("headless");
            }
            // we want to run our test execution without  the chrome opened
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));

        } else if (browsername.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
        } else if (browsername.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    // here we implicit tell Selenium to execute this method first
    // because this method is the one responsable for application launch
    @BeforeMethod(alwaysRun = true)
    public LandingPage lanchApplication() throws IOException {
        // invoke the browser
        driver = init();
        // ge to the page
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    public String getScreenshot(String testCasename, WebDriver driver) throws IOException{
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file  = new File(System.getProperty("user.dir") + "\\reports\\" +
                testCasename + ".png");
        FileUtils.copyFile(source,file);
        return testCasename+".png";

    }

    // this method runs after each @Test method
    @AfterMethod(alwaysRun = true)
    public void close() {
        // quit() ends the whole session => avoids the "Connection reset" warning
        driver.quit();
    }

    // converting  fil to hashmap object
    // Reading data from a Json file store inro a hashmap
    public List<HashMap<String,String>> getJasonToMap(File filePath) throws IOException {
        // first step  : Declare and Read the JSON File
        String jsonContent =    FileUtils.readFileToString(filePath);
        // second step : convert the jsonfile => Type String to a hashmap
        // an object mapper is the one responsible to convert the json file to a hashmap
        //  here I need to understand more what is exactly a Object Mapper
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String,String>> data = objectMapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;

    }
}