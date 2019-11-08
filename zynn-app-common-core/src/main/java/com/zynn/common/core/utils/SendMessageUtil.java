package com.zynn.common.core.utils;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zynn.common.core.bo.BatchXuanWuContentBO;
import com.zynn.common.core.bo.BatchXuanWuSmsBO;
import com.zynn.common.core.bo.SingleXuanWuContentBO;
import com.zynn.common.core.bo.SingleXuanWuSmsBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * 玄武短信发送工具
 * @author wangyulin
 * @date  2019年2月20日17:05:53
 */
@Slf4j
public class SendMessageUtil {

    private static String username = "ynyx@ynyx";
    private static String password = "EM92epHG";

    public static void toSendMessageSingle(String phoneNum, String content) {

        try {
            doMassSend(phoneNum,content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void toSendMessageBatch(List<BatchXuanWuContentBO> batchXuanWuContentBOS) {
        try {
            subList(batchXuanWuContentBOS,1000,list -> {
                try {
                    doGroupSend(list);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static  void subList(List paramList, int maxNum , Consumer<List> consumer) {
        if (CollectionUtils.isEmpty(paramList)){
            return;
        }
        final List<List> partitionList = Lists.partition(paramList, maxNum);
        partitionList.forEach(l -> {
            consumer.accept(l);
        });
    }


    public static void doMassSend(String phoneNum, String content) throws Exception {
        String massURL = "http://211.147.239.62:9051/api/v1.0.0/message/mass/send";
        HttpURLConnection conn = getConnection(massURL, username, password);
        writeResponse(conn, getMassJsonContent(phoneNum,content));
    }

    public static void doGroupSend(List<BatchXuanWuContentBO> batchXuanWuContentBOS) throws Exception {
        String groupURL = "http://211.147.239.62:9051/api/v1.0.0/message/group/send";
        HttpURLConnection conn = getConnection(groupURL, username, password);
        writeResponse(conn, getGroupJsonContent(batchXuanWuContentBOS));
    }

    private static void writeResponse(HttpURLConnection conn, String requestContent) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(requestContent);
        out.close();

        StringBuilder response = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            response.append(tmp);
        }
        log.info("send batch message , parms = {} ,response = {}  ",requestContent,response.toString());
    }

    private static HttpURLConnection getConnection(String serverURL, String username, String password) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setRequestProperty("Accept", "application/json");
        String authorization = generateAuthorization(username, password);
        conn.setRequestProperty("Authorization", authorization);
        conn.connect();
        return conn;
    }

    /**
     * 生成http请求头Authorization串，用于鉴权
     */
    private static String generateAuthorization(String username, String password) {
        String md5Pwd = DigestUtils.md5Hex(password);
        String pair = username + ":" + md5Pwd;
        return Base64.encodeBase64String(pair.getBytes());
    }

    private static String getMassJsonContent(String phoneNum, String content) {
        String contactCellphone = phoneNum
                .replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll(",", "")
                .replaceAll("\"", "");
        SingleXuanWuSmsBO xuanWuSmsBO = new SingleXuanWuSmsBO();
        xuanWuSmsBO.getItems().add(new SingleXuanWuContentBO(contactCellphone, ""));
        xuanWuSmsBO.setContent(content);
        return JSONObject.toJSONString(xuanWuSmsBO);
    }

    private static String getGroupJsonContent(List<BatchXuanWuContentBO> batchXuanWuContentBOS) {
        BatchXuanWuSmsBO batchXuanWuSmsBO = new BatchXuanWuSmsBO();
        batchXuanWuContentBOS.forEach(b -> {
            b.setTo(b.getTo().replaceAll(" ", "")
                    .replaceAll("-", "")
                    .replaceAll(",", "")
                    .replaceAll("\"", ""));
        });
        batchXuanWuSmsBO.getItems().addAll(batchXuanWuContentBOS);
        return JSONObject.toJSONString(batchXuanWuSmsBO);
    }

}
