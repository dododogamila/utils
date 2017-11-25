package cn.zxj.utils.propertiy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取
 */
public class PropertyUtil {
    Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    static Properties properties=new Properties();
    private static PropertyUtil _instance = null;

    public static PropertyUtil ShareInstance() {
        if(_instance == null){
            _instance = new PropertyUtil();
        }
        return _instance;
    }

    public PropertyUtil() {
        InputStream is= PropertyUtil.class.getResourceAsStream("/config.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    public static  String getProperty(String key){
        String value= properties.getProperty(key);
        if(value==null){
            value= properties.getProperty(key);
        }
        return value;
    }

    public static int getIntProperty(String key){
        String value=getProperty(key);
        if(value==null)return 0;
        return Integer.parseInt(value.trim());
    }

}


