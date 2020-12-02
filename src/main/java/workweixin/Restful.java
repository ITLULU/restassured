package workweixin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

public class Restful {

    public String url;
    public String method;
    public HashMap<String, String> headers =new HashMap<>();
    public HashMap<String, String> query=new HashMap<>();
    public String body;
    static Logger logger=Logger.getLogger(Restful.class);

    public static  Restful restful;
    public static  Restful getInStance() {
        if(restful==null){
            restful=getApiFromYaml("/conf/weworkConfig.yaml");
        }
        return restful;
    }
    public  static Restful getApiFromYaml(String path) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Restful restful = mapper.readValue(Restful.class.getResourceAsStream(path), Restful.class);
            logger.info("restful:"+restful);
            return  restful;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  Restful updateApiFromMap(Restful restful, HashMap<String, Object> map) {
        if(map==null){
            return  restful;
        }
        if (restful.method.toLowerCase().contains("get")) {
            map.entrySet().forEach(entry -> {
                restful.query.replace(entry.getKey(), entry.getValue().toString());
            });
        }

        if (restful.method.toLowerCase().contains("post")) {
            if (map.containsKey("_body")) {
                restful.body = map.get("_body").toString();
            }
            if (map.containsKey("_file")) {
                String filePath = map.get("_file").toString();
                map.remove("_file");
                restful.body = template(filePath, map);
            }
        }
        return restful;

    }

    public static String template(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    @Override
    public String toString() {
        return "Restful{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", query=" + query +
                ", body='" + body + '\'' +
                '}';
    }

    public static void main(String[] args) {
        logger.info(getApiFromYaml("/api/list.yaml"));
    }
}
