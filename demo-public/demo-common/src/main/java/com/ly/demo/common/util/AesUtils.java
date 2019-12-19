package com.ly.demo.common.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * AES的加密和解密 与 aes.js 一起使用前后端相同加解密算法
 *
 * @author libo
 */
public class AesUtils {

    //密钥 (需要前端和后端保持一致)
    private static final String SECRET_KEY = "flhgctyf1005144X";
    //算法
    private static final String ALGORITHM_SIGN = "AES/ECB/PKCS5Padding";


    /**
     * aes加密
     *
     * @param content 原始内容
     * @return 加密结果
     */
    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, SECRET_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * aes解密
     *
     * @param encrypt 加密内容
     * @return 解密结果
     */
    public static String aesDecrypt(String encrypt) {
        try {
            return aesDecrypt(encrypt, SECRET_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        int a = Character.MIN_RADIX;
        int b = Character.MAX_RADIX;
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }

    /**
     * base64 encode(编码)
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base64 code
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode(解码)
     *
     * @param base64Code 待解码的base64 code
     * @return 解码后的byte[]
     */
    private static byte[] base64Decode(String base64Code) {
        try {
            return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     */
    private static byte[] aesEncryptToBytes(String content, String encryptKey) {
        byte[] bytes;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHM_SIGN);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
            bytes = cipher.doFinal(content.getBytes("utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return bytes;
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) {
        byte[] bytes;
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHM_SIGN);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
            bytes = cipher.doFinal(encryptBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new String(bytes);
    }


    /**
     * AES 加密后 base64 encode
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base64 code
     */
    public static String aesEncrypt(String content, String encryptKey) {
        try {
            return base64Encode(aesEncryptToBytes(content, encryptKey));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * base64 decode 后 AES解密
     *
     * @param encryptStr 待解密的 base64 encode
     * @param decryptKey 解密密钥
     * @return 解密结果
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) {
        return StringUtils.isBlank(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {


        String content = "87ys_adminfdafdad+c";
        System.out.println("加密前：" + content);
        System.out.println("加密密钥和解密密钥：" + SECRET_KEY);
        String encrypt = aesEncrypt(content);
        System.out.println("加密后：" + encrypt);
        String decrypt = aesDecrypt(encrypt);
        System.out.println("解密后：" + decrypt);

        Random random = new Random(52);
        for (int i = 0; i < 16; i++) {
            //System.out.println((int) (Math.random() * 52 + 1));
        }

        char[] sequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int index = (int) (Math.random() * 51 + 1);
            stringBuffer.append(sequence[index]);
        }
        String yw = "87ys_adminfdafdadyyff";
        System.out.println(content);
        String keyrandom = stringBuffer.toString();
        System.out.println(keyrandom);
        String s = aesEncrypt(content, keyrandom);
        System.out.println(s);
        System.out.println(aesDecrypt(s, keyrandom));

    }
}