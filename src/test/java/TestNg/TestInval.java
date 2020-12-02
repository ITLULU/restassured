package TestNg;

import org.testng.annotations.Test;

public class TestInval {
    @Test(invocationCount = 5, invocationTimeOut = 5100)
    public void loginTest() throws InterruptedException{

        Thread.sleep(1000);
        System.out.println("login test");

    }

}
