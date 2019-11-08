package com.zynn.service.module.user.task;

import com.zynn.common.core.annotation.TaskLock;
import com.zynn.common.core.condition.NotWindowsCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 用户关系发送短信定时任务
 *
 * @author zhanghao
 * @date 2019/5/22 11:52
 **/
@Component
@Slf4j
@Conditional(NotWindowsCondition.class)
public class UserRelationSendMessageTask {

    public static final String MESSAGE_CONTENT = "你的好友%s等%d位好友邀请你加入duck，快去加入吧: %s";

    public static final Integer LIMIT_LENGTH = 5;



    @Scheduled(cron = "0 0 12 * * ?")
    @TaskLock(leaseTime = 23, unit = TimeUnit.HOURS)
    @Retryable(maxAttempts = 5,backoff = @Backoff(delay = 5000L,multiplier = 3.0))
    public void task() {
    }



}
