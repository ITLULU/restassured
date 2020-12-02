package workweixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.FileUtil;
import utils.JsonUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;


public class DepartmentTest {
    Department department=null;
    static Logger logger=Logger.getLogger(DepartmentTest.class);
    String random=String.valueOf(System.currentTimeMillis());
    @BeforeClass
    public void setup(){
        if (department==null){
            department=new Department();
        }
    }
    @Test
    public void TestFilePAth(){
        BufferedInputStream fis= (BufferedInputStream) this.getClass().getResourceAsStream("/dir/result.txt");
        byte [] bytes=new byte[1024];
        try {
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(new String(bytes));
    }
    @Test
    public void TestAll(){
        Response response=department.All();
        String res=response.getBody().asString();
        JSONObject jsonObject= JsonUtil.StringTransForjSON(res);
        String json = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
        FileUtil.writeIntoText(json,"D:/IdeaProject/interfaceRestAssured/src/main/resources/dir/result.txt");
        logger.info("响应结果："+json);
    }
    @Test
    public void TestList(){
        Response response=department.list("1");
        logger.info("result:"+JSON.toJSONString(JsonUtil.StringTransForjSON(response.asString()), SerializerFeature.PrettyFormat));
        response.then().statusCode(200).body("department", hasItem("testhome1234"));
        response.then().statusCode(200).body("department[0].id", equalTo("testhome1234"));
        response.then().statusCode(200).body("department.id", equalTo("33"));
    }
    @Test
    void list(){
        department.list("33").then().statusCode(200).log().all();
        department.list("39").then().statusCode(200).body("errcode", equalTo(0));
        department.list("39").then().statusCode(200).body("department[0].id", equalTo(39));
        department.list("39").then().statusCode(200).body("department.find  { it.id == 39 }.id", equalTo(39));
    }
    @Test
    public void TestCreat(){
        Response response=department.create("department"+random, "1");
        logger.info("result:"+JSON.toJSONString(JsonUtil.StringTransForjSON(response.asString()), SerializerFeature.PrettyFormat));
        response.then().body("errcode", equalTo(0));
    }

    @Test
    void createByMap(){
        HashMap<String, Object> map=new HashMap<String, Object>(){{
            put("name", String.format("seveniruby_d1_map%s", random));
            put("parentid", "1");
        }
        };
        department.create(map).then().body("errcode", equalTo(0));
    }
    @Test
    void createWithChinese() {
        department.create("思寒department"+random, "1").then().body("errcode", equalTo(0));
    }
/*
    @ParameterizedTest
    @CsvFileSource(resources = "/data/createWithDup.csv")
    void createWithDup(String name, Integer expectCode){
        department.create(name+random, "1").then().body("errcode", equalTo(0));
        department.create(name+random, "1").then().body("errcode", equalTo(expectCode));

    }*/
    @Test(dataProvider = "CSVData")
    void createWithCSV(String name, Integer expectCode){
        department.create(name+random, "1").then().body("errcode", equalTo(0));
        department.create(name+random, "1").then().body("errcode", equalTo(expectCode));
    }

    @DataProvider(name = "CSVData")
    Object[][] CSVData(){
        return new Object[][]{
                { "dup1_",60008},
                {"dup2_",60007} ,
                {"dup3_",60008},
        };
    }

    @Test
    void delete() {
        String nameOld="seveniruby_d1"+random;
        department.create(nameOld, "1");
        Integer idInt=department.list("").path("department.find{ it.name=='"+ nameOld +"' }.id");
        System.out.println(idInt);
        String id=String.valueOf(idInt);
        department.delete(id).then().body("errcode", equalTo(0));
    }


    @Test
    void update() {
        String nameOld="seveniruby_d1"+random;
        department.create(nameOld, "1");
        Integer idInt=department.list("").path("department.find{ it.name=='"+ nameOld +"' }.id");
        System.out.println(idInt);
        String id=String.valueOf(idInt);
        department.update("seveniruby_d2"+random,  id).then().body("errcode", equalTo(0));
    }

    @Test
    void updateAll(){
        //todo:
        HashMap<String, Object> map=new HashMap<>();
        department.readApiFromYaml("readApiFromYaml.json", map).then().statusCode(200);
    }

    @Test
    void deleteAll(){
        department.deleteAll();
        MatcherAssert.assertThat(1.0, lessThan(2.0));
    }


}
