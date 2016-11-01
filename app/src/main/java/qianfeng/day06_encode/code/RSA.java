package qianfeng.day06_encode.code;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author keven
 * @data 16/5/8 下午11:26
 *
 */

public class RSA {
    /**
     * 算法名称
     */
    public static final String KEY_ALGORITHM = "RSA";   //算法
    /**
     * RSA密钥长度
     * 默认为1024位
     * 密钥长度必须为64的倍数 * 范围在512~65536之间
     */
    private static final int KEY_SIZE = 1024;



    /**
     * 生成密钥对
     *
     * @return 密钥对对象
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException {
        //实例化密钥对生成器
        KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥对生成器
        generator.initialize(KEY_SIZE);
        //生成密钥对
        return generator.generateKeyPair();
    }

    /**
     * 获得密钥对中公钥
     * @param pair 密钥对 *
     * @return
     */
    public static RSAPublicKey getRSAPublicKey(KeyPair pair){
        RSAPublicKey key = (RSAPublicKey)pair.getPublic();
        //获得公钥编码
        byte[] encoded = key.getEncoded();
        return key;
    }

    /**
     * 获得密钥对中私钥
     * @param pair 密钥对 *
     * @return
     */
    public static RSAPrivateKey getRSAPrivateKey(KeyPair pair){
        RSAPrivateKey key = (RSAPrivateKey)pair.getPrivate();
        //获得私钥编码
        byte[] encoded = key.getEncoded();
        return key;
    }



    /**
     * RSA加密
     * @param data  待解密的数据
     * @param key   公钥
     * @return      解密后的数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //取得私钥
        X509EncodedKeySpec spec = new X509EncodedKeySpec(key);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PublicKey publicKey = factory.generatePublic(spec);
        //对数据加密
        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * RSA解密
     *
     * @param data 待解密的数据
     * @param key  私钥
     * @return 解密后的数据
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //取得私钥
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(key);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = factory.generatePrivate(spec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }






}
