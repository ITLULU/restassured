package ContentType;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ContentTypeTest {
    static Logger logger = Logger.getLogger(ContentTypeTest.class);

    /**
     * 验证响应文件类型是 json
     */
    @Test
    public void testResponseContentType() {
        given().get("http://jsonplaceholder.typicode.com/photos/1").
                then().
                statusCode(200).
                contentType(ContentType.JSON);
    }

    /**
     * 验证响应文件类型是html
     */
    @Test
    public void testResponseContentType2() {
        Response response = given().
                get("https://www.baidu.com").
                then().
                statusCode(200).extract().response();
        logger.info("contentType:" + response.getContentType());
        logger.info("cookies:" + response.getCookies());
        Map<String, String> cookies =response.getCookies();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            logger.info(entry.getKey() + ":" + entry.getValue());
        }

        Headers headers = response.getHeaders();
        logger.info("headers:" + response.getHeaders());
        for (Header h : headers) {
            logger.info(h.getName() + ":" + h.getValue());
        }

    }

    /**
     * json schema约束 验证响应的contentType
     */
    @Test
    public void testJsonSchema() {
        given().
                get("xxxxxx").
                then().
                assertThat().body(matchesJsonSchemaInClasspath("json-schema.json"));
    }
}
