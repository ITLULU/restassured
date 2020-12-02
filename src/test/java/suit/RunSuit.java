package suit;

import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class RunSuit {
    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<String>();
        suites.add("testng-2.xml");
        testNG.setTestSuites(suites);
        testNG.run();

        // 等待执行结束，然后去执行失败用例
        TestNG testNG1 = new TestNG();
        List<String> suites1 = new ArrayList<String>();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        suites1.add("test-output/testng-failed.xml");
        testNG1.setTestSuites(suites1);
        testNG1.run();


    }
}
