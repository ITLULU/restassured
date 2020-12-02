package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class JsonUtil {
    //静态
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static Logger logger=Logger.getLogger(JsonUtil.class);
    /**
     * 将map转json字符串
     * @Author 鹿少年 2020年9月6日 下午9:37:52
     * @param map
     * @return
     */
    public static String MapToJsonString(Map<String, Object> map) {
        String jsonStr = new String();
        try {
            jsonStr = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }

    /**
     * 将对象转换成json字符串。
     * @Author 鹿少年 2020年9月6日 下午9:48:49
     * @param object
     * @return
     */
    public static String ObjectToJson(Object object) {
        String result = null;
        try {
            result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * json结果集转对象
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = objectMapper.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转map
     * @Author 鹿少年 2020年9月18日 上午9:12:20
     * @param jsonData
     * @return
     */
    public static Map<String, String> JSONStringToMap(String jsonData) {
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(jsonData, Map.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将字符串数组转换为object数组
     * @param <T>
     * @Author 鹿少年 2020年9月18日 上午9:29:09
     * @param jsonData
     * @param beanType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] JsonArrayStringToObajectArray(String jsonData, Class<T[]> beanType) {
        T[] array = null;
        try {
            array = (T[]) objectMapper.readValue(jsonData, beanType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 将json数据转换成pojo对象list
     * @Author 鹿少年 2020年9月18日 下午1:46:45
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonArrayToList(String jsonData, Class<T[]> beanType) {
        try {
            return Arrays.asList(objectMapper.readValue(jsonData, beanType));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * jsonarray转list,不可以转map
     * @Author 鹿少年 2020年9月18日 下午1:11:20
     * @param jsonData
     * @param T
     * @return
     */
    public static <T> List<T> JsonArrayToList(String jsonData, TypeReference T) {
        try {
            return (List<T>) objectMapper.readValue(jsonData, T);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
      }
    /**
     * fastjson转换
     */

    /**
     * json字符串转JSONObject
     * @param jsonString
     * @return
     */
    public static JSONObject StringTransForjSON(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        return jsonObject;
    }

    /**
     * list字符串转JSON字符串
     *
     * @param lists
     * @return
     */
    public static String listTransforTOJSON(List<Object> lists) {

        String str = JSON.toJSON(lists).toString();
        return str;
    }

    /**
     * map转JSON
     *
     * @param map
     * @return
     */
    public static JSONObject mapTransTOJSON(Map<String, Object> map) {
        JSONObject json = new JSONObject(map);
        return json;
    }

    /**
     * json字符串转Object实体类
     * @param str
     * @return
     */
    public static <T> T JAVAObjectTransToJSON(String str, Class<T> beanType) {
          T t=JSONObject.parseObject(str,beanType);
          return t;
    }

    /**
     * json字符串转 Object实体List数组
     * @param str
     * @return
     */
    public static List<Object> getListFromJSON(String str){
        List<Object> list1 = JSONObject.parseObject(str, List.class);
        return list1;
    }

    /**
     * JSon字符串转Map
     * @param str
     * @return
     */
    public static Map<String,Object> getMapFromJSON(String jsonmap,String str){
        Map<String,Object>  map1 = JSONObject.parseObject(jsonmap, Map.class);
        Iterator iterator = map1.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry= (Map.Entry) iterator.next();
            System.out.println("key :"+entry.getKey()+"   value: " + entry.getValue());
        }

        return map1;
    }

    /**
     * 得到格式化的jSON字符串
     * @param jsonObject
     * @return
     */
    public static String  getJSONString(JSONObject jsonObject){
      return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
    }
    /**
     * string转格式化string
     * @param jsonObject
     * @return
     */
    public static String  getJSONString(Object jsonObject){
        return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
    }
    public static void main(String[] args) {

        String jsonString = "{\"abc\":\"1\",\"hahah\":\"2\"}";
        logger.info("stringתjsonObject:" + StringTransForjSON(jsonString));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "下午");
        map.put("age", 12);
        logger.info("mapתjsonString:" + mapTransTOJSON(map));
    }
}


