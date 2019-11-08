package com.zynn.service.module.user.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户注册切面
 * @Author zhanghao
 * @date 2019/3/24 16:57
 **/
@Aspect
@Component
@Slf4j
public class UserRegisterAspect {


//    //只拦截 UserAuthInfoMapper insert 方法
//    @Pointcut("execution(public * com.zynn.service.module.user.dao.UserAuthInfoMapper.insert(..))")
//    public void userRegister(){}
//
//    /**
//     * 在注册成功之后进行操作
//     * 进行IM 第三方用户的注册
//     * @Author zhanghao
//     * @Date  2019/3/24
//     * @Param [joinPoint, registerFlag]
//     * @return void
//     **/
//    @AfterReturning(value = "userRegister()", returning = "registerFlag")
//    public void  registerAfter(JoinPoint joinPoint, Integer registerFlag){
//    }


}
