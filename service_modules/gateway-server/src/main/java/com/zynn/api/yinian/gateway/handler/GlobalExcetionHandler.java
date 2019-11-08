package com.zynn.api.yinian.gateway.handler;

import com.zynn.api.yinian.gateway.bean.LogInfo;
import com.zynn.api.yinian.gateway.constant.ProfilesConstant;
import com.zynn.api.yinian.gateway.excetions.AuthFailException;
import com.zynn.api.yinian.gateway.excetions.MethodNotAllowedException;
import com.zynn.api.yinian.gateway.excetions.ShareLoseEfficacyException;
import com.zynn.api.yinian.gateway.excetions.SingleLoginException;
import com.zynn.api.yinian.gateway.result.Result;
import com.zynn.api.yinian.gateway.result.ResultEnum;
import com.zynn.api.yinian.gateway.service.LogService;
import com.zynn.api.yinian.gateway.utils.LogInfoUtil;
import com.zynn.api.yinian.gateway.utils.SendMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

/**
 * @classDesc: 统一异常处理,参考{@link org.springframework.web.server.AbstractErrorWebExceptionHandler}修改
 * @author: zhanghao
 * @createTime: 2019/3/21
 */
public class GlobalExcetionHandler implements ErrorWebExceptionHandler {




    @Value("${spring.profiles.active}")
    private String active;


    @Resource(name = "esLogService")
    private LogService logService;



    private static final Logger log = LoggerFactory.getLogger(GlobalExcetionHandler.class);

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private ThreadLocal<Result> exceptionHandlerResult = new ThreadLocal<>();



    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        /**
         * 按照异常类型进行处理
         */
        int code;
        String body;


        if (ex instanceof AuthFailException){
            code = ResultEnum.UN_AUTH.getCode();
            body = "登录失效，请重新登录";
        }else  if (ex instanceof MethodNotAllowedException){
            code = ResultEnum.METHOD_NOT_ALLOWED.getCode();
            body = ResultEnum.METHOD_NOT_ALLOWED.getMessage();
        }else  if (ex instanceof SingleLoginException){
            code = ResultEnum.UN_SINGLE_LOGIN.getCode();
            body = ResultEnum.UN_SINGLE_LOGIN.getMessage();
        }else  if (ex instanceof ShareLoseEfficacyException){
            code = ResultEnum.SHARE_LOSE_EFFICACY.getCode();
            body = ResultEnum.SHARE_LOSE_EFFICACY.getMessage();
        } else if (ex instanceof NotFoundException) {
            code = HttpStatus.NOT_FOUND.value();
            body = "Service Not Found";
        }else if(ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            code = responseStatusException.getStatus().value();
            body = responseStatusException.getMessage();
            log.error("server error",ex);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));
            final LogInfo logInfo = LogInfoUtil.logInfoForWeb(exchange);
            logInfo.setResponseStatusCode(code);
            logInfo.setResponseData(sw.getBuffer().toString());
            if (active.equals(ProfilesConstant.PRO_ACTIVE)){
                // 日志记录
                ServerHttpRequest request = exchange.getRequest();

                SendMsgUtils.sendDingtalkMsg(String.format("[接口报错]请求路径:%s,日志信息:%s",request.getPath(),logInfo.toString()));
            }
        }else{
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            body ="Internal Server Error";
            log.error("server error",ex);
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw, true));
            final LogInfo logInfo = LogInfoUtil.logInfoForWeb(exchange);
            logInfo.setResponseStatusCode(code);
            logInfo.setResponseData(sw.getBuffer().toString());
            if (active.equals(ProfilesConstant.PRO_ACTIVE)){
                ServerHttpRequest request = exchange.getRequest();
                SendMsgUtils.sendDingtalkMsg(String.format("[接口报错]请求路径:%s,异常信息:%s",request.getPath(),logInfo.toString()));
            }
        }

        Result result = new Result();
        result.setCode(code);
        result.setMsg(body);



        /**
         * 参考AbstractErrorWebExceptionHandler
         */
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionHandlerResult.set(result);

        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }



    /**
     * 参考DefaultErrorWebExceptionHandler
     * @param request
     * @return
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Result result = exceptionHandlerResult.get();
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result));
    }


    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param exchange
     * @param response
     * @return
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GlobalExcetionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GlobalExcetionHandler.this.viewResolvers;
        }

    }

}
