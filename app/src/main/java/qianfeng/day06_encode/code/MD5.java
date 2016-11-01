package qianfeng.day06_encode.code;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 *
 * @author keven
 * @data 16/5/8 下午11:10
 * MD5算法是典型的消息摘要算法,其前身有MD2,MD3和,MD4算法。
 * 不论哪种MD算法,它们都需要获得以讹随 机长度的信息并产生以一个128位的信息摘要。
 * 如果将这个128位的信息摘要转换为十六进制,就可以得到一 个32位的字符串,
 * 因此我们见到的大部分MD5算法的指纹都是32位十六进制的字符串。
 * <p/>
 * 特点：消息摘要的主要特点是对同一段数据做多次摘要处理后,其摘要值完全一致。
 */

public class MD5 {
    /**
     * 获得数据的MD5编码
     *
     * @param data 待处理的数据
     * @return 加密后的MD5值
     */
    public static byte[] getMD5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


}
