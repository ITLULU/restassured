package workweixin;

import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class Department extends Contact {
    Logger logger=Logger.getLogger(Department.class);
    public Response All(){
        logger.info("测试所有");
       /* Response response=  given()
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().statusCode(200)
                .extract().response();
        return response;*/
        return list("");
    }

    public Response list(String id){
        logger.info("测试list");
      /*
       Response response= given().param("access_token", Wework.getToken())
                .param("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().statusCode(200)
                .extract().response();
       */

        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("id", id);
        return getResponseFromYaml("/api/list.yaml", map);

    }
    public Response create(String name,String parentid) {
        //post请求使用 queryParam
       /* String body =JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"))
                .set("$.name", name)
                .set("parentid", parentid).jsonString();
        Response response = given().queryParam("access_token", Wework.getToken())
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().statusCode(200)
                .extract().response();
        return  response;*/
        HashMap<String, Object> map=new HashMap<>();
        map.put("_file", "/data/create.json");
        map.put("name", name);
        map.put("parentid", parentid);

        return getResponseFromYaml("/api/create.yaml", map);

    }

    public Response create(HashMap<String, Object> map){
        map.put("_file", "/data/create.json");
        return getResponseFromYaml("/api/create.yaml", map);
    }

    public Response delete(String id){
       /* return given().queryParam("access_token", Wework.getToken())
                .queryParam("id", id)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/deletr")
                .then().log().all().statusCode(200).extract().response();*/
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("id", id);
        return getResponseFromYaml("/api/delete.yaml", map);
    }

    public Response update(String name, String parentid){
        /*String body =JsonPath.parse(this.getClass().getResourceAsStream("data/create.json"))
                .set("$.name", name)
                .set("parentid", parentid).jsonString();
        return  given()
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();*/
        HashMap<String, Object> map=new HashMap<>();
        map.put("_file", "/data/update.json");
        map.put("name", name);
        map.put("id", parentid);

        return getResponseFromYaml("/api/update.yaml", map);
    }

    public Response update(HashMap<String, Object> map){
        //todo:
        return getResponseFromHar(
                "demo.har.json",
                "https://work.weixin.qq.com/wework_admin/party?action=addparty" ,
                map
        );
    }

    public Response deleteAll(){
        List<Integer> idList=list("").then().log().all().extract().path("department.id");
        idList.forEach(id->delete(id.toString()));
        return null;
    }

    public Response updateAll(HashMap<String, Object> map){
        //todo:
        return readApiFromYaml("readApiFromYaml.json", map);
    }

}
