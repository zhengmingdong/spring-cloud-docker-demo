package com.zynn.common.core.utils;

import java.net.URLEncoder;

/**
 * 对字符串进行加密和URL encode 操作 并且捕获异常
 *
 * @author yu_chen
 * @date 2018年06月05日15:45:26
 */
public class EncodeUtil {

    public static void main(String[] args) {
        String s = encodeString("43107");
        System.out.println(s);
    }

    /**
     * 加密字符串
     *
     * @param param 参数值
     * @return 如果加密失败就返回null
     */
    public static String encodeString(String param) {
        String result = "";
        try {
            param = DesUtil.encryptDES(param);
            result = URLEncoder.encode(param, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}
