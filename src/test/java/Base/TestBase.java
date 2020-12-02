package Base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.propertiesUtil;

public class TestBase {

    public static RequestSpecification httpRequest;
    public static Logger logger;

    public static String serverHost;
    public static String port;
    //Global Setup Variables
    public static Response response ; //Response
    public static JsonPath jp = null; //JsonPath

    //初始化请求对象
    public RequestSpecification requestSpecification;
   static {
       serverHost = propertiesUtil.getKeyByBundle("config","Host");
       port = propertiesUtil.getKeyByBundle("config","Port");
       //全局指定parse类型
       RestAssured.defaultParser= Parser.JSON;
   }

    @BeforeClass
    public void setup() {
        String className = this.getClass().getName();
        logger = Logger.getLogger(className);
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.setLevel(Level.DEBUG);
        setBaseURI(); //设置Base URI
        //设置Base Path，我这里是api（https://reqres.in/接口地址都是api开头，
        //所以这里basepath设置api这个字符串），看看具体你自己项目请求地址结构
        setBasePath("api");
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }

    @AfterClass
    public void afterTest (){
        //测试之后恢复一些值的设定
        resetBaseURI();
        resetBasePath();
    }

    //设置 base URI
    public static void setBaseURI (){
        if("80".equals(port)) {
            RestAssured.baseURI = serverHost;
        }else {
            RestAssured.baseURI = serverHost+":"+port;
        }
        //System.out.println(RestAssured.baseURI);
    }

    //设置base path
    public static void setBasePath(String basePath){
        RestAssured.basePath = basePath;
    }

    //执行完测试后重置 Base URI
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }

    //执行完测试后重置 base path
    public static void resetBasePath(){
        RestAssured.basePath = null;
    }

    //返回 JsonPath对象
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        logger.info("json:"+json);
        return new JsonPath(json);
    }

}