package com.zynn.common.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 随即生产一定长度字符串
 *
 * @author 杨岳
 * @date 2018/7/30 17:27
 */
public class RandomStringUtils {

    private static int decimalValue = 32;

    private static int randomLength = 4;

    private static String s36bit_const = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private static final char[] legalCharsUpper = s36bit_const.toCharArray();

    /**
     * 随即长度的字符串
     *
     * @param count 需要随即字符的长度
     * @return 生产的随即字符串
     */
    public static String randomString(int count) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < count; i++) {
            int o = random.nextInt(legalChars.length);
            result += legalChars[o];
        }
        return result;
    }

    /**
     * 随即长度的字符串
     *
     * @param count 需要随即字符的长度
     * @return 生产的随即字符串
     */
    public static String randomStringUpper(int count) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < count; i++) {
            int o = random.nextInt(legalCharsUpper.length);
            result += legalCharsUpper[o];
        }
        return result;
    }

    /**
     * 十进制转32进制，不足四位用0补齐
     *
     * @param number
     * @return
     */
    private static String to32String(long number) {
        BigDecimal divide = new BigDecimal(decimalValue);
        BigDecimal decimal = new BigDecimal(number);
        String res = StringUtils.EMPTY;
        while (BigDecimal.ZERO.compareTo(decimal) != 0) {
            BigDecimal[] divRes = decimal.divideAndRemainder(divide);
            decimal = divRes[0];
            res = s36bit_const.charAt(divRes[1].intValue()) + res;
        }
        int length = res.length();
        if (length < randomLength) {
            for (int j = 0; j < randomLength - length; j++) {
                res = String.format("%s%s", "0", res);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(to32String(121L));
    }
}
