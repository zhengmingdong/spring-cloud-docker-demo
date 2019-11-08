package com.zynn.service.module.es.service;

import com.zynn.common.pojo.dto.zuul.LogInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author 刘天元
 * @description
 * @create 2018-07-01 10:47
 **/
@Service
@Slf4j
public class SyncLogService {

    @Autowired
    private RestHighLevelClient client;

    public void syncOneLog(LogInfoDTO logInfoDTO) {
        Map<String, Object> jsonMap = new HashMap<>(20);
        jsonMap.put("request_id", logInfoDTO.getRequestId());
        jsonMap.put("user_id", logInfoDTO.getUserId());
        jsonMap.put("jwt", logInfoDTO.getJwt());
        jsonMap.put("request_url", logInfoDTO.getRequestUrl());
        jsonMap.put("method", logInfoDTO.getMethod());
        jsonMap.put("user_agent", logInfoDTO.getUserAgent());
        jsonMap.put("params", logInfoDTO.getParams());
        jsonMap.put("start_time", logInfoDTO.getStartTime());
        jsonMap.put("end_time", logInfoDTO.getEndTime());
        jsonMap.put("time", logInfoDTO.getTime());
        jsonMap.put("create_time", logInfoDTO.getCreateTime());
        jsonMap.put("exception", logInfoDTO.getException());
        jsonMap.put("response_status_code", logInfoDTO.getResponseStatusCode());
        jsonMap.put("redirect_host", logInfoDTO.getRedirectHost());
        jsonMap.put("redirect_port", logInfoDTO.getRedirectPort());
        jsonMap.put("response_data", logInfoDTO.getResponseData());
        jsonMap.put("version", logInfoDTO.getVersion());
        jsonMap.put("source", logInfoDTO.getSource());
        jsonMap.put("imei", logInfoDTO.getImei());
        jsonMap.put("device_info", logInfoDTO.getDeviceInfo());

        //Map会自动转换为JSON格式的文档源
        IndexRequest indexRequest = new IndexRequest("log_index", "_doc")
                .source(jsonMap);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            log.info("indexResponse "+indexResponse.toString());
        } catch (IOException e) {
            log.info("IOException "+e.toString());
        }

    }


}
