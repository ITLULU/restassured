package utils;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

public class propertiesUtil {
    public static Properties properties=new Properties();
    public static BufferedReader buf=null;
    static {
        try {
            InputStream fileReader=new FileInputStream( new File("src/main/resources/conf/config.properties"));
            buf=new BufferedReader(new InputStreamReader(fileReader));
            properties.load(buf);
        } catch ( IOException e) {
            e.printStackTrace();
        }finally {
            if(buf!=null){
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  static  String getKeyByBundle(String  key){
            //直接写config即可，不需要写文件后缀名
            ResourceBundle resourceBundle = ResourceBundle.getBundle("conf/config");
            //变量Enum
            Enumeration enumeration = resourceBundle.getKeys();
        try {
            return new String(resourceBundle.getString(key).getBytes("iso-8859-1"), "utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  "";
    }

    public  static  String getKeyByBundle(String filename,String  key){
        //直接写config即可，不需要写文件后缀名
        ResourceBundle resourceBundle = ResourceBundle.getBundle(filename);
        //变量Enum
        Enumeration enumeration = resourceBundle.getKeys();
        try {
            return new String(resourceBundle.getString(key).getBytes("iso-8859-1"), "utf-8") ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  "";
    }

}
