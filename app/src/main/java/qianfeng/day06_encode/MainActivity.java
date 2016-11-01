package qianfeng.day06_encode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Toast;

//import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
//import java.security.cert.Extension;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
/*import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;*/
import javax.crypto.SecretKey;

import qianfeng.day06_encode.code.BASE64;
import qianfeng.day06_encode.code.CommUtils;
import qianfeng.day06_encode.code.DES;
import qianfeng.day06_encode.code.MD5;
import qianfeng.day06_encode.code.RSA;
import qianfeng.day06_encode.code.SHA;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ENCODE";
    private static final String STR_BASE64 = "this is base64";
    private static final String STR_MD5 = "this is md5";
    private static final String STR_SHA = "来两个中文 is fdfdfssha this is shathis is shathis is shathis is shathis is shathis is sha";
    private static final String STR_DES = "this is des";
    private static final String STR_RSA = "this is rsa!!!";
    public String base64encoder;
    public SecretKey secretKey;
    public byte[] encrypt;
    public KeyPair keyPair;
    public byte[] rsaEncrypt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * BASE 64
     *
     * @param view
     */
    public void base64encode(View view) {
        //文本编码
        base64encoder = BASE64.base64encoder(STR_BASE64);
        Log.e(TAG, base64encoder);
    }

    public void base64decode(View view) {
        if (base64encoder != null) {
            //文本解码
            String base64decode = BASE64.base64decode(base64encoder);
            Log.e(TAG, base64decode);
        }
    }

    /**
     * MD5s
     * @param view
     */
    public void md5(View view) {
        byte[] md5 = MD5.getMD5(STR_MD5.getBytes());
        StringBuilder sb  = new StringBuilder();
        for (byte b : md5) {
            sb.append(b);
        }
        Log.e(TAG, "md5:"+sb.toString());
        String md5Str = CommUtils.bytesConvertToHexString(md5);
        Log.e(TAG, "md5:"+md5Str);
    }

    /**
     * SHA
     * @param view
     */
    public void sha(View view) {
        try {
            byte[] bytes = SHA.encodeSHA(STR_SHA.getBytes());
            byte[] bytes256 = SHA.encodeSHA256(STR_SHA.getBytes());
            byte[] bytes384 = SHA.encodeSHA384(STR_SHA.getBytes());
            byte[] bytes512 = SHA.encodeSHA512(STR_SHA.getBytes());

            String bytesStr = CommUtils.bytesConvertToHexString(bytes);
            String bytes256Str = CommUtils.bytesConvertToHexString(bytes256);
            String bytes384Str = CommUtils.bytesConvertToHexString(bytes384);
            String bytes512Str = CommUtils.bytesConvertToHexString(bytes512);

            Log.e(TAG,"SIZE:"+bytesStr.length()+" "+bytesStr+" length:"+bytes.length);
            Log.e(TAG,"SIZE:"+bytes256Str.length()+" "+bytes256Str+" length:"+bytes256.length);
            Log.e(TAG,"SIZE:"+bytes384Str.length()+" "+bytes384Str+" length:"+bytes384.length);
            Log.e(TAG,"SIZE:"+bytes512Str.length()+" "+bytes512Str+" length:"+bytes512.length);

           /* StringBuilder sb  = new StringBuilder();
            for (byte b : bytes512) {
                sb.append(b);
            }
            Log.e(TAG,"SIZE:"+bytes512Str.length()+" "+bytes512Str+" length:"+sb.length());*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * DES
     * @param view
     */
    public void desInit(View view) {
        //初始化密钥
        try {
            secretKey = DES.initKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void desEncrypt(View view) {
        //加密
        if (secretKey != null) {
            try {
                //第一个参数 字节数组，密钥
                encrypt = DES.encrypt(STR_DES.getBytes(), secretKey);
                String encryptStr = CommUtils.bytesConvertToHexString(encrypt);
                Log.e(TAG,"encryptStr:"+encryptStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "请初始化密钥", Toast.LENGTH_SHORT).show();
        }
    }
    public void desDecrypt(View view) {
        //解密
        if (secretKey != null) {
            try {
                byte[] decrypt = DES.decrypt(encrypt, secretKey);
                Log.e(TAG,"decryptStr:"+new String(decrypt));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "请初始化密钥", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * RSA
     * @param view
     */
    public void rsaInit(View view) {
        //rsa 初始化
        try {
            keyPair = RSA.initKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void rsaEncrypt(View view) {
        if (keyPair != null) {
            //rsa 加密
            RSAPublicKey rsaPublicKey = RSA.getRSAPublicKey(keyPair);   //获取公钥
//            RSAPrivateKey rsaPrivateKey = RSA.getRSAPrivateKey(keyPair);
            try {
                rsaEncrypt = RSA.encrypt(STR_RSA.getBytes(), rsaPublicKey.getEncoded());
//                rsaEncrypt = RSA.encrypt(STR_RSA.getBytes(), rsaPrivateKey.getEncoded());
                String encryptStr = CommUtils.bytesConvertToHexString(rsaEncrypt);
                Log.e(TAG,"encryptStr:"+encryptStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "请初始化密钥", Toast.LENGTH_SHORT).show();
        }

    }

    public void rsaDecrypt(View view) {
        //rsa 解密
        if (keyPair != null) {
            RSAPrivateKey rsaPrivateKey = RSA.getRSAPrivateKey(keyPair);
//            RSAPublicKey rsaPublicKey = RSA.getRSAPublicKey(keyPair);
            try {
                byte[] decrypt = RSA.decrypt(rsaEncrypt, rsaPrivateKey.getEncoded());
//                byte[] decrypt = RSA.decrypt(rsaEncrypt, rsaPublicKey.getEncoded());
                Log.e(TAG,"decrypt:"+new String(decrypt));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "请初始化密钥", Toast.LENGTH_SHORT).show();
        }
    }
}
