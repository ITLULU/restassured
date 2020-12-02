package work;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class WeWorkTest {
     public static  Logger logger= Logger.getLogger(WeWorkTest.class);

    /**
     * 企业微信接口 获取token
     */
   @Test(groups = "BaseGroup",description = "测试获取token")
    public void testToken(){
       //企业Id:wwb0d6330624cea8bd
       //审批钥匙 gL79RzXuQILJfFtxI9ouN6DJNifQsW3GMOk7dKo8J2s
       Response response= RestAssured.given()
                .queryParam("corpid","wwd6da61649bd66fea")
                .queryParam("corpsecret","1JPyY9GvPLZfpvxEDjok-Xt_9v7HIBYJhZUoO6EgNGY")
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
         logger.info("token:"+response.then().extract().path("access_token"));
          response.then().log().all().statusCode(200).body("errcode", Matchers.equalTo(0));
    }
    @Test(groups = "BaseGroup",description = "输出token")
    public void TestGetToken(){
        Reporter.log("测试token:"+Wework.getToken());
        logger.info("acces-Token:"+ Wework.getToken());
    }

    @Test
    public void TestgetToken(){
        logger.info(Wework.getToken());
    }
}
