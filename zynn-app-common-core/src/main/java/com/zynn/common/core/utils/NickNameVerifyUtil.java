package com.zynn.common.core.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 昵称验证
 * @author 王宇林
 * @create 2019年2月21日14:13:42
 **/
public class NickNameVerifyUtil {

    /**
     * emoji表达式
     */
    private  static  final String PATTERN_STRING = "[\uD83C\uDC04-\uD83C\uDE1A]|[\uD83D\uDC66-\uD83D\uDC69]|[\uD83D\uDC66\uD83C\uDFFB-\uD83D\uDC69\uD83C\uDFFF]|[\uD83D\uDE45\uD83C\uDFFB-\uD83D\uDE4F\uD83C\uDFFF]|[\uD83C\uDC00-\uD83D\uDFFF]|[\uD83E\uDD10-\uD83E\uDDC0]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEF6]";

    /**
     * 验证手机号正确性
     * @param str 手机号
     * @return
     */
    public static boolean isEmoji(String str) {
        if(StringUtils.isNotBlank(str)){
            Pattern  p = Pattern.compile(PATTERN_STRING);
            Matcher  m = p.matcher(str);
            return m.matches();
        }
        return false;
    }
}
