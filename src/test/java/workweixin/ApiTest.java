package workweixin;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import devtols.AppTest;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiTest {
    Api api;
    @BeforeAll
    public void setup(){
         api=new Api();
    }
    @Test
    public void templateFromYaml() {

        api.getResponseFromYaml("/api/list.yaml", null).then().statusCode(200);
    }

    @Test
    public void request(){
        RequestSpecification req=given().log().all();
        req.queryParam("id", 1);
        req.queryParam("d", "pdd");
        req.get("http://www.baidu.com");

    }

    @Test
    public void resource(){
        URL url= AppTest.class.getResource("/api/app.har.json");
        System.out.println(url.getFile());
        System.out.println(url.getPath());
    }

    @Test
    public void getApiFromHar() {
        Api api=new Api();
        System.out.println(api.getApiFromHar("/api/app.har.json", ".*tid=67.*").url);
        System.out.println(api.getApiFromHar("/api/app.har.json", ".*tid=41.*").url);
//        System.out.println(api.getApiFromHar("/api/app.har.json", ".*tid=21.*").url);
    }

    @Test
    public void matches(){
        String s="https://work.weixin.qq.com/api/devtools/devhandler.php?tid=67&access_token=gs4n_tfZfSWNnLxtJx_Qsww8tpRN_7fsglgvhencsjNO1uR4mvylY2vfy42sX_Oub1i1rjstiWi3D-bk4qybWhpwPHR9yQ9D-T-huOvRCO0RzLrcetj5foV1wgoXhb6fKm5f8oZa-SH4hbgenoL-FYfEuxvxOaKusrWpNAwl4NSBD_4_l4eDPFysBGTj1HDrvqt57Nij_P-jzT1jFV9v_Q&f=json";
        System.out.println(s.matches(".*tid=67.*"));

    }

    @Test
    public void getResponseFromHar() {
        Api api=new Api();
        api.getResponseFromHar("/api/app.har.json", ".*tid=67.*", null);
    }

    @Test
    public void mustache() throws IOException {
        HashMap<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("name", "Mustache");
        scopes.put("id", "1");
        Api api=new Api();
//        api.templateFromMustache("/data/create.mustache",scopes);
        Writer writer = new OutputStreamWriter(System.out);
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader("name:{{name}}"), "example");
     //   Mustache mustache = mf.compile("/data/create.mustache");
        mustache.execute(writer, scopes);
        writer.flush();

    }

}
