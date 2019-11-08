package com.zynn.service.bridge.service.es;

import com.zynn.common.pojo.dto.event.EventPositionDTO;
import com.zynn.service.bridge.config.ServicePathConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 同步地理位置
 *
 * @author zhengmigndong
 * @date 2018年7月1日10:55:36
 */
@RequestMapping(ServicePathConstant.PATH_V1 + "/sync/position")
public interface SyncPositionRemoteService {

    /**
     * 新增地理位置
     *
     * @param eventPositionDTO
     * @return
     */
    @PostMapping(value = "/create")
    void create(@RequestBody EventPositionDTO eventPositionDTO);

    /**
     * 查询es中地理位置信息是否存在
     *
     * @param eventPositionDTO
     * @return
     */
    @PostMapping(value = "/checkPositionExist")
    List<EventPositionDTO> checkPositionExist(@RequestBody EventPositionDTO eventPositionDTO);

    /**
     * 更新地理位置
     *
     * @param eventPositionDTO
     * @return
     */
    @PostMapping(value = "/update")
    void update(@RequestBody EventPositionDTO eventPositionDTO);

}
