package TestNg;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDepend {

    Logger logger = Logger.getLogger(TestDepend.class);
    @Test(description="登录测试" )
    public void Testlogin() {
        logger.info("测试登录失败");
        Assert.fail();
    }
    @Test(description="依赖登录" ,dependsOnMethods= {"TestNg.TestTieOut.testenable"})
    public void TestPayOrder() {
        System.out.println("----支付购买----");
        logger.info("登陆成功---可以支付购买物品");
    }
}
