package workweixin;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import utils.JsonUtil;
import work.WeworkConfig;

import java.io.*;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class Api extends  Restful {
    static Logger logger = Logger.getLogger(Api.class);
    HashMap<String, Object> query = new HashMap<String, Object>();

    public Api(){
        useRelaxedHTTPSValidation();
    }



    public RequestSpecification getDefaultRequestSpecification()
    {
        return given().log().all();
    }
    public  static  String template(String path,HashMap<String,Object>map){
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }


    public static String templateFromMustache(String path, HashMap<String, Object> map) {

        Writer writer = new OutputStreamWriter(System.out);
        MustacheFactory mf = new DefaultMustacheFactory();
       // Mustache mustache = mf.compile(new StringReader("name: ddddddddddd {{name}} "), "example");
        Mustache mustache = new DefaultMustacheFactory().compile("/data/create.mustache");
        mustache.execute(writer, map);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("mustache:"+mustache.toString());
        return mustache.toString();
    }


    public Response getResponseFromHar(String path, String pattern, HashMap<String, Object> map) {
        Restful restful=getApiFromHar(path, pattern);
        restful=updateApiFromMap(restful, map);
        return getResponseFromRestful(restful);
    }

    public Response templateFromSwagger(String path, String pattern, HashMap<String, Object> map) {
        //todo: 支持从swagger自动生成接口定义并发送
        //todo: 分析swagger codegen
        //从har中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });

        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return getDefaultRequestSpecification().when().request(method, url);
    }


    public Restful getApiFromHar(String path, String pattern) {
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(URLDecoder.decode
                    (this.getClass().getResource(path).getPath(), "utf-8")));
            logger.info("har:"+har);
            HarRequest request = new HarRequest();
            Boolean match=false;
            for (HarEntry entry : har.getLog().getEntries()) {
                request = entry.getRequest();
                logger.info("request:"+request);
                if (request.getUrl().matches(pattern)) {
                    match=true;
                    break;
                }
            }

            if(match==false){
                request=null;
                throw new Exception("出错，没有查找到");
            }


            Restful restful = new Restful();
            restful.method = request.getMethod().name().toLowerCase();
            logger.info("method:"+restful.method);
            //todo: 去掉url中的query部分
            restful.url = request.getUrl();
            logger.info("url:"+restful.url);
            logger.info("query:"+ JsonUtil.listTransforTOJSON(Collections.singletonList(request.getQueryString())));
            request.getQueryString().forEach(q -> {
                logger.info("name:"+q.getName()+"  value:"+q.getValue());
                restful.query.put(q.getName(), q.getValue());
            });
            restful.body = request.getPostData().getText();
            return restful;


        } catch (HarReaderException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }




    public Response getResponseFromRestful(Restful restful) {

        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }

        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }
        String[] url=updateUrl(restful.url);
        return requestSpecification.log().all()
                .when().request(restful.method, restful.url)
                .then().log().all()
                .extract().response();


    }


    public Response getResponseFromYaml(String path, HashMap<String, Object> map) {
        //fixed: 根据yaml生成接口定义并发送
        Restful restful = getApiFromYaml(path);
        restful = updateApiFromMap(restful, map);

        RequestSpecification requestSpecification = getDefaultRequestSpecification();

        //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
        if (restful.query != null) {
            restful.query.entrySet().forEach(entry -> {
                requestSpecification.queryParam(entry.getKey(), entry.getValue());
            });
        }

        if (restful.body != null) {
            requestSpecification.body(restful.body);
        }

        String[] url=updateUrl(restful.url);

        return requestSpecification.log().all()
                .header("Host", url[0])
                .when().request(restful.method, url[1])
                .then().log().all()
                .extract().response();

    }


    private String[] updateUrl(String url) {
        //fixed: 多环境支持，替换url，更新host的header
        HashMap<String, String> hosts=WeworkConfig.getInStance().env.get(WeworkConfig.getInStance().current);
        logger.info("当前环境hosts:"+hosts);
        String host="";
        String urlNew="";
        for(Map.Entry<String, String> entry : hosts.entrySet()){
            if(url.contains(entry.getKey())){
                host=entry.getKey();
                urlNew=url.replace(entry.getKey(), entry.getValue());
            }
        }
        return new String[]{host, urlNew};
    }
    //todo: 支持wsdl soap

    public Response readApiFromYaml(String path, HashMap<String, Object> map) {
        //todo: 动态调用
        return null;
    }

}
