package work;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

public class GlobalConfig {

    public WeworkConfig weworkConfig;

    public static GlobalConfig load(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            GlobalConfig config = mapper.readValue(GlobalConfig.class.getResource("/conf/globalConfig.yaml"), GlobalConfig.class);
            return config;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String loadString(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String json = "";
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(load());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        };
        return json;
    }

    public static String loadString(Object obj){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        String json = "";
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        };
        return json;
    }

}
