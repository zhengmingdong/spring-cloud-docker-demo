package com.zynn.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 袁毅雄
 * @description 向量工具
 * @date 2019/3/28
 */
@Slf4j
public final class Vectors {

    /**
     * 定义稀疏向量
     *
     * @param size  向量长度 6
     * @param index 向量索引 [1, 2, 5]
     * @return [0, 1, 1, 0, 0, 1]
     */
    public static int[] sparseVector(int size, int[] index) {
        log.info("定义稀疏向量");
        log.info("向量长度{},向量索引:{}", size, JSONObject.toJSONString(index));
        if (size <= 0 || null == index) {
            return new int[]{};
        }
        int[] sparseVector = new int[size];
        for (int i = 0; i < size; i++) {
            sparseVector[i] = 0;
        }
        for (int i = 0; i < index.length; i++) {
            if (index[i] >= size) {
                continue;
            }
            sparseVector[index[i]] = 1;
        }
        log.info("sparseVector:" + JSONObject.toJSONString(sparseVector));
        return sparseVector;
    }

    /**
     * 稀疏向量-->稠密向量
     *
     * @param sparseVector [0, 1, 1, 0, 0, 1]
     * @return [6, 1, 2, 5]
     */
    public static int[] denseVector(int[] sparseVector) {
        log.info("稀疏向量-->稠密向量");
        log.info("sparseVector:{}", JSONObject.toJSONString(sparseVector));
        if (null == sparseVector || sparseVector.length <= 0) {
            return new int[]{};
        }
        List<Integer> denseIndex = new ArrayList<>();
        for (int i = 0; i < sparseVector.length; i++) {
            if (sparseVector[i] == 1) {
                denseIndex.add(i);
            }
        }
        int[] denseVector = new int[denseIndex.size() + 1];
        denseVector[0] = sparseVector.length;
        for (int i = 0; i < denseIndex.size(); i++) {
            denseVector[i + 1] = denseIndex.get(i);
        }
        log.info("denseVector:{}", JSONObject.toJSONString(denseVector));
        return denseVector;
    }

    /**
     * 稠密向量-->稀疏向量
     *
     * @param denseVector [6, 1, 2, 5]
     * @return [0, 1, 1, 0, 0, 1]
     */
    public static int[] sparseVector(int[] denseVector) {
        log.info("稠密向量-->稀疏向量");
        log.info("denseVector:{}", JSONObject.toJSONString(denseVector));
        if (null == denseVector || denseVector.length <= 0) {
            return new int[]{};
        }
        int[] sparseVector = new int[denseVector[0]];
        for (int i = 1; i < denseVector[0]; i++) {
            sparseVector[i] = 0;
        }
        for (int i = 1; i < denseVector.length; i++) {
            sparseVector[denseVector[i]] = 1;
        }
        log.info("sparseVector:{}", JSONObject.toJSONString(sparseVector));
        return sparseVector;
    }
}
