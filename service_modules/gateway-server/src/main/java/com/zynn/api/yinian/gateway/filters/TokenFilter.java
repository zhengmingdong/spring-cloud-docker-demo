package com.zynn.api.yinian.gateway.filters;

import com.alibaba.fastjson.JSONObject;
import com.zynn.api.yinian.gateway.bean.LoginAndRegisterResult;
import com.zynn.api.yinian.gateway.config.swagger.SwaggerProvider;
import com.zynn.api.yinian.gateway.constant.LoginUserConstant;
import com.zynn.api.yinian.gateway.constant.UrlConstant;
import com.zynn.api.yinian.gateway.excetions.AuthFailException;
import com.zynn.api.yinian.gateway.excetions.ShareLoseEfficacyException;
import com.zynn.api.yinian.gateway.service.LogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Component
public class TokenFilter implements GlobalFilter, Ordered {


    @Autowired
    private ValueOperations valueOperations;

    @Autowired
    private LogService logService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        String path = req.getURI().getRawPath();
        if (UrlConstant.matchPath(UrlConstant.PASS_SERVLET_PATHS, path) || UrlConstant.matchPath(UrlConstant.PASS_NO_AUTHS, path)
                || path.endsWith(SwaggerProvider.API_URI)){
            return chain.filter(exchange);
        }

        final String token = exchange.getRequest().getHeaders().getFirst(LoginUserConstant.APP_USER_INFO_KEY);

        final String iMei = exchange.getRequest().getHeaders().getFirst(LoginUserConstant.IMEI);

        if (StringUtils.isBlank(token)){
            throw new AuthFailException();
        }

        final String jsonStr = Optional.ofNullable(valueOperations.get(token)).map(Object::toString).orElseThrow(() -> new AuthFailException());

        LoginAndRegisterResult user = JSONObject.parseObject(jsonStr, LoginAndRegisterResult.class);

        final String userId = Optional.ofNullable(user.getId()).orElseThrow(() -> new AuthFailException());

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("loginUserId", userId).build();

        return chain.filter(exchange.mutate().request(request).build());

    }

    @Override
    public int getOrder() {
        return -9;
    }

}
