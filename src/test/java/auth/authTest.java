package auth;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

/**
 * 授权
 */
public class authTest {
    @Test
    public void testBasicChallengeAuthentication() {
        given().auth().basic("tom", "123")
                .when()
                .get("https://www.xxx.com")
                .then()
                .statusCode(200);
    }
    @Test
    public void testBasicAuthentication() {
        RestAssured.authentication = basic("tom", "123");
        given()
                .get("https://www.xxx.com")
                .then()
                .statusCode(200);
    }

    /**
     *不等服务器询问，把秘钥发过去再说，类似先发制人的效果。
     */
    @Test
    public void testBasicPreemptiveAuthentication() {
        given().auth().preemptive().basic("tom", "123")
                .when()
                .get("https://www.xxx.com")
                .then()
                .statusCode(200);
    }
    /**
     * Digest认证方式是HTTP协议的一种算法，这种方式也是需要等待服务器盘问之后，才发送秘钥
     */
    @Test
    public void testDigestAuthentication() {
        given().auth().digest("tom", "123")
                .when()
                .get("https://www.xxx.com")
                .then()
                .statusCode(200);
    }
}
