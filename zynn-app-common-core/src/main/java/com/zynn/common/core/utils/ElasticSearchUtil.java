package com.zynn.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zynn.common.pojo.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author 袁毅雄
 * @description elasticSearch 查询工具
 * @date 2019/3/28
 */
@Slf4j
public abstract class ElasticSearchUtil {

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @param docMap    es文档数据
     * @return
     */
    public static boolean createDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id, final Map<String, Object> docMap) {
        IndexResponse response = null;
        try {
            response = client.index(new IndexRequest(indexName, indexType, Objects.toString(id)).source(docMap), RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("连接ES错误", e);
            throw new RuntimeException(e);
        }
        return Objects.equals(response.status(), RestStatus.OK);
    }

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @param docMap    es文档数据
     * @return
     */
    public static boolean updateDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id, final Map<String, Object> docMap) {
        UpdateResponse response = null;
        try {
            log.info("readyUpdateEs data for id : {}", Objects.toString(id));
            response = client.update(new UpdateRequest(indexName, indexType, Objects.toString(id)).doc(docMap), RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("更新ES错误", e);
            throw new RuntimeException(e);
        }
        return Objects.equals(response.status(), RestStatus.OK);
    }

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @return
     */
    public static boolean deleteDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id) {
        DeleteResponse response = null;
        try {
            response = client.delete(new DeleteRequest(indexName, indexType, Objects.toString(id)), RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("删除ES错误", e);
            throw new RuntimeException(e);
        }
        return Objects.equals(response.status(), RestStatus.OK);
    }

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @return
     */
    public static Map<String, Object> getDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id) {
        GetResponse response = null;
        try {
            response = client.get(new GetRequest(indexName, indexType, Objects.toString(id)), RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        return response.getSource();
    }

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @return
     */
    public static boolean existsDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id) {
        boolean isExists = false;
        try {
            isExists = client.exists(new GetRequest(indexName, indexType, Objects.toString(id)), RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        return isExists;
    }

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @return
     */
    public static boolean existsValidDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id) {
        BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("is_valid", BaseEnum.IS_VALID_YES.getKey()));
        SearchSourceBuilder source = new SearchSourceBuilder().query(query);
        ImmutablePair<SearchResponse, Integer> immutablePair = search(client, indexName, indexType, source);
        return immutablePair.getRight() > 0;
    }

    /**
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param id        es文档id
     * @param docMap    es文档id
     * @return
     */
    public static boolean createOrUpdateDocument(RestHighLevelClient client, final String indexName, final String indexType, final Long id, final Map<String, Object> docMap) {
        boolean response = false;
        boolean isExists = existsDocument(client, indexName, indexType, id);
        if (isExists) {
            response = updateDocument(client, indexName, indexType, id, docMap);
        } else {
            response = createDocument(client, indexName, indexType, id, docMap);
        }
        return response;
    }

    /**
     * 常规查询
     *
     * @param client    es查询的客户端
     * @param indexName es索引库
     * @param indexType es索引类型
     * @param source    es查询条件
     * @return
     */
    public static ImmutablePair<SearchResponse, Integer> search(final RestHighLevelClient client, final String indexName, final String indexType, final SearchSourceBuilder source) {
        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .types(indexType)
                .source(source);

        SearchResponse searchResponse = null;
        try {
            log.info("执行查询");
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        return new ImmutablePair(
                Objects.isNull(searchResponse) ? NumberUtils.INTEGER_ZERO : searchResponse,
                Objects.isNull(searchResponse) ? NumberUtils.INTEGER_ZERO : ((Long) searchResponse.getHits().getTotalHits().value).intValue()
        );
    }

    /**
     * 基础聚合查询
     *
     * @param client
     * @param indexName
     * @param indexType
     * @param source
     * @return
     */
    public static Aggregations searchAggregations(final RestHighLevelClient client, final String indexName, final String indexType, final SearchSourceBuilder source) {
        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .types(indexType)
                .source(source);

        SearchResponse searchResponse = null;
        try {
            log.info("执行查询");
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        return searchResponse.getAggregations();
    }

    /**
     * 基础聚合查询
     *
     * @param client
     * @param indexName
     * @param indexType
     * @param source
     * @return
     */
    public static SearchResponse searchAggregationsResponse(final RestHighLevelClient client, final String indexName, final String indexType, final SearchSourceBuilder source) {
        SearchRequest searchRequest = new SearchRequest()
                .indices(indexName)
                .types(indexType)
                .source(source);

        SearchResponse searchResponse = null;
        try {
            log.info("执行查询");
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        return searchResponse;
    }


    /**
     * scroll分页,优化后的分页,scroll分页,无法聚合，需要自己手动聚合
     *
     * @param client        es查询的客户端
     * @param indexName     es索引库
     * @param indexType     es索引类型
     * @param source        es查询条件
     * @param containerData es数据行->es数据结果集 数据处理所需的容器
     * @param disposeRow    es数据行级处理过程
     * @param disposeResult es数据结果集处理过程
     * @param <Result>      返回的结果类型
     * @param <Container>   处理数据的容器
     * @return
     */
    public static <Result, Container> List<Result> paging(final RestHighLevelClient client, final String indexName, final String indexType, final SearchSourceBuilder source, Supplier<Container> containerData, BiPredicate<Container, Map<String, Object>> disposeRow, Function<Container, List<Result>> disposeResult) {

        //周转数据的容器
        Container container = containerData.get();

        log.info("初始化scroll分页");
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        SearchRequest request = new SearchRequest()
                .indices(indexName)
                .types(indexType)
                .source(source)
                .scroll(scroll);

        SearchResponse response = null;
        try {
            log.info("执行查询");
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        String scrollId = response.getScrollId();
        SearchHit[] searchHits = response.getHits().getHits();

        log.info("遍历搜索命中的数据，直到没有数据");
        sign:
        do {
            if (searchHits != null && searchHits.length > 0) {
                log.info("-----下一页-----");
                for (SearchHit searchHit : searchHits) {
                    log.info(searchHit.getSourceAsString());
                    if (!disposeRow.test(container, searchHit.getSourceAsMap())) {
                        log.info("手动终止scroll分页查询");
                        break sign;
                    }
                }
            }
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            try {
                response = client.searchScroll(scrollRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                log.error("查询ES错误", e);
                throw new RuntimeException(e);
            }
            scrollId = response.getScrollId();
            searchHits = response.getHits().getHits();
        } while (searchHits != null && searchHits.length > 0);


        log.info("清除滚屏");
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询ES错误", e);
            throw new RuntimeException(e);
        }
        boolean succeeded = clearScrollResponse.isSucceeded();
        log.info("succeeded:", succeeded);

        return disposeResult.apply(container);
    }

    /**
     * id集合批量查询
     *
     * @param client
     * @param cloum
     * @param indexName
     * @param ids
     * @return
     */
    public static <T> Map<Long, T> getListByIds(RestHighLevelClient client, String cloum, String indexName, Set<Long> ids, Class<T> claszz, String idClounm) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termsQuery(cloum, ids));

        //组装es查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //组装es基础查询条件
        searchSourceBuilder.query(boolQueryBuilder).size(ids.size());

        //请求解析结果
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        searchRequest.indices(indexName);

        List<JSONObject> result = new ArrayList<>();

        Map<Long, T> resultMap = Maps.newHashMap();

        try {
            System.out.println(searchRequest.toString());
            //2. 执行查询
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            //3. 转换查询的数据
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit -> result.add(JSONObject.parseObject(hit.getSourceAsString())));

            resultMap = result.stream().collect(Collectors.toMap(e -> e.getLong(idClounm), e -> e.toJavaObject(claszz)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultMap;
    }

    public static GeoPoint latLonConvert(String latitudeLongitude) {
        try {
            String[] split = StringUtils.split(latitudeLongitude, ",");
            String lon = split[0];
            String lat = split[1];
            Double longitude = NumberUtils.DOUBLE_ZERO;
            Double latitude = NumberUtils.DOUBLE_ZERO;
            if (latitudeLongitude.contains(":")) {
                longitude = Double.valueOf(lon.substring(lon.indexOf(":") + 1));
                latitude = Double.valueOf(lat.substring(lat.indexOf(":") + 1, lat.length() - 1));
            }
            if (latitudeLongitude.contains("=")) {
                longitude = Double.valueOf(lon.substring(lon.indexOf("=") + 1));
                latitude = Double.valueOf(lat.substring(lat.indexOf("=") + 1, lat.length() - 1));
            }
            GeoPoint geoPoint = new GeoPoint(latitude, longitude);
            return geoPoint;
        } catch (Exception e) {
            log.error("错误经纬度{}", latitudeLongitude);
            return null;
        }
    }

}

