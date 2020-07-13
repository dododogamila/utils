package cn.zxj.utils.code;

public class RandomStringUtils {

    private static final String DIGITS_CANDIDATE_CHARS = "0123456789";

    public static String getRandomNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (DIGITS_CANDIDATE_CHARS.length() * Math.random());
            sb.append(DIGITS_CANDIDATE_CHARS.charAt(index));
        }
        return sb.toString();
    }

}
