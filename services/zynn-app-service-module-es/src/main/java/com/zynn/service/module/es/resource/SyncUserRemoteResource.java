package com.zynn.service.module.es.resource;

import com.zynn.common.pojo.dto.user.UserAddressListDTO;
import com.zynn.common.pojo.dto.user.UserDTO;
import com.zynn.common.pojo.vo.UserEsVO;
import com.zynn.service.bridge.service.es.SyncUserRemoteService;
import com.zynn.service.module.es.service.SyncUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author zhengmingdong
 * @date 2019-03-18 12:08
 */
@RestController
@Slf4j
public class SyncUserRemoteResource implements SyncUserRemoteService {

    @Autowired
    private SyncUserService syncUserService;

    @Override
    public void syncUserInfo(UserDTO userDTO) {
        log.info("syncUserInfo requestId="+userDTO.getId());
        syncUserService.syncOneUser(userDTO);
    }

    @Override
    public void syncUserCellPhoneInfo(UserAddressListDTO userCellPhoneDTO) {
        log.info("syncUserCellPhoneInfo requestId="+userCellPhoneDTO.getUserId());
        syncUserService.syncOneUserAddressList(userCellPhoneDTO);
    }

    @Override
    public List<UserEsVO> getUserInfoByIds(Set<Long> ids) {
        return syncUserService.getUserInfoByIds(ids);
    }
}
