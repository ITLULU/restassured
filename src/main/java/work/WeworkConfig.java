package work;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;
import workweixin.Restful;

import java.io.IOException;
import java.util.HashMap;

public class WeworkConfig {
    //企业Id:wwb0d6330624cea8bd
    //审批钥匙 gL79RzXuQILJfFtxI9ouN6DJNifQsW3GMOk7dKo8J2s
    //审批ID 3010040
    //
    //    public  String agentId="3010040";
    //    public  String secret="gL79RzXuQILJfFtxI9ouN6DJNifQsW3GMOk7dKo8J2s";
    //    public  String corpid = "wwb0d6330624cea8bd";
    //    public  String contactSecret="C7uGOrNyxWWzwBsUyWEbLQdOqoWPz4hNvxj9RIFv-4U";
    public String agentId="1000005";
    public String secret="1JPyY9GvPLZfpvxEDjok-Xt_9v7HIBYJhZUoO6EgNGY";
    public String corpid = "wwd6da61649bd66fea";
    public String contactSecret="C7uGOrNyxWWzwBsUyWEbLQdOqoWPz4hNvxj9RIFv-4U";

    public String current="test";
    public HashMap<String, HashMap<String, String>> env;

    static Logger logger=Logger.getLogger(Restful.class);
    private static WeworkConfig weworkConfig;
    public static WeworkConfig getInStance() {
        if(weworkConfig==null){
            weworkConfig=load("/conf/weworkConfig.yaml");
        }
        return weworkConfig;
    }

    public static WeworkConfig load(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path), WeworkConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
