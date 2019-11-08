package com.zynn.service.module.es.service;

import com.zynn.common.core.config.redis.RedisKey;
import com.zynn.common.core.constant.EsIndexConstant;
import com.zynn.common.core.constant.RedisLockConstant;
import com.zynn.common.core.utils.ElasticSearchUtil;
import com.zynn.common.core.utils.ObjectUtil;
import com.zynn.common.core.utils.RedisDistributeLock;
import com.zynn.common.pojo.dto.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zhengmingdong
 * @description
 * @create 2019年3月8日10:52:10
 **/
@Service
@Slf4j
public class SyncEventService {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 插入ES信息
     *
     * @param indexRequest
     */
    private void createESIndex(IndexRequest indexRequest) {
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新ES信息
     *
     * @param indexRequest
     */
    private void updateESIndex(UpdateRequest indexRequest) {
        UpdateResponse indexResponse = null;
        try {
            indexResponse = client.update(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 新增动态信息至ES
     *
     * @param eventDTO
     */
    public void insertEvent(EventDTO eventDTO) {
        Map<String, Object> jsonMap = covertDTOToMap(eventDTO);
        this.createEventIndex(eventDTO, jsonMap);
    }

    /**
     * 更新动态信息至ES
     *
     * @param eventDTO
     */
    public void updateEvent(EventDTO eventDTO) {
        Map<String, Object> jsonMap = covertDTOToMap(eventDTO);
        this.updateEventIndex(eventDTO, jsonMap);
    }

    public Map<String, Object> covertDTOToMap(EventDTO eventDTO) {
        Map<String, Object> jsonMap = new HashMap<>(20);
        if (Objects.nonNull(eventDTO.getCommentCount())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.COMMENT_COUNT, eventDTO.getCommentCount());
        }
        if (Objects.nonNull(eventDTO.getLikeCount())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.LIKE_COUNT, eventDTO.getLikeCount());
        }
        if (StringUtils.isNotBlank(eventDTO.getCreateTime())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.CREATE_TIME, Long.valueOf(eventDTO.getCreateTime()));
        }
        if (Objects.nonNull(eventDTO.getSourceType())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.SOURCE_TYPE, eventDTO.getSourceType());
        }
        if (Objects.nonNull(eventDTO.getLatitude()) && Objects.nonNull(eventDTO.getLongitude())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.LATITUDE_LONGITUDE, new GeoPoint(eventDTO.getLatitude(), eventDTO.getLongitude()));
        }
        if (Objects.nonNull(eventDTO.getIsTop())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.IS_TOP, eventDTO.getIsTop());
        }
        if (Objects.nonNull(eventDTO.getShowType())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.SHOW_TYPE, eventDTO.getShowType());
        }
        if (Objects.nonNull(eventDTO.getId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.EVENT_ID, eventDTO.getId());
        }
        if (Objects.nonNull(eventDTO.getUserId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.USER_ID, eventDTO.getUserId());
        }
        if (Objects.nonNull(eventDTO.getIsValid())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.IS_VALID, eventDTO.getIsValid());
        }
        if (StringUtils.isNotBlank(eventDTO.getGambitId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.GAMBIT_ID, eventDTO.getGambitId());
        }
        if (Objects.nonNull(eventDTO.getIsKolEvent())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.IS_KOL, eventDTO.getIsKolEvent());
        }
        if (Objects.nonNull(eventDTO.getPositionId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.POSITION_ID, eventDTO.getPositionId());
        }
        if (Objects.nonNull(eventDTO.getCityId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.CITY_ID, eventDTO.getCityId());
        }
        if (Objects.nonNull(eventDTO.getPicCount())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.PIC_COUNT, eventDTO.getPicCount());
        }
        if (Objects.nonNull(eventDTO.getPositionRank())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.POSITION_RANK, eventDTO.getPositionRank());
        }
        if (Objects.nonNull(eventDTO.getClusterId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.CLUSTER_ID, eventDTO.getClusterId());
        }
        if (Objects.nonNull(eventDTO.getSchoolId())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.SCHOOL_ID, eventDTO.getSchoolId());
        }
        if (Objects.nonNull(eventDTO.getApprovalBlank())) {
            jsonMap.put(EsIndexConstant.EventIndex.Properties.APPROVAL_BLANK, eventDTO.getApprovalBlank());
        }
        return jsonMap;
    }

    /**
     * 更新点赞数
     *
     * @param eventId
     */
    public void updateLikeCount(Long eventId, boolean updateType) {
        RedisKey redisLockKey = new RedisKey(RedisLockConstant.LIKE_COUNT_LOCK, eventId);
        RedisDistributeLock.tryLock(redisLockKey.getRealKey(), 200L, TimeUnit.MILLISECONDS, () -> {
            EventDTO eventDTO = getEventInfo(eventId);
            if (updateType) {
                eventDTO.setLikeCount(eventDTO.getLikeCount() + 1);
            } else {
                eventDTO.setLikeCount(eventDTO.getLikeCount() - 1);
            }
            Map<String, Object> jsonMap = new HashMap<>(1);
            jsonMap.put(EsIndexConstant.EventIndex.Properties.LIKE_COUNT, eventDTO.getLikeCount());
            UpdateRequest indexRequest = new UpdateRequest(EsIndexConstant.EventIndex.INDEX_NAME, EsIndexConstant.EventIndex.INDEX_TYPE, eventDTO.getId().toString()).doc(jsonMap);
            updateESIndex(indexRequest);
        }, () -> {
        });
    }

    /**
     * 修复点赞数
     *
     * @param eventId
     */
    public void repairLikeCount(Long eventId, int likeCount) {
        Map<String, Object> jsonMap = new HashMap<>(1);
        jsonMap.put(EsIndexConstant.EventIndex.Properties.LIKE_COUNT, likeCount);
        UpdateRequest indexRequest = new UpdateRequest(EsIndexConstant.EventIndex.INDEX_NAME, EsIndexConstant.EventIndex.INDEX_TYPE, eventId.toString()).doc(jsonMap);
        updateESIndex(indexRequest);
    }

    /**
     * 更新评论数
     *
     * @param eventId
     */
    public void updateCommentCount(Long eventId, int count) {

        RedisKey redisLockKey = new RedisKey(RedisLockConstant.COMMENT_COUNT_LOCK, eventId);
        RedisDistributeLock.tryLock(redisLockKey.getRealKey(), 200L, TimeUnit.MILLISECONDS, () -> {
            EventDTO eventDTO = getEventInfo(eventId);

            eventDTO.setCommentCount(eventDTO.getCommentCount() + count);

            Map<String, Object> jsonMap = new HashMap<>(1);
            if (eventDTO.getCommentCount() != null) {
                jsonMap.put(EsIndexConstant.EventIndex.Properties.COMMENT_COUNT, eventDTO.getCommentCount());
            }
            UpdateRequest indexRequest = new UpdateRequest(EsIndexConstant.EventIndex.INDEX_NAME, EsIndexConstant.EventIndex.INDEX_TYPE, eventDTO.getId().toString()).doc(jsonMap);
            updateESIndex(indexRequest);
        }, () -> {
        });
    }


    /**
     * 更新评论数
     *
     * @param eventId
     */
    public EventDTO getEventInfo(Long eventId) {

        EventDTO eventDTO = new EventDTO();
        GetRequest getRequest = new GetRequest("event_index", "_doc", eventId.toString());
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            eventDTO = Convert.convertToEventDTO(getResponse.getSourceAsMap());
        } catch (Exception e) {
            log.info("getSingleEvent failed");
        }
        return eventDTO;
    }

    private void createEventIndex(EventDTO eventDTO, Map<String, Object> jsonMap) {
        //Map会自动转换为JSON格式的文档源
        IndexRequest indexRequest = new IndexRequest(EsIndexConstant.EventIndex.INDEX_NAME, EsIndexConstant.EventIndex.INDEX_TYPE, eventDTO.getId().toString()).source(jsonMap);
        createESIndex(indexRequest);
    }

    private void updateEventIndex(EventDTO eventDTO, Map<String, Object> jsonMap) {
        //Map会自动转换为JSON格式的文档源
        UpdateRequest indexRequest = new UpdateRequest(EsIndexConstant.EventIndex.INDEX_NAME, EsIndexConstant.EventIndex.INDEX_TYPE, eventDTO.getId().toString()).doc(jsonMap);
        updateESIndex(indexRequest);
    }

    /**
     * 工具类:数据类型转换
     */
    private static class Convert {
        /**
         * 转换数据-将Map对象转换成EventDTO
         *
         * @param source
         * @return
         */
        private static EventDTO convertToEventDTO(Map<String, Object> source) {
            String latitudeLongitude = ObjectUtil.objectToString(source.get(EsIndexConstant.EventIndex.Properties.LATITUDE_LONGITUDE));
            GeoPoint geoPoint = ElasticSearchUtil.latLonConvert(latitudeLongitude);

            return new EventDTO()
                    .setCommentCount(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.COMMENT_COUNT)))
                    .setLikeCount(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.LIKE_COUNT)))
                    .setUserId(ObjectUtil.objectToLong(source.get(EsIndexConstant.EventIndex.Properties.USER_ID)))
                    .setCreateTime(ObjectUtil.objectToString(source.get(EsIndexConstant.EventIndex.Properties.CREATE_TIME)))
                    .setLocation((ObjectUtil.objectToString(source.get(EsIndexConstant.EventIndex.Properties.LOCATION))))
                    .setSourceType(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.SOURCE_TYPE)))
                    .setShowType(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.SHOW_TYPE)))
                    .setLongitude(Objects.nonNull(geoPoint) ? geoPoint.getLon() : null)
                    .setLatitude(Objects.nonNull(geoPoint) ? geoPoint.getLat() : null)
                    .setPositionId(ObjectUtil.objectToLong(source.get(EsIndexConstant.EventIndex.Properties.POSITION_ID)))
                    .setGambitId(ObjectUtil.objectToString(source.get(EsIndexConstant.EventIndex.Properties.GAMBIT_ID)))
                    .setId(ObjectUtil.objectToLong(source.get(EsIndexConstant.EventIndex.Properties.EVENT_ID)))
                    .setIsValid(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.IS_VALID)))
                    .setIsTop(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.IS_TOP)))
                    .setIsKOL(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.IS_KOL)))
                    .setPositionRank(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.POSITION_RANK)))
                    .setClusterId(ObjectUtil.objectToLong(source.get(EsIndexConstant.EventIndex.Properties.CLUSTER_ID)))
                    .setSchoolId(ObjectUtil.objectToLong(source.get(EsIndexConstant.EventIndex.Properties.SCHOOL_ID)))
                    .setApprovalBlank(ObjectUtil.objectToInteger(source.get(EsIndexConstant.EventIndex.Properties.APPROVAL_BLANK)));
        }
    }
}
