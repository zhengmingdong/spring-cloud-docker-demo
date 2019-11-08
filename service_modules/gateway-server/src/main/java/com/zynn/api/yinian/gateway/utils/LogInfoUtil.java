package com.zynn.api.yinian.gateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.zynn.api.yinian.gateway.bean.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;


/**
 * 封装log信息工具类
 */
@Component
public class LogInfoUtil {


    private static SnowFlake snowFlake;

    @Autowired
    public  void setSnowFlake(SnowFlake snowFlake) {
        LogInfoUtil.snowFlake = snowFlake;
    }

    public static LogInfo logInfoForWeb(ServerWebExchange webExchange){
        LogInfo logInfo = new LogInfo();
        final ServerHttpRequest request = webExchange.getRequest();
        final HttpHeaders headers = request.getHeaders();

        String path = request.getURI().getRawPath();
        final String method = request.getMethodValue();
        final String params = JSONObject.toJSONString(request.getQueryParams());
        final String version = headers.getFirst("version");
        final String source = headers.getFirst("source");
        final String jwt = headers.getFirst("app-user-info-key");
        final String userId = headers.getFirst("loginUserId");
        final String userAgent = headers.getFirst("user-agent");
        final String imei = headers.getFirst("imei");
        final String deviceInfo = headers.getFirst("device-info");
        URI redirect = (URI) webExchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);
        final String host = Optional.ofNullable(redirect).map(URI::getHost).orElse("");
        final int port = Optional.ofNullable(redirect).map(URI::getPort).orElse(0);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logInfo.setCreateTime(format.format(new Date()));
        logInfo.setDeviceInfo(deviceInfo);
        logInfo.setImei(imei);
        logInfo.setJwt(jwt);
        logInfo.setMethod(method);
        logInfo.setParams(params);
        logInfo.setRedirectHost(host);
        logInfo.setRedirectPort(port);
        logInfo.setRequestId(snowFlake.nextId());
        logInfo.setRequestUrl(path);
        logInfo.setSource(source);
        logInfo.setUserAgent(userAgent);
        logInfo.setVersion(version);
        logInfo.setUserId(userId);
        return logInfo;
    }


}
