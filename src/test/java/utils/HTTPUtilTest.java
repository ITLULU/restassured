package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.restassured.RestAssured;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HTTPUtilTest {
    static Logger logger = Logger.getLogger(HTTPUtilTest.class);
    @Test
    public void TestgetDefault(){

        //String url="http://gwgp-n6uzuwmjrou.n.bdcloudapi.com/weather2/query?cityid=111&date=2020-10-1";
     //   String utl1="http://gwgp-n6uzuwmjrou.n.bdcloudapi.com/weather2/city";
    }

    @Test
    public void Testget(){
        //https://api.apiopen.top/likePoetry?name=李

        try {
            String url="https://api.apiopen.top/likePoetry";
            List<BasicNameValuePair> pairs= new ArrayList<>();
            BasicNameValuePair nameValuePair =new BasicNameValuePair("name","李");
            pairs.add(nameValuePair);
            String res=EntityUtils.toString(HTTPUtil.getWithParams(url,pairs).getEntity(),"utf-8");
            logger.info( JsonUtil.getJSONString((JSONObject) JSON.parse(res)));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public  void TestPost(){
        HashMap<String,String> headermap = new HashMap<String,String>();
        headermap.put("Content-Type", "application/json"); //这个在postman中可以查询到
        String url="https://reqres.in/api/users";
        HashMap<String,Object>params=new HashMap<String,Object>();
        params.put("name","computer");
        params.put("job","IT");
        String jsonString =JsonUtil.MapToJsonString(params);
        CloseableHttpResponse  response = HTTPUtil.post(url, headermap,jsonString);
        logger.info("res:"+response.getStatusLine());
        try {
            logger.info( JsonUtil.getJSONString((JSONObject) JSON.parse(EntityUtils.toString(response.getEntity(),"UTF-8"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public  void TestPostMap(){
        HashMap<String,String> headermap = new HashMap<String,String>();
        headermap.put("Content-Type ", "application/json"); //这个在postman中可以查询到
        String url="https://reqres.in/api/users";
        CloseableHttpResponse  response = HTTPUtil.put(url,FileUtil.readFile("src/main/resources/json/post.json"),headermap);
        logger.info("respose:"+response.getEntity().toString());
        try {
            logger.info( JsonUtil.getJSONString((JSONObject) JSON.parse(EntityUtils.toString(response.getEntity(),"UTF-8"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public  void TestPut(){

        String url="https://reqres.in/api/users/1";

        HashMap<String,String> headermap = new HashMap<String,String>();
        headermap.put("Content-Type", "application/json"); //这个在postman中可以查询到

        HashMap<String,Object>params=new HashMap<String,Object>();
        params.put("name","computer");
        params.put("job","IT");
        String jsonString =JsonUtil.MapToJsonString(params);
        logger.info("jsonString:"+jsonString);
        CloseableHttpResponse  response = HTTPUtil.put(url, jsonString, headermap);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,200,"response status code is not 200");

        try {
            logger.info( JsonUtil.getJSONString((JSONObject) JSON.parse(EntityUtils.toString(response.getEntity(),"UTF-8"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteApiTest(){
        String url="https://reqres.in/api/users/2";
        CloseableHttpResponse  response  = HTTPUtil.delete(url);

        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("statusCode:"+statusCode);
        Assert.assertEquals(statusCode, 204,"status code is not 204");

    }
    @Test
    public void TestPostwithJson(){

        HashMap<String,String>heads=new HashMap<>();
        heads.put("Connection" ,"keep-alive");
        heads.put("Content-Type" ,"application/json");

        CloseableHttpResponse response = HTTPUtil.post("http://10.3.152.3:32683/api/v1/auth/login",heads,
                JsonUtil.getJSONString(FileUtil.getFileName("src/main/resources/json/login.json")));
        logger.info(JsonUtil.getJSONString(response.getEntity()));
    }
    @Test
    public void testrest(){
         RestAssured.given().header("Content-Type" ,"application/json")
                .when().body(FileUtil.getFileName("src/main/resources/json/login.json"))
                .post("http://10.3.152.3:32683/api/v1/auth/login")
                .then().log().all().statusCode(200).extract();

    }

}
