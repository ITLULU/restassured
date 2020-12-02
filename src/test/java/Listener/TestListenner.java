package Listener;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListenner implements ITestListener {
    Logger logger = Logger.getLogger(TestListenner.class);
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("用例启动。" + result.toString());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("用例执行成功，" + result.toString());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("用例运行失败，启动截图。");
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

    }
}
