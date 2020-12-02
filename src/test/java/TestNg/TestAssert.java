package TestNg;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestAssert {
    @Test
    public void TestAssert() {
        Assert.assertEquals("2", 2);
    }
    @Test
    public void TestAssertNotNull() {
        int a=3;
        Assert.assertNotNull(a);
    }
    @Test
    public void TestAssertSame() {
        String expected="aaa";
        String actual="aaa";
        Assert.assertSame(actual, expected,"实际结果跟预期结果不一样");
    }
    @Test
    public void TestAssertTrue() {
        Assert.assertTrue(1==2);
    }
    @Test
    public void TestAssertAll(){
        System.out.println("Test start");
        SoftAssert assertion = new SoftAssert();
        assertion.assertEquals(12, 13,"两者不相等");
        System.out.println("Test complete");
        System.out.println(3+8);
        assertion.assertAll();
    }

}
