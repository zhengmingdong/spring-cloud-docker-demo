package com.zynn.service.module.es.service;

import com.zynn.common.core.constant.EsIndexConstant;
import com.zynn.common.core.utils.ObjectUtil;
import com.zynn.common.pojo.dto.user.UserAddressListDTO;
import com.zynn.common.pojo.dto.user.UserDTO;
import com.zynn.common.pojo.vo.UserEsVO;
import com.zynn.service.module.es.util.RecommandUtil;
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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author wangyulin
 * @description
 * @create 2019年3月8日10:52:10
 **/
@Service
@Slf4j
public class SyncUserService {

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
            log.error("create es index error ",e);
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
            log.error("update es index error ",e);
        }
    }

    /**
     * 同步用戶信息至ES
     *
     * @param userDTO
     */
    public void syncOneUser(UserDTO userDTO) {

        Map<String, Object> jsonMap = new HashMap<>(20);
        jsonMap.put("user_id", userDTO.getId());
        if (StringUtils.isNotBlank(userDTO.getUserNickName())) {
            jsonMap.put("nick_name", userDTO.getUserNickName());
        }
        if (Objects.nonNull(userDTO.getUserSex())) {
            jsonMap.put("sex", userDTO.getUserSex());
        }
        if (StringUtils.isNotBlank(userDTO.getUserPic())) {
            jsonMap.put("head_pic", userDTO.getUserPic());
        }
        if (Objects.nonNull(userDTO.getUserType())) {
            jsonMap.put("user_type", userDTO.getUserType());
        }

        if (RecommandUtil.existsIndex(userDTO.getId(), client, "user_index")) {
            this.updateUserIndex(userDTO, jsonMap);
        } else {
            this.createUserIndex(userDTO, jsonMap);
        }
    }

    private void createUserIndex(UserDTO userDTO, Map<String, Object> jsonMap) {
        //Map会自动转换为JSON格式的文档源
        IndexRequest indexRequest = new IndexRequest("user_index", "_doc", userDTO.getId().toString())
                .source(jsonMap);
        createESIndex(indexRequest);
    }

    private void updateUserIndex(UserDTO userDTO, Map<String, Object> jsonMap) {

        //Map会自动转换为JSON格式的文档源
        UpdateRequest indexRequest = new UpdateRequest("user_index", "_doc", userDTO.getId().toString())
                .doc(jsonMap);
        updateESIndex(indexRequest);
    }


    /**
     * 同步用戶通讯录信息至ES
     *
     * @param userAddressListDTO
     */
    public void syncOneUserAddressList(UserAddressListDTO userAddressListDTO) {

        Map<String, Object> jsonMap = new HashMap<>(20);
        jsonMap.put("cellphone", userAddressListDTO.getUserCellphone());
        jsonMap.put("user_id", userAddressListDTO.getUserId());
        jsonMap.put("cellphone_list", userAddressListDTO.getCellphoneList());

        if (RecommandUtil.existsIndex(userAddressListDTO.getUserId(), client, "cellphone_index")) {
            this.updateUserAddressListIndex(userAddressListDTO, jsonMap);
        } else {
            this.createUserAddressListIndex(userAddressListDTO, jsonMap);
        }
    }

    private void createUserAddressListIndex(UserAddressListDTO userAddressListDTO, Map<String, Object> jsonMap) {
        //Map会自动转换为JSON格式的文档源
        IndexRequest indexRequest = new IndexRequest("cellphone_index", "_doc", userAddressListDTO.getUserCellphone())
                .source(jsonMap);
        createESIndex(indexRequest);
    }

    private void updateUserAddressListIndex(UserAddressListDTO userAddressListDTO, Map<String, Object> jsonMap) {

        //Map会自动转换为JSON格式的文档源
        UpdateRequest indexRequest = new UpdateRequest("cellphone_index", "_doc", userAddressListDTO.getUserCellphone())
                .doc(jsonMap);
        updateESIndex(indexRequest);
    }

    public List<UserEsVO> getUserInfoByIds(Set<Long> ids) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termsQuery("user_id", ids));

        //组装es查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //组装es基础查询条件
        searchSourceBuilder.query(boolQueryBuilder).size(ids.size());

        //请求解析结果
        return userEsVOList(client, searchSourceBuilder);
    }

    /**
     * 处理
     * List<EventDTO>
     *
     * @param client
     * @param searchSourceBuilder
     * @return
     */
    public static List<UserEsVO> userEsVOList(RestHighLevelClient client, SearchSourceBuilder searchSourceBuilder) {
        //1. 明确查询的索引
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("user_index");

        List<UserEsVO> result = new ArrayList<>();
        try {
            System.out.println(searchRequest.toString());
            //2. 执行查询
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //3. 转换查询的数据
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> result.add(convertToUserEsVO(hit.getSourceAsMap())));
        } catch (IOException e) {
            log.error("search es index error ",e);
        }
        return result;
    }


    /**
     * 转换数据-将Map对象转换成EventDTO
     *
     * @param source
     * @return
     */
    public static UserEsVO convertToUserEsVO(Map<String, Object> source) {
        UserEsVO user = new UserEsVO();
        if (source.get(EsIndexConstant.UserIndex.Properties.USER_ID) != null) {
            user.setUserId(ObjectUtil.objectToLong(source.get(EsIndexConstant.UserIndex.Properties.USER_ID)));
        }
        if (source.get(EsIndexConstant.UserIndex.Properties.NICK_NAME) != null) {
            user.setNickName(ObjectUtil.objectToString(source.get(EsIndexConstant.UserIndex.Properties.NICK_NAME)));
        }
        if (source.get(EsIndexConstant.UserIndex.Properties.HEAD_PIC) != null) {
            user.setHeadPic(ObjectUtil.objectToString(source.get(EsIndexConstant.UserIndex.Properties.HEAD_PIC)));
        }
        return user;
    }

}
