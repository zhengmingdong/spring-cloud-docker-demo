package com.zynn.common.core.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 刘天元
 * @description 获取ip与端口号
 * @create 2018-07-22 09:57
 **/
@Configuration
public class ServiceInfoUtil implements ApplicationListener<WebServerInitializedEvent> {
    private static WebServerInitializedEvent event;

    private static String host;

    private static Integer port;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        ServiceInfoUtil.event = event;
    }

    public static int getPort() {
        if(port == null) {
            port = event.getWebServer().getPort();
            Assert.state(port != -1, "端口号获取失败");
        }
        return port;
    }

    public static String getIp() {
        if(StringUtils.isBlank(host)) {
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return host;
    }
}