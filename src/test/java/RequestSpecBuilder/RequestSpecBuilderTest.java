package RequestSpecBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecBuilderTest {

    RequestSpecification requestSpc;
    @BeforeClass
    public void setup() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addParam("userId", "2");
        builder.addHeader("Accept-Encoding", "gzip, deflate");
        requestSpc = builder.build();
    }

    @Test
    public void test1() {
        given().spec(requestSpc).log().all()
                .when()
                .get("http://jsonplaceholder.typicode.com:80/posts")
                .then()
                .statusCode(200).log().body();
    }




}
