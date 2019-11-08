package com.zynn.service.module.user.service;

import com.zynn.common.core.service.BaseService;
import com.zynn.service.module.user.entity.UserVisitorInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 访问记录表 服务类
 * </p>
 *
 * @author zhengmingdong
 * @since 2019-04-01
 */
public interface UserVisitorInfoService extends BaseService<UserVisitorInfo> {

    /**
     * 访问个人主页
     *
     * @param visitId   访问用户
     * @param beVisitId 被访问用户
     * @return
     */
    void visit(Long visitId, Long beVisitId);

}
