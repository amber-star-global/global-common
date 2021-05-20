package com.global.common.persistence.distributed;

import com.codingapi.txlcn.tc.aspect.weave.DTXResourceWeaver;
import com.codingapi.txlcn.tc.config.TxClientConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import java.sql.Connection;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-05-20 下午 06:07
 * @Version: v1.0
 */
@Aspect
public class TomcatDatasourceAspect implements Ordered {

    //TX-LCN 资源切面处理对象
    @Autowired
    private DTXResourceWeaver dtxResourceWeaver;
    @Autowired
    private TxClientConfig txClientConfig;

    @Around("execution(public java.sql.Connection org.apache.tomcat.jdbc.pool.DataSourceProxy.getConnection(..) )")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return dtxResourceWeaver.getConnection(() -> (Connection) point.proceed());
    }

    @Override
    public int getOrder() {
        return txClientConfig.getResourceOrder();
    }
}
