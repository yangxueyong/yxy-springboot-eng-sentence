package com.example.eng.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

public class AESCodeUtils {
 
    // 算法名
    public static final String KEY_NAME = "AES";
    // 加解密算法/模式/填充方式
    // ECB模式只用密钥即可对数据进行加密解密，CBC模式需要添加一个iv
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
 
    /**
     * 微信 数据解密<br/>
     * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充<br/>
     * 对称解密的目标密文:encrypted=Base64_Decode(encryptData)<br/>
     * 对称解密秘钥:key = Base64_Decode(session_key),aeskey是16字节<br/>
     * 对称解密算法初始向量:iv = Base64_Decode(iv),同样是16字节<br/>
     *
     * @param encrypted   目标密文
     * @param session_key 会话ID
     * @param iv          加密算法的初始向量
     */
    public static String wxDecrypt(String encrypted, String session_key, String iv) {
        String json = null;
        byte[] encrypted64 = Base64.decodeBase64(encrypted);
        byte[] key64 = Base64.decodeBase64(session_key);
        byte[] iv64 = Base64.decodeBase64(iv);
        byte[] data;
        try {
            init();
            json = new String(decrypt(encrypted64, key64, generateIV(iv64)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
 
    /**
     * 初始化密钥
     */
    public static void init() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator.getInstance(KEY_NAME).init(128);
    }
 
    /**
     * 生成iv
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        // iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        // Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_NAME);
        params.init(new IvParameterSpec(iv));
        return params;
    }
 
    /**
     * 生成解密
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv)
            throws Exception {
        Key key = new SecretKeySpec(keyBytes, KEY_NAME);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }

    public static void main(String[] args) {
        System.out.println(wxDecrypt("iqV/23sSbZ4pXLAHyT3XBUbEAA7xioOqq7LXNiUjnZWlwF4WVB6vWbqbTlCAFTyW/PmKzrBL5vXPXWa0SE2JUkmeSXtmUra7YnorYMwXkg5JIkjOTc/AIalL1VWqPwRwYTMOEcpfneMCXTVoH5bdfasoiK2KKcmyokN4w68pWzfEQBcbk8OV6ZtOGPCQ18pKtW6z3z5zKSImdDsvJnRpzI5S2iMHyneGtVZYorlZ0dwSTkrfCsgzmdSJ40s2rTKSNY7Xk7iMSqnCc+IAkApC31aUHxtXE7oqBsjv3S/VvWGTphWxTrstu55BtuXIcEOnB2295gVnaXQKAl0pBZQTPVIMCWehFXdb+UMZMvot7Vj5deRdOeKsPUNrRhIq3th3TLhWHBpGJpau2YdL0t15aUk88vSNBnhduw6Nb44YuB2L74dxC65f0GO/2eNEcnpIPNqgzu+uUE0ZfoI0/ZgQYA==",
                "wB9zo3MD/bFX0NzHuvFCaw==",
                "u/BT51JD3GNYZOrzup9Xpw=="));
    }
}