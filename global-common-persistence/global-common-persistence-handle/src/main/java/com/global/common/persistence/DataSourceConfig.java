package com.global.common.persistence;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-31 下午 04:49
 * @Version: v1.0
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DataSourceConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        log.info("配置事务管理, 配置数据源: {}", dataSource);
        return new DataSourceTransactionManager(dataSource);
    }
}
