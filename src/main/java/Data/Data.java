package Data;

import org.testng.annotations.DataProvider;

public class Data {
    @DataProvider(name = "getUsers")
    public Object[][] getUsers(){
        return new Object[][]{
                {"","","用户名或密码不能为空"},
                {"login","","用户名或密码不能为空"},
                {"","123456","用户名或密码不能为空"},
                {"admin","admin","欢迎管理员登录"},
                {"","","用户名或密码不能为空"},
                {"login","123456","欢迎用户:login登录"}
        };
    }
}
