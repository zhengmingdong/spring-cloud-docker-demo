package com.zynn.common.core.utils;

import com.zynn.common.core.constant.YinianConstant;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

/**
 * @author yu_chen
 * @date 2018年06月05日15:40:51
 */
public class UrlToShortUtil {

    private static HttpClient httpclient = HttpClients.createDefault();

    public static void main(String[] args) {
        urlToShort("");
    }

    /**
     * 解密
     *
     * @param url
     * @return
     */
    public static String urlToShort(String url) {
        try {
            HttpGet httpGet = new HttpGet(YinianConstant.SUOLINNK + URLEncoder.encode(url, "utf-8"));
            HttpResponse execute = httpclient.execute(httpGet);
            return EntityUtils.toString(execute.getEntity(), "utf-8");
        } catch (Exception e) {
            return url;
        }
    }
}
