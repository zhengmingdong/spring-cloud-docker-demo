package com.zynn.api.yinian.gateway.filters;

import com.zynn.api.yinian.gateway.constant.LoginUserConstant;
import com.zynn.api.yinian.gateway.constant.SignConstant;
import com.zynn.api.yinian.gateway.constant.UrlConstant;
import com.zynn.api.yinian.gateway.excetions.MethodNotAllowedException;
import com.zynn.api.yinian.gateway.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
public class SignFilter implements GlobalFilter, Ordered {

    @Autowired
    private ValueOperations valueOperations;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getRawPath();
        if (UrlConstant.matchPath(SignConstant.NO_VERIFY_PATH, path) || !UrlConstant.matchPath(SignConstant.VERIFY_PATH, path)){
            return chain.filter(exchange);
        }

        if (exchange.getRequest().getMethod().matches("GET")){
            return  chain.filter(exchange);
        }

        final HttpHeaders headers = exchange.getRequest().getHeaders();

        final String requestId = headers.getFirst(SignConstant.CONSTANT_REQUEST);
        String userInfoKey = headers.getFirst(LoginUserConstant.APP_USER_INFO_KEY);
        String sign = headers.getFirst(SignConstant.CONSTANT_SIGN);

        if(SignConstant.INNER_SIGN.equals(sign)){
            return chain.filter(exchange);
        }

        if(StringUtils.isBlank(requestId) || StringUtils.isBlank(sign) ){
            throw new MethodNotAllowedException();
        }

        final String redisKey  = SignConstant.REDIS_PRE.concat("_").concat(requestId).concat("_").concat(userInfoKey);

        if (valueOperations.get(redisKey) != null){
            throw new MethodNotAllowedException();
        }

        if (!MD5Utils.md5Encrypt(SignConstant.encryptRule(requestId)).equals(sign)){
            throw new MethodNotAllowedException();
        }

        valueOperations.set(redisKey,requestId,60 * 60, TimeUnit.SECONDS);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -8;
    }
}
