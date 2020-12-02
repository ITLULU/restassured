package work;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class getTokenTest {
    @Test
    public void testToken(){
        Wework wework=new Wework();
        String token=wework.getWeworkToken();
        assertThat(token, not(equalTo(null)));
    }
    @Test
    public void createDepartment(){
        given().log().all().queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "   \"name\": \"广州研发中\",\n" +
                        "   \"parentid\": 1,\n" +
                        "   \"order\": 1,\n" +
                        "}")
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().statusCode(200).body("errcode", equalTo(60011));
    }
}
