package com.zynn.service.module.user.util;

import com.zynn.common.core.constant.EsIndexConstant;
import com.zynn.common.core.utils.ObjectUtil;
import com.zynn.common.pojo.dto.user.UserDTO;
import com.zynn.common.pojo.vo.UserEsVO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 索引工具
 *
 * @author wangyulin
 * @date 2019年3月14日14:58:50
 */
public class UserIndexUtil {

    /**
     * 处理
     * List<EventDTO>
     *
     * @param client
     * @param searchSourceBuilder
     * @return
     */
    public static List<UserDTO> eventDTOList(RestHighLevelClient client, SearchSourceBuilder searchSourceBuilder) {
        //1. 明确查询的索引
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices("user_index");
        List<UserDTO> result = new ArrayList<>();
        try {
            System.out.println(searchRequest.toString());
            //2. 执行查询
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //3. 转换查询的数据
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> result.add(convertToEventDTO(hit.getSourceAsMap())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
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
            e.printStackTrace();
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

    /**
     * 转换数据-将Map对象转换成EventDTO
     *
     * @param source
     * @return
     */
    public static UserDTO convertToEventDTO(Map<String, Object> source) {
        return new UserDTO()
                .setId(ObjectUtil.objectToLong(source.get(EsIndexConstant.UserIndex.Properties.USER_ID)))
                .setUserNickName(ObjectUtil.objectToString(source.get(EsIndexConstant.UserIndex.Properties.NICK_NAME)))
                .setUserSex(ObjectUtil.objectToInteger(source.get(EsIndexConstant.UserIndex.Properties.SEX)))
                .setUserPic(ObjectUtil.objectToString(source.get(EsIndexConstant.UserIndex.Properties.HEAD_PIC)));
    }
}
