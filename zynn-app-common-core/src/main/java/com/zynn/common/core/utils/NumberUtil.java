package com.zynn.common.core.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * @ClassName NumberUtil
 * @Description TODO
 * @Author 刘猛
 * @Date 2018/10/18 10:04
 **/
public class NumberUtil {

    public static boolean isEmptyNum(Object i) {
        if (Objects.isNull(i)) {
            return true;
        }
        if (i instanceof Integer) {
            Integer temp = (Integer) i;
            if (Objects.nonNull(i) && temp > 0) {
                return false;
            }
        } else if (i instanceof Long) {
            Long temp = (Long) i;
            if (Objects.nonNull(i) && temp > 0) {
                return false;
            }
        } else if (i instanceof Float) {
            Float temp = (Float) i;
            if (Objects.nonNull(i) && temp > 0) {
                return false;
            }
        } else if (i instanceof Double) {
            Double temp = (Double) i;
            if (Objects.nonNull(i) && temp > 0) {
                return false;
            }
        } else {
            throw new RuntimeException("请传入数值类型的参数！");
        }
        return true;
    }


    /**
     * 获取区间的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        int i = random.nextInt(max) % (max - min + 1) + min;
        return i;
    }

    public static void main(String[] args) {
        System.out.println(getRandom(70, 95));
    }

    /**
     * 随机获取n个不同的数值
     *
     * @param size 总数
     * @param cnt  个数
     * @return
     */
    public static int[] ramdomSizeNum(int size, int cnt) {
        int[] result = new int[cnt];
        int percent = size / cnt;
        for (int i = 0; i < cnt; i++) {
            result[i] = getRandom(i * percent, (i + 1) * percent);
        }
        return result;
    }

    /**
     * 获取一个集合n个不重复的下标
     *
     * @param size
     * @param cnt
     * @return
     */
    public static int[] ramdomNumLimitCount(int size, int cnt) {
        if (size <= cnt) {
            cnt = size;
        }
        int[] result = new int[cnt];
        List<Integer> tempList = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            tempList.add(i);
        }
        int k;
        for (int j = 0; j < cnt; j++) {
            k = getRandom(0, tempList.size());
            result[j] = tempList.get(k);
            tempList.remove(k);
        }
        return result;
    }

    /**
     * 指定数组数值随机出先并可以设置概率
     *
     * @param keyChanceMap (key:类型，value:概率)
     * @return
     */
    public static Integer chanceSelect(Map<Integer, Integer> keyChanceMap) {
        if (MapUtils.isEmpty(keyChanceMap)) {
            return null;
        }
        Integer sum = 0;
        for (Integer value : keyChanceMap.values()) {
            sum += value;
        }
        // 从1开始
        Integer rand = new Random().nextInt(sum) + 1;

        for (Map.Entry<Integer, Integer> entry : keyChanceMap.entrySet()) {
            rand -= entry.getValue();
            // 选中
            if (rand <= 0) {
                Integer item = entry.getKey();
                return item;
            }
        }
        return null;
    }

    /**
     * 概率随机获取
     *
     * @param keyChanceMap
     * @return
     */
    public static Integer getRandomType(Map<Integer, Integer> keyChanceMap) {
        return chanceSelect(keyChanceMap);
    }
}

