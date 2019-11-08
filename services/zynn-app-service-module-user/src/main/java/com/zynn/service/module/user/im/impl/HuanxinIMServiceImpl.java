package com.zynn.service.module.user.im.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zynn.service.module.user.config.HuanxinConfiguration;
import com.zynn.service.module.user.enums.IMServerEnum;
import com.zynn.service.module.user.im.AbstractIMService;
import com.zynn.service.module.user.im.exceptions.TokenFailExcetion;
import com.zynn.service.module.user.im.params.huanxin.*;
import com.zynn.service.module.user.im.result.huanxin.ClusterDetailResult;
import com.zynn.service.module.user.im.result.huanxin.HuanxinBaseResult;
import com.zynn.service.module.user.im.result.huanxin.TokenResult;
import com.zynn.service.module.user.im.result.huanxin.UserRegisterResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.zynn.common.core.constant.RedisKeyConstants.HUANXIN_TOKEN;

/**
 * 环信的IM服务实现类
 * @Author zhanghao
 * @date 2019/3/21 18:02
 **/
@Service
@Slf4j
public class HuanxinIMServiceImpl<P extends HuanxinBaseParams,R extends HuanxinBaseResult> extends AbstractIMService<P,R> {


    private static final String URL_HOST = "http://a1.easemob.com/{org_name}/{app_name}";

    // manager 环信用户名称
    private static final String MANAGER_HUANXIN_USER_NAME = "manager";

    // manager 用户ID
    private static final String MANAGER_USER_ID = "-1";

    //manager 头像
    private static final String MANAGER_PIC = "https://public.zhuiyinanian.com/user-avatar.jpg";

    //manager 昵称
    private static final String MANAGER_NIKE_NAME = "鸭鸭助手";

    @Resource
    private WebClient webClient;

    @Autowired
    private ValueOperations valueOperations;

    @Resource
    private HuanxinConfiguration huanxinConfiguration;


    private ThreadLocal<Boolean> tokenFailRetry = new ThreadLocal<Boolean>() {
    };


    /**
     * 创建webClient请求
     *
     * @return org.springframework.web.reactive.function.client.WebClient.ResponseSpec
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [path, params]
     **/
    private WebClient.ResponseSpec webClientForPost(String path, Consumer<HttpHeaders> httpHeaders, Object params) {
        return webClient.post().
                uri(URL_HOST.concat(path), huanxinConfiguration.getOrgName(), huanxinConfiguration.getAppName())
                .headers(httpHeaders)
                .syncBody(params)
                .retrieve();
    }

    /**
     * 创建webClient请求
     *
     * @return org.springframework.web.reactive.function.client.WebClient.ResponseSpec
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [path, params]
     **/
    private WebClient.ResponseSpec webClientForDelete(String path, Consumer<HttpHeaders> httpHeaders) {
        return webClient.delete().
                uri(URL_HOST.concat(path), huanxinConfiguration.getOrgName(), huanxinConfiguration.getAppName())
                .headers(httpHeaders)
                .retrieve();
    }

    /**
     * 创建webClient请求
     *
     * @return org.springframework.web.reactive.function.client.WebClient.ResponseSpec
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [path, params]
     **/
    private WebClient.ResponseSpec webClientForPut(String path, Consumer<HttpHeaders> httpHeaders, Object bodyParams, Map<String, Object> pathParams) {
        pathParams.put("org_name", huanxinConfiguration.getOrgName());
        pathParams.put("app_name", huanxinConfiguration.getAppName());
        return webClient.put().
                uri(URL_HOST.concat(path), pathParams)
                .headers(httpHeaders)
                .body(BodyInserters.fromObject(bodyParams))
                .retrieve();
    }

    /**
     * 创建webClient请求,参数在path上
     *
     * @return org.springframework.web.reactive.function.client.WebClient.ResponseSpec
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [path, token, params]
     **/
    private WebClient.ResponseSpec webClientForGet(String path, Consumer<HttpHeaders> httpHeaders, Map<String, Object> params) {
        params.put("org_name", huanxinConfiguration.getOrgName());
        params.put("app_name", huanxinConfiguration.getAppName());
        return webClient.get().
                uri(URL_HOST.concat(path), params)
                .headers(httpHeaders)
                .retrieve();
    }


    /**
     * 创建请求头
     *
     * @return java.util.function.Consumer<org.springframework.http.HttpHeaders>
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [token]
     **/
    private Consumer<HttpHeaders> createHttpHeaders(String token) {
        return (httpHeaders) -> {
            httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
            httpHeaders.add("Accept", "application/json;charset=UTF-8");
            Optional.ofNullable(token).ifPresent(e -> httpHeaders.add("Authorization", "Bearer ".concat(token)));
        };
    }


    /**
     * 为结果添加错误处理以及重试
     *
     * @return reactor.core.publisher.Mono<T>
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [resultMono, resultClass]
     **/
    private <T> Mono<T> addErrorHandler(Mono<T> resultMono, Class<T> resultClass) {
        return resultMono.onErrorResume(WebClientResponseException.class, err -> {
            //如果返回结果是429、503，有可能代表该接口被限流了，在5秒后重试一次
            if (err.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS) || err.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                final HuanxinBaseResult huanxinBaseParams = JSONObject.parseObject(err.getResponseBodyAsString(), HuanxinBaseResult.class);
                throw new RuntimeException(String.format("client error , error: %s , error_description = %s", huanxinBaseParams.getError(), huanxinBaseParams.getError_description()));
            } else {
                final HuanxinBaseResult huanxinBaseResult = JSONObject.parseObject(err.getResponseBodyAsString(), HuanxinBaseResult.class);
                huanxinBaseResult.setResponseCode(err.getRawStatusCode());
                log.error("huanXinClient error , error data = {}", JSONObject.toJSONString(huanxinBaseResult));
                return Mono.just(JSONObject.parseObject(JSONObject.toJSONString(huanxinBaseResult), resultClass));
            }
        }).retryBackoff(1, Duration.ofSeconds(5));
    }


    /**
     * token 无效进行，重新获取token，再试一遍
     *
     * @return reactor.core.publisher.Mono<?>
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param [mono, supplier]
     **/
    private <T> Mono<T> tokenFailRetry(Mono<T> mono, Supplier<Mono<T>> supplier) {
        return mono.flatMap(result -> {
            final String jsonStr = JSONObject.toJSONString(result);
            try {
                final HuanxinBaseResult baseResult = JSONObject.parseObject(jsonStr, HuanxinBaseResult.class);
                if (baseResult != null && baseResult.getResponseCode() != null && baseResult.getResponseCode() == HttpStatus.UNAUTHORIZED.value()) {
                    if (tokenFailRetry.get() == null || !tokenFailRetry.get()) {
                        valueOperations.getOperations().delete(HUANXIN_TOKEN);
                        tokenFailRetry.set(true);
                        return Mono.empty().then(supplier.get()).doFinally(e -> tokenFailRetry.remove());
                    }
                    throw new TokenFailExcetion("yet get token ,token fail");
                }
            } catch (JSONException ex) { //有可能出现返回值不是JSON对象的情况，不做处理
            }
            return Mono.just(result);
        });
    }


    /**
     * 获取环信的token
     *
     * @return reactor.core.publisher.Mono<java.lang.String>
     * @Author zhanghao
     * @Date 2019/3/22
     * @Param []
     **/
    private Mono<String> getToken() {
        String token = Optional.ofNullable(valueOperations.get(HUANXIN_TOKEN)).map(Object::toString).orElse(null);
        if (token == null) {
            TokenParams tokenParams = new TokenParams();
            tokenParams.setClient_id(huanxinConfiguration.getClentId());
            tokenParams.setClient_secret(huanxinConfiguration.getClientSecret());
            final Mono<TokenResult> resultMono = webClientForPost("/token", createHttpHeaders(null), tokenParams)
                    .bodyToMono(TokenResult.class);
            return addErrorHandler(resultMono, TokenResult.class)
                    .doOnSuccess(result -> {
                        //成功了之后放入redis
                        valueOperations.set(HUANXIN_TOKEN, result.getAccess_token(), result.getExpires_in(), TimeUnit.SECONDS);
                    })
                    .map(TokenResult::getAccess_token);
        }
        return Mono.just(token);
    }

    @Override
    public String getServiceName() {
        return IMServerEnum.huanxin.name();
    }

    @Override
    public Mono<UserRegisterResult> userRegister(HuanxinBaseParams params) {
        if (params instanceof UserRegisterParams) {
            UserRegisterParams userRegisterParams = (UserRegisterParams) params;
            return getToken().flatMap(token -> {
                Mono<UserRegisterResult> resultMono = webClientForPost("/users", createHttpHeaders(token), userRegisterParams)
                        .bodyToMono(String.class)
                        .flatMap(str -> {
                            //解析返回的JSON字符串，获得需求的数据
                            final JSONObject resultObj = JSONObject.parseObject(str);
                            if (resultObj.containsKey("entities")) {
                                final JSONObject entities = resultObj.getJSONArray("entities").getJSONObject(0);
                                final UserRegisterResult userRegisterResult = new UserRegisterResult();
                                userRegisterResult.setActivated(entities.getBoolean("activated"));
                                userRegisterResult.setNickname(entities.getString("nikename"));
                                userRegisterResult.setUsername(entities.getString("username"));
                                return Mono.just(userRegisterResult);
                            }
                            return Mono.just(resultObj.toJavaObject(UserRegisterResult.class));
                        });
                return tokenFailRetry(addErrorHandler(resultMono, UserRegisterResult.class), () -> userRegister(userRegisterParams));
            });
        }
        throw new IllegalArgumentException("params type not is UserRegisterParams");
    }


    @Override
    public Mono<Object> updateUserNickname(P params) {
        if (params instanceof UpdateNicknameParams) {
            UpdateNicknameParams nicknameParams = (UpdateNicknameParams) params;

            if (StringUtils.isBlank(nicknameParams.getUsername())) {
                return Mono.empty();
            }

            return getToken().flatMap(token -> {
                final Mono<Object> resultMono = webClientForPut("/users/{username}", createHttpHeaders(token), nicknameParams, Maps.newHashMap("username", nicknameParams.getUsername()))
                        .bodyToMono(String.class)
                        .flatMap(jsonStr -> {
                            //解析返回的JSON字符串，获得需求的数据
                            final JSONObject resultObj = JSONObject.parseObject(jsonStr);
                            if (resultObj.containsKey("entities")) {
                                final JSONObject entities = resultObj.getJSONArray("entities").getJSONObject(0);
                                final String resultUsername = entities.getString("username");
                                log.info(String.format("username and resultUsername is, param username = %s ,param Nickname = %s, resultUsername = %s", nicknameParams.getUsername(), nicknameParams.getNickname(), resultUsername));
                                return Mono.empty();
                            }
                            throw new RuntimeException("接口结果格式错误，结果为: " + resultObj.toJSONString());
                        });
                return tokenFailRetry(addErrorHandler(resultMono, Object.class), () -> updateUserNickname(params));
            });
        }
        throw new IllegalArgumentException("params type not is UpdateNicknameParams");
    }


}
