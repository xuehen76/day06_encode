package qianfeng.day06_encode.code;

/**
 * @author keven
 * @data 2016/10/30 下午9:02
 */

public class CommUtils {
    /**
     * byte数组 转 16进制
     * @param bytes
     * @return
     */
    public static String bytesConvertToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String s = Integer.toHexString(0xff & aByte);
            if (s.length() == 1) {
                sb.append("0" + s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
