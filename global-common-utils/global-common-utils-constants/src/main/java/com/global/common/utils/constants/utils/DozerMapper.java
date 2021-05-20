package com.global.common.utils.constants.utils;

import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:49
 * @Version: v1.0
 */
public class DozerMapper extends DozerBeanMapper {


    public <S, T> List<T> map(Collection<S> sources, Class<T> targetClass) {
        return VerifyProxyUtil.isNotEmpty(sources) ? sources.stream().filter(VerifyProxyUtil::nonNull)
                .map(s -> map(s, targetClass)).collect(Collectors.toList()) : null;
    }
}
