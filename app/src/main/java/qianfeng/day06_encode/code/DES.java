package qianfeng.day06_encode.code;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by keven on 16/7/4.
 */
public class DES {

    //加密方式
    public final static String ALGORITHM = "DES";

    //密码，长度要是8的倍数  自定义
    private static  String password = "12345678";
    private static SecureRandom random;
//    private static SecretKey securekey;


    /**
     * 初始化密钥
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey initKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        //创建一个密匙工厂，然后用它把DESKeySpec进行转换
        SecretKeyFactory keyFactory =  SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }


    /**
     * DES加密
     *
     * @param data //待加密的数据
     * @return //加密后的数据
     * @throws  NoSuchAlgorithmException
     * @throws  InvalidKeyException
     * @throws  InvalidKeySpecException
     * @throws  NoSuchPaddingException
     * @throws  BadPaddingException
     * @throws  IllegalBlockSizeException
     */
    public static byte[] encrypt(byte[] data,SecretKey securekey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        try{
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //用密匙初始化Cipher对象  编码模式
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(data);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES数据解密
     *
     * @param data 待解密的数据
     * @throws  NoSuchAlgorithmException
     * @throws  InvalidKeyException
     * @throws  InvalidKeySpecException
     * @throws  NoSuchPaddingException
     * @throws  BadPaddingException
     * @throws  IllegalBlockSizeException
     */
    public static byte[] decrypt(byte[] data,SecretKey securekey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象   解码模式
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(data);
    }

}
