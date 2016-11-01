package qianfeng.day06_encode.code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 *
 * @author keven
 * @data 16/5/8 下午11:12
 * SHA算法基于MD4算法基础之上,作为MD算法的继任者。
 * SHA与MD算法不同之处在于摘要长度,SHA算法的 摘要长度更长,安全性更高。
 */

public class SHA {

    /**
     * SHA-1消息摘要
     *
     * @param data 待处理的数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encodeSHA(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        return md.digest(data);
    }

    /**
     * SHA-256消息摘要
     *
     * @param data 待处理的数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encodeSHA256(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(data);
    }

    /**
     * SHA-384消息摘要
     *
     * @param data 待处理的数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encodeSHA384(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-384");
        return md.digest(data);
    }

    /**
     * SHA-512消息摘要
     *
     * @param data 待处理的数据
     * @return 消息摘要
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encodeSHA512(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        return md.digest(data);
    }
}
