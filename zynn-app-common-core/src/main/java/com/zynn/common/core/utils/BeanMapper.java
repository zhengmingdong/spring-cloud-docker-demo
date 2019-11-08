package com.zynn.common.core.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BeanMapper {

    private static Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return MAPPER.map(source, destinationClass);
    }

    public static void map(Object source, Object destination) {
        MAPPER.map(source, destination);
    }

    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            destinationList.add(MAPPER.map(sourceObject, destinationClass));
        }
        return destinationList;
    }

}
