package Serialization;

import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.UserRequest;
import pojo.UserResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestSerialization {
    static  Logger logger =Logger.getLogger(TestSerialization.class.getName());
    @Test
    public void testSerializationUsingHashMap() {
        Map<String, String> inputJson =  new HashMap<String, String>();
        inputJson.put("FirstName", "Anthony");
        inputJson.put("LastName", "Liu");
        inputJson.put("Age", "18");

        Response response=
                given()
                .contentType("application/json")
                .body(inputJson).log().all()
                .when()
                .post("http://www.thomas-bayer.com/restnames/countries.groovy")
                .then()
                .statusCode(200).extract().response();
        byte[] bytes=response.asByteArray();
        logger.info("response"+response.asString());
        for (byte by :bytes){
            logger.info("by:"+by);
        }
    }

    /**
     * 序列化
     */
    @Test
    public void testSerializationUsingBeanClass() {
        UserRequest u = new UserRequest();
        u.setAge(18);
        u.setWeight(75);
        u.setHome("China");

       Response response= given().contentType("application/json")
                .body(u, ObjectMapperType.JACKSON_2).when()
                .post("http://www.thomas-bayer.com/restnames/countries.groovy")
                .then().statusCode(200)
                .contentType("application/xml").log().all().extract().response();
        logger.info("response"+response.asString());
    }

    @Test
    public void testDeSerialization() {
        UserRequest u = new UserRequest();
        u.setAge(18);
        u.setWeight(75);
        u.setHome("China");

        UserResponse ur =
                given().body(u)
                        .when()
                        .post("http://www.thomas-bayer.com/restnames/countries.groovy")
                        .as(UserResponse.class);
        // 断言
        ur.setRegId(1101); // 随意设置一个响应数据
        Assert.assertEquals(ur.getRegId(),1101,"预计");
        Assert.assertTrue(ur.getRegId() > 0);
    }

}
