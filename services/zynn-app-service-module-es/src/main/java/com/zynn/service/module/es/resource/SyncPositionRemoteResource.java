package com.zynn.service.module.es.resource;

import com.zynn.common.pojo.dto.event.EventPositionDTO;
import com.zynn.service.bridge.service.es.SyncPositionRemoteService;
import com.zynn.service.module.es.service.SyncPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhengmingdong
 * @date 2019-03-18 12:08
 */
@RestController
public class SyncPositionRemoteResource  implements SyncPositionRemoteService {

    @Autowired
    private SyncPositionService syncPositionService;

    @Override
    public void create(@RequestBody EventPositionDTO eventPositionDTO) {
        syncPositionService.insertOnePosition(eventPositionDTO);
    }

    @Override
    public List<EventPositionDTO> checkPositionExist(@RequestBody EventPositionDTO eventPositionDTO) {
        return syncPositionService.checkPositionExist(eventPositionDTO);
    }

    @Override
    public void update( @RequestBody EventPositionDTO eventPositionDTO) {
        syncPositionService.updateOnePosition(eventPositionDTO);
    }
}
