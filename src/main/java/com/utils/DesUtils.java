package com.utils;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author Hope
 * Date： 2020/12/15  11:42
 * 描述：明文加密以及解密，以及使用JDK1.8的方式
 */
public class DesUtils {
    private static final String Algorithm = "DESede";


    /**
     * 解密
     *
     * @param value 密文
     * @param key 密钥
     * @return 明文
     * @throws Exception
     */
    public static String decrypt3DES(String value, String key) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }

        // byte[] b = decryptMode(getKeyBytes(key), DatatypeConverter.parseBase64Binary(value));
        //JDK1.8替代上述方式
        byte[] b = decryptMode(getKeyBytes(key), Base64.getDecoder().decode(value));
        return new String(b);
    }


    /**
     * 加密
     * @param value 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String encrypt3DES(String value, String key) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        //return DatatypeConverter.printBase64Binary(encryptMode(getKeyBytes(key), value.getBytes("UTF-8")));
        return Base64.getEncoder().encodeToString(encryptMode(getKeyBytes(key), value.getBytes("UTF-8")));
    }

    @Nullable
    private static byte[] encryptMode(byte[] keyByte, byte[] src) {
        try {
            //加密
            SecretKey desKey = new SecretKeySpec(keyByte, Algorithm);
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private static byte[] decryptMode(byte[] keyByte, byte[] src) {
        try {
            //解密
            SecretKey desKey = new SecretKeySpec(keyByte, Algorithm);
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getKeyBytes(String key) throws Exception {
        if (StringUtils.isEmpty(key)) {
            throw new Exception("key is null or empty");
        }

        MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.update(key.getBytes());
        byte[] byteKey = alg.digest();
        int start = byteKey.length;
        byte[] byteKey24 = new byte[24];
        System.arraycopy(byteKey, 0, byteKey24, 0, start);
        System.arraycopy(byteKey, 0, byteKey24, start, 24 - start);
        return byteKey24;
    }


    public static void main(String[] args) throws Exception {
        String key = "dffgsgjhUJGJKJ67898hjh8";
        String text = "13114376517";
        String ciphText = encrypt3DES(text, key);
        System.out.println(ciphText);
        System.out.println(decrypt3DES(ciphText,key));
    }

}
