package work;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Wework {
    private static String token=null;
    public static  String getWeworkToken() {
        Response response= RestAssured.given()
                .queryParam("corpid",WeworkConfig.getInStance().corpid)
                .queryParam("corpsecret",WeworkConfig.getInStance().secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
        return response.then().extract().path("access_token");
    }
    public static  String getWeworkToken(String secret) {
        Response response= RestAssured.given()
                .queryParam("corpid",WeworkConfig.getInStance().corpid)
                .queryParam("corpsecret",secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
        return response.then().extract().path("access_token");
    }
    public static String getToken() {
        //todo:支持两种类型的token
        if(token==null) {
            token=getWeworkToken();
        }
        return token;
    }


}
