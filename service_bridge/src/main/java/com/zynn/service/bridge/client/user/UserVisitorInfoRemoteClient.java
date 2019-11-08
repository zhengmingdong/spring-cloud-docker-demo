package com.zynn.service.bridge.client.user;

import com.zynn.service.bridge.config.annotation.UserClient;
import com.zynn.service.bridge.fallback.user.UserVisitorInfoRemoteFallback;
import com.zynn.service.bridge.service.user.UserVisitorInfoRemoteService;

/**
 * @author 袁毅雄
 * @description 访客
 * @date 2019/7/7
 */
@UserClient(fallbackFactory = UserVisitorInfoRemoteFallback.class)
public interface UserVisitorInfoRemoteClient extends UserVisitorInfoRemoteService {
}
