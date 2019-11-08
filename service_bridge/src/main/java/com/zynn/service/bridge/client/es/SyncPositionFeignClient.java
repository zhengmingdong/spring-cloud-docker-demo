package com.zynn.service.bridge.client.es;

import com.zynn.service.bridge.config.annotation.ESClient;
import com.zynn.service.bridge.service.es.SyncPositionRemoteService;

@ESClient
public interface SyncPositionFeignClient extends SyncPositionRemoteService {
}
