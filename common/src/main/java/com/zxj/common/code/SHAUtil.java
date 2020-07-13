package cn.zxj.utils.code;

import java.security.MessageDigest;

/**
 * @Author spike
 * @Date 2020-06-01
 **/
public class SHAUtil {
    private static final String SHA1 = "SHA1";


    public static String encryptSHA1(String data) {
        if (data==null){
            throw new RuntimeException("encrypt data is null");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA1);
            messageDigest.update(data.getBytes());
            return HexUtil.encodeHex(messageDigest.digest(),true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        System.out.println(encryptSHA1("1234"));
    }
}
