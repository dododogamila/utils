package cn.zxj.utils.code;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author spike
 * @Date 2020-05-18
 **/
public class StringUtils {
    private static final String SALT_CANDIDATE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

    private static final String EMAIL_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


    public static boolean isEmail(String str){
        Pattern regex = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = regex.matcher(str);
        return matcher.matches();
    }
    public static String getRandomStr(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (SALT_CANDIDATE_CHARS.length() * Math.random());
            sb.append(SALT_CANDIDATE_CHARS.charAt(index));
        }
        return sb.toString();
    };

}
