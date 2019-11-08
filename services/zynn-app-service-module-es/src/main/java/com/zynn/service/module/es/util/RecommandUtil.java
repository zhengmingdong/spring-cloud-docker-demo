package com.zynn.service.module.es.util;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 *@ClassName RecommandUtil
 *@Description TODO
 *@Author wangyulin
 *@Date 2019年3月8日11:55:54
 **/
@Slf4j
public class RecommandUtil {


    /**
     * 判断es是否存在用户索引
     * @param userId
     * @return
     */
    public static boolean existsIndex(Long userId,RestHighLevelClient client,String index){
        GetRequest getRequest = new GetRequest(index, "_doc", userId.toString());
        boolean is = false;
        try {
            is = client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
