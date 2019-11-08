package com.zynn.service.bridge.service.user;

import com.zynn.common.core.annotation.OperationSub;
import com.zynn.common.core.dto.UserActiveDTO;
import com.zynn.common.core.dto.UserRecallDTO;
import com.zynn.service.bridge.config.ServicePathConstant;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author 袁毅雄
 * @description 访客
 * @date 2019/7/7
 */
@RequestMapping(ServicePathConstant.PATH_V1 + "/visitor")
public interface UserVisitorInfoRemoteService {

    /**
     * 未活跃用户新增访客
     *
     * @param unActiveUser 未活跃用户:key-最后活跃日期，values->未活跃的用户id
     * @return
     */
    @OperationSub(operationMaxNum = 200)
    @PostMapping("/twoRecentVisitors")
    List<UserRecallDTO> twoRecentVisitors(@RequestBody List<UserActiveDTO> recallUsers);
}
