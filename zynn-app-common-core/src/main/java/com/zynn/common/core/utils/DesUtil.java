package com.zynn.common.core.utils;

import com.zynn.common.core.constant.DesConstant;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author yu_chen
 * @date 2018年06月05日15:40:51
 */
public class DesUtil {


    public static void main(String[] args) throws Exception {

    }

    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    /**
     * 加密
     *
     * @param encryptString 加密的字符串
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(DesConstant.DES_KEY.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    /**
     * 解密
     *
     * @param decryptString 解密的字符串
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString)
            throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(DesConstant.DES_KEY.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }
}
