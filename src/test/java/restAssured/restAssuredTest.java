package restAssured;

import Base.TestBase;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.*;

public class restAssuredTest extends TestBase {
    static Logger logger=Logger.getLogger(restAssuredTest.class);
    @Test
    public void Testget(){
        ValidatableResponse validatableResponse= restassured.get("http://jsonplaceholder.typicode.com/posts/3");
       int  stautecode =validatableResponse.extract().response().getStatusCode();
       String res=validatableResponse.extract().response().getStatusLine();
       logger.info("res:"+res);
       logger.info("stautecode:"+stautecode);
        Assert.assertEquals(stautecode,200,"响应结果是"+res+"不是200");
        validatableResponse.body("id",equalTo(3)).and().body("userId",equalTo(1));

        /**
         * hasItem
         * hasItems 对应多个
         */
        validatableResponse.body("title",hasItem("molestias"));
        MatcherAssert.assertThat("myStringOfNote", containsStringIgnoringCase("Ring"));
    }
    @Test
    public void TestCreateParam() {
        response = requestSpecification.param("name","anthony@163.com")
                .param("job", "tester")
                .header("Content-Type", "text/html")
                .when().post("/users");
        TestUtils.checkStatusCode(response, 201);
        TestUtils.printResponseBody(response);
    }

    @Test
    public void TestCreate() {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("name","anthony12@126.com");
        map.put("job","dev");
        response = requestSpecification.formParams(map)
                .header("Content-Type", "text/html")
                .when().post("/users");
        TestUtils.checkStatusCode(response, 201);
        TestUtils.printResponseBody(response);

    }
    @Test
    public void TestListUsers() {
        response = requestSpecification.get("/users?page=2");
        jp = getJsonPath(response);
        TestUtils.checkStatusCode(response, 200);
        TestUtils.printAllResponseText(response);
    }
}
