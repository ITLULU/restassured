package restAssured;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class restassured {
    /**
     * param post和get都可以使用  queryparam只能get formparam只能在post 使用
     * @param url
     * @param params
     * @return
     */
    public static ValidatableResponse get(String url, HashMap<String,String>params){
        RequestSpecification requestSpecification = null;
        for(Map.Entry<String,String> entry :params.entrySet()){
            requestSpecification=given().queryParam(entry.getKey(),entry.getValue());
        }
       return requestSpecification.when().get(url).then().statusCode(200).log().all();

    }

    /**
     * 多个参数使用params
     * @param url
     * @param params
     * @return
     */
    public static ValidatableResponse getParams(String url, HashMap<String,String>params){

        return  given().params(params)
                .when()
                .get(url)
                .then().statusCode(200).log().all();

    }

    /**
     * 带请求头
     * @param url
     * @param heads
     * @return
     */
    public static ValidatableResponse getHeards(String url, HashMap<String,String>heads){

        return  given().headers(heads)
                .when().log().all()
                .get(url)
                .then().statusCode(200).log().all();

    }

    /**
     * 带请求参数和请求头
     * @param url
     * @param heads
     * @param params
     * @return
     */
    public static ValidatableResponse getHeardsParams(String url, HashMap<String,String>heads,HashMap<String,String>params){

        return  given().headers(heads)
                .params(params)
                .when().log().all()
                .get(url)
                .then().statusCode(200).log().all();

    }

    public static ValidatableResponse get(String url){

        return given().get(url).then().statusCode(200).log().all();

    }

}
