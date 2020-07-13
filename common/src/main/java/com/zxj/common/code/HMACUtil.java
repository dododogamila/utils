package cn.zxj.utils.code;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author spike
 * @Date 2020-06-01
 **/
public class HMACUtil {

    private static final String HMACMD5 = "HmacMD5";
    private static final String HMACSHA256 = "HmacSHA256";
    private static final String HMACSHA512 = "HmacSHA512";

    // HMAC MD5 加密
    public static String encryptHmacMD5(String data, String key) {
        paramCheck(data, key);
        return encryptHmac(data, key, HMACMD5);
    }

    // HMAC SHA256 加密
    public static String encryptHmacSHA256(String data,String key) {
        paramCheck(data, key);
        return encryptHmac(data, key, HMACSHA256 );
    }

    // HMAC SHA521 加密
    public static String encryptHmacSHA512(String data, String key) {
        paramCheck(data, key);
        return encryptHmac(data, key, HMACSHA512);
    }

    // 基础MAC 算法
    public static String encryptHmac(String data,String  key, String type) {
        try {
            // 1、还原密钥
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), type);
            // 2、创建MAC对象
            Mac mac = Mac.getInstance(type);
            // 3、设置密钥
            mac.init(secretKey);
            // 4、数据加密
            byte[] bytes = mac.doFinal(data.getBytes());
            // 5、生成数据
            String rs = encodeHex(bytes);
            return rs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 数据准16进制编码
    private static String encodeHex(final byte[] data) {
        return HexUtil.encodeHex(data, true);
    }

    private static void paramCheck(String data, String key) {
        if (data ==null || key ==null ){
            throw new RuntimeException("param is null");
        }
    }

    public static void main(String[] args) {
        String data = "java小工匠";
        // MD5
        String key = "12343";
        String hmacMd5Encrypt = encryptHmacMD5(data, key);
        System.out.println("HMAC Md5 加密:" + hmacMd5Encrypt);
        // SHA256
        String hmacSha256Encrypt = encryptHmacSHA256(data, key);
        System.out.println("HMAC SHA256 加密:" + hmacSha256Encrypt);
        // SHA512
        String hmacSha512Encrypt = encryptHmacSHA512(data, key);
        System.out.println("HMAC SHA512 加密:" + hmacSha512Encrypt);
    }
}
