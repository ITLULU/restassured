package TestNg;

import org.testng.annotations.Test;

public class TestGroup {
    @Test(description="测试分组" ,groups= {"operation"})
    public void TestGroupAdd() {
        System.out.print(String.valueOf(11+12));
    }
    @Test(description="测试分组" ,groups= {"operation","Animal"})
    public void TestGroupAnimal() {
        System.out.printf("animal 动物");
    }
    @Test(description="测试分组" ,groups= {"Animal"})
    public void TestGroupCat() {
        System.out.printf("cat 喵喵");
    }
    @Test(description="测试分组" ,groups= {"laguage"})
    public void TestGroupLaguage() {
        System.out.printf("chinese 汉语");
    }

}
