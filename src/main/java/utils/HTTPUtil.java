package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPUtil {
    static Logger logger = Logger.getLogger(HTTPUtil.class);

    /**
     * 获取响应code 200
     * @param httpResponse
     * @return
     */
     public static int getStatusCode(CloseableHttpResponse httpResponse ){
         return  httpResponse.getStatusLine().getStatusCode();
     }

    /**
     * 得到响应字符串
     * @param httpResponse
     * @return
     */
     public static String getResponseString(CloseableHttpResponse httpResponse){
         try {
             return  EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
         } catch (IOException e) {
             e.printStackTrace();
         }
         return  null;
     }

    /**
     * 得到所有的响应头
     * @param httpResponse
     * @return
     */
    public static Header[] getResHeader(CloseableHttpResponse httpResponse){
            return  httpResponse.getAllHeaders();
    }


    /**
     * 默认的get请求出来
     * @param url
     */
    public static void getDefault(String url){
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpget);
            //拿到Http响应状态码，例如和200,404,500去比较
            int responseStatusCode = getStatusCode(httpResponse);
            logger.info("response status code -->"+responseStatusCode);

            //把响应内容存储在字符串对象
            String responseString =getResponseString(httpResponse);
            //创建Json对象，把上面字符串序列化成Json对象
            JSONObject responseJson = JSON.parseObject(responseString);
            logger.info("respon json from API-->" + responseJson);

            //获取响应头信息,返回是一个数组
            Header[] headerArray = httpResponse.getAllHeaders();
            //创建一个hashmap对象，通过postman可以看到请求响应头信息都是Key和value得形式，所以我们想起了HashMap
            HashMap<String, Object> hm = new HashMap<String, Object>();
            //增强for循环遍历headerArray数组，依次把元素添加到hashmap集合
            for(Header header : headerArray) {
                hm.put(header.getName(), header.getValue());
            }
            JSONObject jsonObject = JsonUtil.mapTransTOJSON(hm);
            logger.info("jsonObject:"+jsonObject);
            String json=JsonUtil.ObjectToJson(jsonObject);
            logger.info("json:"+json);
            logger.info("jsonString："+JsonUtil.getJSONString(jsonObject));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不带参数的Url get请求
     * @param url
     * @return
     */
    public static CloseableHttpResponse get(String url){
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * get请求 header
     * @param url
     * @param headermap
     * @return
     */
    public static CloseableHttpResponse getWithHead(String url,Map<String,String> headermap)  {

        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        //加载请求头到httpget对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httpget.addHeader(entry.getKey(), entry.getValue());
        }
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpget);
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * 带参数的get请求
     * @param url
     * @param pairs
     * @return
     */
    public static CloseableHttpResponse getWithParams(String url,List<BasicNameValuePair>pairs)  {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder uriBuilder=new URIBuilder(url);
            for (int i=0;i<pairs.size();i++) {
                uriBuilder.setParameters((NameValuePair) pairs.get(i));
            }
            HttpGet httpget = new HttpGet(uriBuilder.toString());
            //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
            CloseableHttpResponse httpResponse = httpclient.execute(httpget);
            httpclient.close();
            return httpResponse;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;


    }


    /**
     * 参数在map里
     * @param url
     * @param paramsmap
     * @param headermap
     * @return
     */
    public static  CloseableHttpResponse post(String url, HashMap<String,Object>paramsmap, HashMap<String,String> headermap){
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpPost的请求对象
        HttpPost httppost = new HttpPost(url);

        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();

        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
            logger.info("formEntity:"+formEntity);
            // 第一步：通过setEntity 将我们的entity对象传递过去
            httppost.setEntity(new StringEntity(JsonUtil.MapToJsonString(paramsmap)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //加载请求头到httppost对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httppost);
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * 直接请求数据是json数据
     * @param url
     * @param headermap
     * @param jsonObject
     * @return
     */
    public static  CloseableHttpResponse post(String url, HashMap<String,String> headermap,String jsonObject){
        //创建一个可关闭的HttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建一个HttpPost的请求对象
        HttpPost httppost = new HttpPost(url);
        try {
            // 第一步：通过setEntity 将我们的entity对象传递过去
            httppost.setEntity(new StringEntity(jsonObject));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //加载请求头到httppost对象
        for(Map.Entry<String, String> entry : headermap.entrySet()) {
            httppost.addHeader(entry.getKey(), entry.getValue());
        }
        //发送post请求
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httppost);
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    /**
     * put请求
     * @param url
     * @param entityString
     * @param headerMap
     * @return
     */
    public static CloseableHttpResponse put(String url, String entityString, HashMap<String, String> headerMap)  {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);
        CloseableHttpResponse httpResponse =null;
        try {
            httpput.setEntity(new StringEntity(entityString));
            for(Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpput.addHeader(entry.getKey(), entry.getValue());
            }
            //发送put请求
            httpResponse= httpclient.execute(httpput);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return httpResponse;
    }

    /**
     * delete请求
     * @param url
     * @return
     */
    public static CloseableHttpResponse delete(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpdel = new HttpDelete(url);

        //发送dellete请求
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpclient.execute(httpdel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }


}
