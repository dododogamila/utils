package cn.zxj.utils.code;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class AESX3 {
    /**
     * 封装原解密方法， 增加对空值的处理
     * @param sourceText
     * @param aesKey
     * @return
     */
    public static String decodePhone4VPS(String sourceText, String aesKey) {
        if(sourceText==null || "".equals(sourceText)){
            return "";
        }else{
            return  aesDecode(sourceText,aesKey);
        }
    }

    public static String aesEncode(String sourceText, String aesKey) {

        try {
            return SSOBase64.encode(AES.encode(sourceText.getBytes("UTF-8"),
                    aesKey.getBytes("UTF-8"))).replaceAll("\\n", "").replaceAll("\\r", "");
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static String Encode16(String sourceText, String aesKey)
            throws UnsupportedEncodingException, Exception {
        return Hex.encodeHexString(AES.encode(sourceText.getBytes("UTF-8"),
                aesKey.getBytes("UTF-8")));
    }

    public static String Decode16(String encodeText, String aesKey)
            throws Exception, UnsupportedEncodingException,
            Exception {
        return new String(
                AES.decode(Hex.decodeHex(encodeText.toCharArray()), aesKey
                        .getBytes("UTF-8")), "UTF-8");
    }

    public static String aesDecode(String encodeText, String aesKey) {
        try {
            return new String(AES.decode(SSOBase64.decode(encodeText
                    .getBytes("UTF-8")), aesKey.getBytes("UTF-8")), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public final static String md5(String s) {
        try {
            System.out.printf("string length: %d\n", s.length());
            System.out.printf("data: %s\n", s);
            byte[] strTemp = s.getBytes("UTF-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            String sign = Hex.encodeHexString(md).toUpperCase();
            System.out.println("sign: " + sign);
            return sign;
        } catch (Exception e) {
            return "";
        }
    }


    public static String Encode(String toJsonItem, String e2AppEn32Key) {

        return aesEncode(toJsonItem, e2AppEn32Key);
    }

    public static void main(String[] args) {

        /*for(int i=0;i<10;i++) {
            String pass = RandomUtil.generateMinString(8);
            String passEncode = AESX3.aesEncode(pass, "WbE3osPz60XB3t956iftYw==");

            System.out.println(pass + ":" + passEncode);
        }*/
        String passEncode = AESX3.aesEncode("Lxd&828927", "#s15key#iLily*@i!#vpab#456i#!0#n");
        System.out.println(passEncode);
        //URLDecoder.decode()
        String num = AESX3.aesDecode("2nfehgxrM3S9ZfEkbV6CnQ==","#s15key#iLily*@i!#vpab#456i#!0#n");
        System.out.println(num);
    }
}

