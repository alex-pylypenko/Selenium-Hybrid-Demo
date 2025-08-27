package codenbox.utilities;


import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import java.util.concurrent.ConcurrentHashMap;

public class Retry implements IRetryAnalyzer {

    private static final int retryLimit = 2;
    private static ConcurrentHashMap<String, Integer> retryCounts = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        int currentRetry = retryCounts.getOrDefault(testName, 0);

        if (currentRetry < retryLimit) {
            retryCounts.put(testName, currentRetry + 1);
            System.out.println("Retrying " + testName + " | Attempt " + (currentRetry + 1));
            return true;
        }
        return false;
    }
}


//import org.testng.IRetryAnalyzer;
//import org.testng.ITestResult;
//
//public class Retry implements IRetryAnalyzer{
//
//	int count = 0;
//	int retryLimit = 1;
//	
//	@Override
//	public boolean retry(ITestResult result) {
//		
//		if (count < retryLimit) {
//			count++;
//			return true;
//		}
//		return false;
//	}
//
//}
