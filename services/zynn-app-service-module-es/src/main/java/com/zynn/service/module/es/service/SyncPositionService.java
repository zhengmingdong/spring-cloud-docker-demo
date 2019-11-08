package com.zynn.service.module.es.service;

import com.zynn.common.core.constant.EsIndexConstant;
import com.zynn.common.core.utils.ElasticSearchUtil;
import com.zynn.common.core.utils.ObjectUtil;
import com.zynn.common.pojo.dto.event.EventPositionDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author zhengmingdong
 * @description
 * @create 2019年3月8日10:52:10
 **/
@Service
@Slf4j
public class SyncPositionService {

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
            log.info(indexResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
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
            log.info(indexResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增地理位置信息至ES
     *
     * @param eventPositionDTO
     */
    public void insertOnePosition(EventPositionDTO eventPositionDTO) {
        Map<String, Object> jsonMap = covertDTOToMap(eventPositionDTO);
        this.createPositionIndex(eventPositionDTO, jsonMap);
    }

    /**
     * 更新地理位置信息至ES
     *
     * @param eventPositionDTO
     */
    public void updateOnePosition(EventPositionDTO eventPositionDTO) {
        Map<String, Object> jsonMap = covertDTOToMap(eventPositionDTO);
        this.updatePositionIndex(eventPositionDTO, jsonMap);
    }

    public Map<String, Object> covertDTOToMap(EventPositionDTO eventPositionDTO) {
        Map<String, Object> jsonMap = new HashMap<>(20);
        if (StringUtils.isNotBlank(eventPositionDTO.getCountry())) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.COUNTRY, eventPositionDTO.getCountry());
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getProvince())) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.PROVINCE, eventPositionDTO.getProvince());
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getCity())) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.CITY, eventPositionDTO.getCity());
        }
        if (eventPositionDTO.getWantCount() != null) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.WANT_COUNT, eventPositionDTO.getWantCount());
        }
        if (eventPositionDTO.getComeCount() != null) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.COME_COUNT, eventPositionDTO.getComeCount());
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getLocation())) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.LOCATION, eventPositionDTO.getLocation());
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getPosition())) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.POSITION, eventPositionDTO.getPosition());
        }
        if (eventPositionDTO.getLatitude() != null && eventPositionDTO.getLongitude() != null) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.LATITUDE_LONGITUDE, new GeoPoint(eventPositionDTO.getLatitude(),eventPositionDTO.getLongitude()));
        }
        if (eventPositionDTO.getPositionId() != null) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.POSITION_ID, eventPositionDTO.getPositionId());
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getPositionQuery())) {
            jsonMap.put(EsIndexConstant.PositionIndex.Properties.POSITION_QUERY, eventPositionDTO.getPositionQuery());
        }
        return jsonMap;
    }

    /**
     * 判断地理位置在es中是否已经存在
     *
     * @param eventPositionDTO
     * @return
     */
    public List<EventPositionDTO> checkPositionExist(EventPositionDTO eventPositionDTO) {
        //1. 设置es查询基础条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(eventPositionDTO.getCountry())) {
            boolQueryBuilder.must(QueryBuilders.termsQuery(EsIndexConstant.PositionIndex.Properties.COUNTRY, eventPositionDTO.getCountry()));
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getProvince())) {
            boolQueryBuilder.must(QueryBuilders.termsQuery(EsIndexConstant.PositionIndex.Properties.PROVINCE, eventPositionDTO.getProvince()));
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getCity())) {
            boolQueryBuilder.must(QueryBuilders.termsQuery(EsIndexConstant.PositionIndex.Properties.CITY, eventPositionDTO.getCity()));
        }
        if (StringUtils.isNotBlank(eventPositionDTO.getPosition())) {
            boolQueryBuilder.must(QueryBuilders.termsQuery(EsIndexConstant.PositionIndex.Properties.POSITION, eventPositionDTO.getPosition()));
        }
        //2 组装es查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //2.1 组装es基础查询条件
        searchSourceBuilder.query(boolQueryBuilder);
        //3. 请求解析结果
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices(EsIndexConstant.PositionIndex.INDEX_NAME);
        List<EventPositionDTO> result = new ArrayList<>();
        try {
            // 执行查询
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            searchResponse.getHits();
            //3. 转换查询的数据
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> result.add(Convert.convertToPositionDTO(hit.getSourceAsMap())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private void createPositionIndex(EventPositionDTO eventPositionDTO, Map<String, Object> jsonMap) {
        //Map会自动转换为JSON格式的文档源
        IndexRequest indexRequest = new IndexRequest(EsIndexConstant.PositionIndex.INDEX_NAME, EsIndexConstant.PositionIndex.INDEX_TYPE, eventPositionDTO.getPositionId().toString()).source(jsonMap);
        createESIndex(indexRequest);
    }

    private void updatePositionIndex(EventPositionDTO eventPositionDTO, Map<String, Object> jsonMap) {
        //Map会自动转换为JSON格式的文档源
        UpdateRequest indexRequest = new UpdateRequest(EsIndexConstant.PositionIndex.INDEX_NAME, EsIndexConstant.PositionIndex.INDEX_TYPE, eventPositionDTO.getPositionId().toString()).doc(jsonMap);
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
        private static EventPositionDTO convertToPositionDTO(Map<String, Object> source) {
            String latitudeLongitude = ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.LATITUDE_LONGITUDE));
            GeoPoint geoPoint = ElasticSearchUtil.latLonConvert(latitudeLongitude);

            return new EventPositionDTO()
                    .setCountry(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.COUNTRY)))
                    .setProvince(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.PROVINCE)))
                    .setCity(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.CITY)))
                    .setWantCount(ObjectUtil.objectToInteger(source.get(EsIndexConstant.PositionIndex.Properties.WANT_COUNT)))
                    .setComeCount(ObjectUtil.objectToInteger(source.get(EsIndexConstant.PositionIndex.Properties.COME_COUNT)))
                    .setLocation(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.LOCATION)))
                    .setPosition(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.POSITION)))
                    .setLongitude(Objects.nonNull(geoPoint) ? geoPoint.getLon() : null)
                    .setLatitude(Objects.nonNull(geoPoint) ? geoPoint.getLat() : null)
                    .setLatitudeLongitude(latitudeLongitude)
                    .setPositionId(ObjectUtil.objectToLong(source.get(EsIndexConstant.PositionIndex.Properties.POSITION_ID)))
                    .setPositionQuery(ObjectUtil.objectToString(source.get(EsIndexConstant.PositionIndex.Properties.POSITION_QUERY)));
        }

    }
}
