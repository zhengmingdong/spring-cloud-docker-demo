package com.zynn.service.module.es.resource;

import com.zynn.common.pojo.dto.zuul.LogInfoDTO;
import com.zynn.service.bridge.service.es.SyncLogRemoteService;
import com.zynn.service.module.es.service.SyncLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhengmingdong
 * @date 2019-03-18 12:08
 */
@Slf4j
@RestController
public class SyncLogRemoteResource implements SyncLogRemoteService {

    @Autowired
    private SyncLogService syncLogService;

    @Override
    public void syncLog(LogInfoDTO logInfoDTO) {
        log.info("syncLog requestId="+logInfoDTO.getRequestId());
        syncLogService.syncOneLog(logInfoDTO);
    }
}
