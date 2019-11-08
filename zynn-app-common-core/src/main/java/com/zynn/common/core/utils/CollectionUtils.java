package com.zynn.common.core.utils;

import java.util.*;
import java.util.stream.Stream;

/**
 * 集合工具类
 *
 * @author 杨岳
 * @date 2018/7/2 18:50
 */
public class CollectionUtils {

    /**
     * 统计工具类
     *
     * @param list  需要统计map
     * @param field 统计用的集合对象参数名
     * @return
     */
    public static Map<String, Integer> statistics(List<Map<String, Object>> list, String field) {
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> map : list) {
            String key = map.get(field).toString();
            if (result.containsKey(key)) {
                Integer integer = result.get(key);
                result.put(key, integer + 1);
            } else {
                result.put(key, 1);
            }
        }
        return result;
    }

    /**
     * 使用 Map按value进行排序
     *
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();
        st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }


    /**
     * 比较两个list 是否完全一致
     * 未使用比较器
     */
    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size()) {
            return false;
        }
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }
        return true;
    }
}