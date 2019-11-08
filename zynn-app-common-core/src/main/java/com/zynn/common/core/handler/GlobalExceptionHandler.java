package com.zynn.common.core.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.zynn.common.core.exception.BusinessExcetion;
import com.zynn.common.pojo.result.BaseCodeAndMessageEnum;
import com.zynn.common.pojo.result.CodeAndMessage;
import com.zynn.common.pojo.result.Result;
import com.zynn.common.pojo.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.stream.Stream;

import static com.zynn.common.pojo.result.BaseCodeAndMessageEnum.ASSERT_FAILURE_ERROR;
import static com.zynn.common.pojo.result.BaseCodeAndMessageEnum.NULL_ERROR;

/**
 * 统一异常处理
 *
 * @author yu_chen
 * @date 2018/4/22 13:52
 */
@ControllerAdvice(basePackages = {
        "com.zynn.service.module.user.controller",
        "com.zynn.service.module.system.controller",
        "com.zynn.service.module.es.controller",
        "com.zynn.service.module.event.controller",
        "com.zynn.service.module.activity.controller",
        "com.zynn.service.module.address.controller",
        "com.zynn.service.module.calculate.controller",
        "com.zynn.service.module.reduce.controller",
})
@Slf4j
public class GlobalExceptionHandler  {


    /**
     * 自定义异常处理
     * 自定义异常,属于某些情况下预期的异常，http返回状态为200， result中的code为-1，message为用户可读信息。
     * @param businessExcetion 业务异常
     * @return 失败的result
     */
    @ExceptionHandler(value = BusinessExcetion.class)
    @ResponseBody
    public Result handlerBaseException(BusinessExcetion businessExcetion) {
        final CodeAndMessage codeAndMessage = businessExcetion.getCodeAndMessage();
        log.info("handle businessExcetion , code = {} , message = {}", codeAndMessage.getCode(),codeAndMessage.getMessage());
        return ResultUtil.of(codeAndMessage);
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public Result handlerException(IOException exception) {
        String name=null;
        if (exception   instanceof FileAlreadyExistsException) {
            name = ((FileAlreadyExistsException) exception).getClass().getName();
            log.info(name);
        }
        log.error("",exception);
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            String className = stackTraceElement.getClassName();
            int lineNumber = stackTraceElement.getLineNumber();
            if (StringUtils.isNotBlank(className)&&className.contains("com.zynn")) {
                log.error("异常路径:{},lineNumber:{}",className,lineNumber);
            }
        }
        return ResultUtil.fail(BaseCodeAndMessageEnum.SERVICE_ERROR.getCode(),name);
    }

    /**
    * 处理全局Exception,预期之外的异常， http返回状态为500，message为开发人员可读信息.
    * @Author: 刘天元
    * @Date: 2018/6/1 17:15
    */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handlerException(Exception exception) {
        // 处理动态代理类中抛出的BaseException
        Throwable cause = exception.getCause();
        // 处理Exception
        log.error("handle exception", exception);
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));

//        String address = ServiceInfoUtil.getIp() + ":" + ServiceInfoUtil.getPort();
        return ResultUtil.fail(BaseCodeAndMessageEnum.SERVICE_ERROR.getCode(),sw.getBuffer().subSequence(0,200).toString());
    }

    /**
     * 处理参数校验异常， result中的code为400，message为开发人员可读信息.
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result handleValidationException(ConstraintViolationException e){
        log.error("参数验证失败 : " + e);
        StringBuilder builder = new StringBuilder();
        for(ConstraintViolation<?> s:e.getConstraintViolations()){
            for(Path.Node node : s.getPropertyPath()) {
                if (ElementKind.PARAMETER.equals(node.getKind())) {
                    builder.append(node.getName());
                }
            }
            builder.append(":").append(s.getMessage()+" ");
        }
        return ResultUtil.fail(BaseCodeAndMessageEnum.BAD_REQUEST.getCode() ,builder.toString());
    }

    /**
     * 处理参数校验异常， result中的code为400，message为开发人员可读信息.
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        return ResultUtil.fail(BaseCodeAndMessageEnum.BAD_REQUEST.getCode(), "缺少请求参数 "+e.getParameterName());
    }


    /**
     * @Author zhanghao
     * @Description 拦截断言失败信息
     * @Date  2019/3/20
     * @Param [ex]
     * @return com.zynn.common.pojo.result.Result
     **/
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result handlerIllegalArgumentException(IllegalArgumentException ex){
        log.error("断言失败",ex);
        return ResultUtil.fail(ASSERT_FAILURE_ERROR.getCode(),ex.getMessage());
    }

    /**
     * @Author zhanghao
     * @Description 拦截断言失败信息
     * @Date  2019/3/20
     * @Param [ex]
     * @return com.zynn.common.pojo.result.Result
     **/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result handlerNullPointerException(NullPointerException ex){
        log.error("空指针异常",ex);
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw, true));
        return ResultUtil.fail(NULL_ERROR.getCode(),sw.getBuffer().subSequence(0,200).toString());
    }

    /**
     * 处理参数校验异常， result中的code为400，message为开发人员可读信息.
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证失败 : " + e);
        BindingResult result = e.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append( error.getDefaultMessage()+"\n");
        }
        return ResultUtil.fail(BaseCodeAndMessageEnum.BAD_REQUEST.getCode(), builder.toString());
    }


    /**
     * @Author zhanghao
     * @Description 处理枚举解析异常
     * @Date  2019/4/25
     * @Param [invalidFormatException]
     * @return com.zynn.common.pojo.result.Result
     **/
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleInvalidFormatException(HttpMessageNotReadableException ex){
        log.error("处理枚举解析异常 : " + ex);
        final Throwable cause = ex.getCause();
        if (cause instanceof  InvalidFormatException){
            InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
            final String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            final Object[] enumConstants = invalidFormatException.getTargetType().getEnumConstants();
            final Object value = invalidFormatException.getValue();
            StringBuilder sb = new StringBuilder(fieldName);
            sb.append("cannot be").append(value).append("can only be [");
            Stream.of(enumConstants).forEach(e -> {
                sb.append(e).append(",");
            });
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");
            return ResultUtil.fail(BaseCodeAndMessageEnum.BAD_REQUEST.getCode(),sb.toString());
        }
        return ResultUtil.fail(BaseCodeAndMessageEnum.BAD_REQUEST.getCode(),ex.getMessage());
    }


}
