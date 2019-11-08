package com.zynn.service.bridge.service.es;

import com.zynn.common.pojo.dto.zuul.LogInfoDTO;
import com.zynn.service.bridge.config.ServicePathConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhengmigndong
 * @date 2018年7月1日10:55:36
 */
@RequestMapping(ServicePathConstant.PATH_V1)
public interface SyncLogRemoteService {

    /**
     * 将请求日志写入es
     *
     * @param logInfoDTO
     * @return
     */
    @PostMapping(value = "/sync/log/create")
    void syncLog(@RequestBody LogInfoDTO logInfoDTO);

}
