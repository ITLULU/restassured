package ResponseSpec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class ResponseSpecTest {
    ResponseSpecification responseSpc;
    @BeforeClass
    public void setup(){

        ResponseSpecBuilder resbuilder = new ResponseSpecBuilder();
        resbuilder.expectStatusCode(200);
        resbuilder.expectHeader("Content-Type", "application/json; charset=utf-8");
        resbuilder.expectHeader("Cache-Control", "public, max-age=14400");
        responseSpc = resbuilder.build();
    }
    @Test
    public void testRes( ) {
        when()
                .get("http://jsonplaceholder.typicode.com/posts?userId=2")
                .then()
                .spec(responseSpc)
                .time(Matchers.lessThan(30000L));
    }
}
