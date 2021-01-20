package com.global.common.utils.constants.utils;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:49
 * @Version: v1.0
 */
public class DozerMapper extends DozerBeanMapper {


    public <S, T> List<T> map(Collection<S> sources, Class<T> targetClass) {
        return CollectionUtils.isNotEmpty(sources) ? sources.stream().filter(Objects::nonNull)
                .map(s -> map(s, targetClass)).collect(Collectors.toList()) : null;
    }
}
