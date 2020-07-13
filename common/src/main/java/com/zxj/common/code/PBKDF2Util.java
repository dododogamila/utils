package cn.zxj.utils.code;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import static cn.zxj.utils.code.StringUtils.getRandomStr;

/**
 * @Author spike
 * @Date 2020-06-01
 **/
public class PBKDF2Util {
    private static final int SALT_LENGTH = 12;
    private static final int DEFAULT_ITERATIONS = 10000;


    /**
     * Constructs PKBDF2 hash (see RFC 2898 for a description of PBKDF2).
     *
     * @param data
     * @param salt
     * @return constructed hash
     */
    public static String computePBKDF2Hash(String data, String salt, int iterations)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(Charset.forName("UTF-8")), iterations, 256);
        byte[] rawHash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(rawHash);
    }

    public static String createPBKDF2Hash(String data){
        if (data==null){
            throw new RuntimeException("encrypt data is null");
        }
        String hash = null;
        try {
            String salt = getRandomStr(SALT_LENGTH);
            hash = computePBKDF2Hash(data, salt, DEFAULT_ITERATIONS);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return hash;
    }



    public static void main(String[] args){
        System.out.println(createPBKDF2Hash("12343afsasdfasdfasdfadf4"));
    }
}
