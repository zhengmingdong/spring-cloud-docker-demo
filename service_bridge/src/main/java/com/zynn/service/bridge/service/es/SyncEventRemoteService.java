package com.zynn.service.bridge.service.es;

import com.zynn.common.pojo.dto.EventDTO;
import com.zynn.service.bridge.config.ServicePathConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhengmigndong
 * @date 2018年7月1日10:55:36
 */
@RequestMapping(ServicePathConstant.PATH_V1 + "/sync/event")
public interface SyncEventRemoteService {

    /**
     * 新增动态
     *
     * @param eventDTO
     * @return
     */
    @PostMapping(value = "/create")
    void create(@RequestBody EventDTO eventDTO);

    /**
     * 更新地理位置
     *
     * @param eventDTO
     * @return
     */
    @PostMapping(value = "/update")
    void update(@RequestBody EventDTO eventDTO);

    /**
     * 更新点赞数
     *
     * @param eventId
     * @param updateType
     */
    @PostMapping(value = "/update/likeCount")
    void updateLikeCount(@RequestParam("eventId") Long eventId,@RequestParam("updateType")boolean updateType);

    /**
     * 更新点赞数
     *
     * @param eventId
     * @param likeCount
     */
    @PostMapping(value = "/repair/likeCount")
    void repairLikeCount(@RequestParam("eventId") Long eventId,@RequestParam("likeCount")int likeCount);

    /**
     * 更新点赞数
     *
     * @param eventId
     * @param count
     */
    @PostMapping(value = "/update/commentId")
    void updateCommentCount(@RequestParam("eventId") Long eventId,@RequestParam("count")int count);

}
