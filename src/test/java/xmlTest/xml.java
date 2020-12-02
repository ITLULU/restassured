package xmlTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.xml.HasXPath.hasXPath;

public class xml {
    static Logger logger=Logger.getLogger(xml.class);
    @Test
    public void testSingleXMLContent() {
        given()
                .get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/")
                .then()
                .body("CUSTOMER.ID", equalTo("10"))
                .log().all();

    }
    @Test
    public void testCompleteTextinOneLine() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/").
                then().
                body("CUSTOMER.text()", equalTo("10SueFuller135 Upland Pl.Dallas")).
                log().all();
    }
    /**
     *根据Xpath寻找
     */
    @Test
    public void findValueByXpath() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10/").
                then().
                body(hasXPath("/CUSTOMER/FIRSTNAME"), containsString("Sue"));
    }

    /**
     * 拿到结点值
     */
    @Test
    public void findValueByXpath2() {
        given().
                get("http://www.thomas-bayer.com/sqlrest/INVOICE/").
                then().
                body(hasXPath("/INVOICEList/INVOICE[text()='20']")).
                log().all();
    }
    /**
     * 使用path方法提取内容,一行代码写法
     */
    @Test
    public void testExtractDetailsUsingPath2() {
        String href =given().get("http://jsonplaceholder.typicode.com/photos/1").path("url");
        logger.info("href:"+href);
        RestAssured.when().get(href).then().statusCode(200);

        //第二种写法
        String href1 = given().get("http://jsonplaceholder.typicode.com/photos/1").andReturn().jsonPath().getString("url");
        logger.info("href1:"+href1);
        RestAssured.when().get(href1).then().statusCode(200);
    }

    /**
     * 先拿到响应对象，然后再解析  主要使用的
     */
    @Test
    public void testFirstGetResponseThenDoActions() {
        Response resp = given().get("http://jsonplaceholder.typicode.com/photos/1").
                then().
                extract().
                response();
        // 解析响应
        logger.info("Context Type: " + resp.contentType());
        logger.info("Status Code: " + resp.statusCode());
        logger.info("Href: " + resp.path("url"));
    }
}
