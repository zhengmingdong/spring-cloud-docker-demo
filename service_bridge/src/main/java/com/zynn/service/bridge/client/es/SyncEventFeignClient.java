package com.zynn.service.bridge.client.es;

import com.zynn.service.bridge.config.annotation.ESClient;
import com.zynn.service.bridge.service.es.SyncEventRemoteService;

@ESClient
public interface SyncEventFeignClient extends SyncEventRemoteService {
}
