package org.example.Listners;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class Retry implements IRetryAnalyzer {
    //  Used to catch the flicky unstable tests
    // I need to understand also here
    // this class used to retry the test case when it fails just to make sure
    // it will ask the question do I need to run the test again maybe something
    // unexpected happened I de not know
    int count = 0 ;
    int maxTry = 2 ;
    // -------- I dont get  --------------->
    // returns false always
    // so we need to add a condition that is always true
    @Override
    public boolean retry(ITestResult result) {
        if(count<maxTry)
        {
            count++;
            return true;
        }
        return false;
    }
}
