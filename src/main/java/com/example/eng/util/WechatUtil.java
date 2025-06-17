package com.example.eng.util;

import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

@Slf4j
public class WechatUtil {
    public static void main(String[] args) {
        String decrypt = decrypt("iqV/23sSbZ4pXLAHyT3XBUbEAA7xioOqq7LXNiUjnZWlwF4WVB6vWbqbTlCAFTyW/PmKzrBL5vXPXWa0SE2JUkmeSXtmUra7YnorYMwXkg5JIkjOTc/AIalL1VWqPwRwYTMOEcpfneMCXTVoH5bdfasoiK2KKcmyokN4w68pWzfEQBcbk8OV6ZtOGPCQ18pKtW6z3z5zKSImdDsvJnRpzI5S2iMHyneGtVZYorlZ0dwSTkrfCsgzmdSJ40s2rTKSNY7Xk7iMSqnCc+IAkApC31aUHxtXE7oqBsjv3S/VvWGTphWxTrstu55BtuXIcEOnB2295gVnaXQKAl0pBZQTPVIMCWehFXdb+UMZMvot7Vj5deRdOeKsPUNrRhIq3th3TLhWHBpGJpau2YdL0t15aUk88vSNBnhduw6Nb44YuB2L74dxC65f0GO/2eNEcnpIPNqgzu+uUE0ZfoI0/ZgQYA==",
                "wB9zo3MD/bFX0NzHuvFCaw==",
                "u/BT51JD3GNYZOrzup9Xpw==", "utf-8");
        System.out.println(decrypt);
    }
    /**
     * 解密用户信息
     */
    public static String decrypt(String data, String key, String iv, String encodingFormat) {
        //被加密的数据
        byte[] dataByte = Base64.decode(data);
        //加密秘钥
        byte[] keyByte = Base64.decode(key);
        //偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, encodingFormat);
                return result;
            }
            return null;
        } catch (Exception e) {
            log.error("解密失败",e);
            throw new RuntimeException("解密失败");
        }
    }
}
