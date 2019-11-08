package com.zynn.common.core.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author 袁毅雄
 * @description 初始化环境条件:启动环境不是windows
 * @date 2019/6/28
 */
public class NotWindowsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return !conditionContext.getEnvironment().getProperty("os.name").contains("Windows");
    }
}
