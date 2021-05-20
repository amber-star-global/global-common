package com.global.common.persistence.distributed;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-05-20 下午 06:04
 * @Version: v1.0
 */
@EnableDistributedTransaction
@Configuration
public class DistributedConfig {

    @Bean
    @ConditionalOnBean(DataSource.class)
    public TomcatDatasourceAspect tomcatDatasourceAspect() {
        return new TomcatDatasourceAspect();
    }
}
