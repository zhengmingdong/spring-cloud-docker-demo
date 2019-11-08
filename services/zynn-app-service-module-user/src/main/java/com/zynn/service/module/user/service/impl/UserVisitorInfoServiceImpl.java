package com.zynn.service.module.user.service.impl;

import com.zynn.common.core.service.impl.BaseServiceImpl;
import com.zynn.common.core.utils.SequenceUtil;
import com.zynn.service.module.user.dao.UserVisitorInfoMapper;
import com.zynn.service.module.user.entity.UserVisitorInfo;
import com.zynn.service.module.user.service.UserVisitorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * <p>
 * 访问记录表 服务实现类
 * </p>
 * app_data_acquire_201906
 *
 * @author zhengmingdong
 * @since 2019-04-01
 */
@Service
@Slf4j
public class UserVisitorInfoServiceImpl extends BaseServiceImpl<UserVisitorInfoMapper, UserVisitorInfo> implements UserVisitorInfoService {


    @Override
    public void visit(Long visitId, Long beVisitId) {
        UserVisitorInfo visitorInfo = new UserVisitorInfo();
        visitorInfo.setId(SequenceUtil.getId());
        visitorInfo.setVisitUser(visitId);
        visitorInfo.setBeVisitedUser(beVisitId);
        visitorInfo.setCreateUser(visitId.toString());
        visitorInfo.setUpdateUser(visitId.toString());
        Assert.isTrue(this.save(visitorInfo), "save visit fail");
    }

}
