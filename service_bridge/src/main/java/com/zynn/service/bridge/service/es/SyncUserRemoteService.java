package com.zynn.service.bridge.service.es;

import com.zynn.common.pojo.dto.user.UserAddressListDTO;
import com.zynn.common.pojo.dto.user.UserDTO;
import com.zynn.common.pojo.vo.UserEsVO;
import com.zynn.service.bridge.config.ServicePathConstant;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author 王宇林
 * @description 同步用户信息的Feign
 * @date 2019年3月8日13:25:21
 */
@RequestMapping(ServicePathConstant.PATH_V1 + "/sync")
public interface SyncUserRemoteService {

    /**
     * 将用戶信息同步至es
     *
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/user/createOrUpdate")
    void syncUserInfo(@RequestBody UserDTO userDTO);


    /**
     * 同步通讯录至es
     *
     * @param userCellPhoneDTO
     * @return
     */
    @PostMapping(value = "/cellphone/createOrUpdate")
    void syncUserCellPhoneInfo(@RequestBody UserAddressListDTO userCellPhoneDTO);

    /**
     * 批量查询用户信息
     *
     * @param ids
     * @return
     */
    @GetMapping(value = "/getUserInfoByIds")
    List<UserEsVO> getUserInfoByIds(@RequestParam("ids") Set<Long> ids);
}