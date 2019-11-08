package com.zynn.common.core.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 手机号验证共计
 * @author 王宇林
 * @create 2019年2月21日14:13:42
 **/
public class CellphoneVerifyUtil {

    /**
     * 手机号验证表达式
     */
    private  final static String VERIFY = "^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$";

    /**
     * 手机号验证表达式
     */
    private  final static String EMAIL_VERIFY = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * QQ号验证表达式
     */
    private  final static String QQ_VERIFY = "^[1-9][0-9]{4,10}$";


    /**
     * 验证手机号正确性
     * @param str 手机号
     * @return
     */
    public static boolean isCellphone(String str) {
        if(StringUtils.isNotBlank(str)){
            Pattern  p = Pattern.compile(VERIFY);
            Matcher  m = p.matcher(str);
            return m.matches();
        }
        return false;
    }


    /**
     * 验证邮箱正确性
     * @param email 邮箱
     * @return
     */
    public static boolean isEmail(String email) {
        if(StringUtils.isNotBlank(email)){
            Pattern  p = Pattern.compile(EMAIL_VERIFY);
            Matcher  m = p.matcher(email);
            return m.matches();
        }
        return false;
    }

    /**
     * 验证QQ正确性
     * @param qq
     * @return
     */
    public static boolean isQQ(String qq) {
        if(StringUtils.isNotBlank(qq)){
            Pattern  p = Pattern.compile(QQ_VERIFY);
            Matcher  m = p.matcher(qq);
            return m.matches();
        }
        return false;
    }
}
