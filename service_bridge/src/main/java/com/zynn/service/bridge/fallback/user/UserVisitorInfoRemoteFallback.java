package com.zynn.service.bridge.fallback.user;

import com.zynn.common.core.dto.UserActiveDTO;
import com.zynn.common.core.dto.UserRecallDTO;
import com.zynn.service.bridge.client.user.UserVisitorInfoRemoteClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 袁毅雄
 * @description 访客
 * @date 2019/7/7
 */
@Component
@Slf4j
public class UserVisitorInfoRemoteFallback implements FallbackFactory<UserVisitorInfoRemoteClient> {

    @Override
    public UserVisitorInfoRemoteClient create(Throwable throwable) {
        return new UserVisitorInfoRemoteClient() {

            @Override
            public List<UserRecallDTO> twoRecentVisitors(List<UserActiveDTO> recallUsers) {
                log.error("UserVisitorInfoRemoteFallback twoRecentVisitors error ", throwable);
                return Lists.newArrayList();
            }
        };
    }
}
