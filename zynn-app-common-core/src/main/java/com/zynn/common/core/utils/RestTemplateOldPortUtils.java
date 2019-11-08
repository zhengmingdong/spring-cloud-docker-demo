package com.zynn.common.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 访问原1.0接口工具类
 * @author 杨岳
 * @date 2018/9/6 15:29
 */
@Component
public class RestTemplateOldPortUtils {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 1.0点赞
     * @param likeUserId  点赞人id
     * @param eventId      动态id
     */
    public void like(Long likeUserId,Long eventId){
        //点赞
        restTemplate.getForEntity("https://api.zhuiyinanian.com/YinianProject/yinian/" +
                "AttachOrRemoveExpressionByLkNew?type=like&userid="+likeUserId+"&eid="+eventId+"&formID=",String.class);
    }

    /**
     * 回复
     * @param commentUserId  回复人
     * @param commentBeUserId 被回复人
     * @param eventId   动态id
     * @param content  回复内容
     * @param ccid    回复的评论id
     */
    public void reply(Long commentUserId,Long commentBeUserId,Long eventId,String content,Long ccid){
        restTemplate.getForObject("https://api.zhuiyinanian.com/YinianProject/yinian/SendComment1?" +
                "commentUserId="+commentUserId+"&commentedUserId="+commentBeUserId+"&eventId="+eventId+"&content="+content
                +"&formID=&ccid="+ccid,String.class);
    }

    /**
     * 评论
     * @param commentUserId  评论人
     * @param eventId     评论动态id
     * @param content     评论内容
     */
    public void comment(Long commentUserId,Long eventId,String content){
        //评论
        restTemplate.getForObject("https://api.zhuiyinanian.com/YinianProject/yinian/SendComment1?" +
                "commentUserId="+commentUserId+"&commentedUserId=10&eventId="+eventId+"&content="+content
                +"&formID=&ccid=0",String.class);
    }
}
