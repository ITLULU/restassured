package TestNg;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestTieOut {
    @Test(timeOut = 3000,enabled = false)
    public void loginTest(){
        try{
            Thread.sleep(3100);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }
    @Test
    public void testenable(){
        System.out.println("测试enable");
    }

    @Test(description="优先级" ,priority= 10)
    public void TestpriorityAdd() {
        Reporter.log(String.valueOf(11+12));
    }
    @Test(description="优先级" ,priority= 2)
    public void Testprioritysub() {
        Reporter.log(String.valueOf(21-12));
    }
}
