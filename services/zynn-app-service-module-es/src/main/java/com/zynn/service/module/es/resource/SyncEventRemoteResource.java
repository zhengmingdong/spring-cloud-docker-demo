package com.zynn.service.module.es.resource;

import com.zynn.common.pojo.dto.EventDTO;
import com.zynn.service.bridge.service.es.SyncEventRemoteService;
import com.zynn.service.module.es.service.SyncEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengmingdong
 * @date 2019-03-18 12:08
 */
@RestController
@Slf4j
public class SyncEventRemoteResource implements SyncEventRemoteService {

    @Autowired
    private SyncEventService syncEventService;

    @Override
    public void create(@RequestBody EventDTO eventDTO) {
        log.info("SyncEventService:createEvent , eventId="+eventDTO.getId());
        syncEventService.insertEvent(eventDTO);
    }

    @Override
    public void update(@RequestBody EventDTO eventDTO) {
        log.info("SyncEventService:updateEvent , eventId="+eventDTO.getId());
        syncEventService.updateEvent(eventDTO);
    }

    @Override
    public void  updateLikeCount(Long eventId,boolean updateType) {
        log.info("updateLikeCount, boolean="+updateType+",eventId="+eventId);
        syncEventService.updateLikeCount(eventId,updateType);
    }

    @Override
    public void repairLikeCount(Long eventId, int likeCount) {
        log.info("repairLikeCount, likeCount="+likeCount+",eventId="+eventId);
        syncEventService.repairLikeCount(eventId,likeCount);
    }


    @Override
    public void updateCommentCount(Long eventId,int count) {
        log.info("updateCommentCount, count="+count+",eventId="+eventId);
        syncEventService.updateCommentCount(eventId,count);
    }

}
