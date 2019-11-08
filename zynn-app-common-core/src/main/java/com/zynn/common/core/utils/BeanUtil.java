package com.zynn.common.core.utils;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 对象转换
 * @author 刘天元
 * @create 2018-06-06 14:04
 **/
public class BeanUtil {

    private static Mapper dozerBeanMapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * 转换对象列表
     * @param fromList 来源对象列表
     * @param toClass 目标对象类类型
     * @return
     */
    public static <FROM, TO> List<TO> convert(List<FROM> fromList, final Class<TO> toClass) {
        return fromList
                .stream()
                .map(from -> dozerBeanMapper.map(from, toClass))
                .collect(Collectors.toList());
    }

    /**
     * @param from 来源对象
     * @param toClass 目标对象类类型
     * @return
     */
    public static <FROM, TO> TO convert(FROM from,  Class<TO> toClass) {
        if(from == null) {
            return null;
        }
        return dozerBeanMapper.map(from, toClass);
    }

}
