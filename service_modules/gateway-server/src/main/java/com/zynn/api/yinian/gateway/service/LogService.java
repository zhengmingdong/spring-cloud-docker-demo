package com.zynn.api.yinian.gateway.service;

import com.zynn.api.yinian.gateway.bean.LogInfo;

public interface LogService {


    /**
     * 保存日志信息
     * @param logInfo
     */
    public void saveLog(LogInfo logInfo);

}
