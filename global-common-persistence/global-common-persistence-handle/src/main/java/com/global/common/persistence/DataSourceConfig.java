package com.global.common.persistence;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DataSourceConfig implements TransactionManagementConfigurer {

    @Autowired
    private HikariDataSource hikariDataSource;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        log.debug("配置事务管理, 配置数据源: {}", hikariDataSource);
        return new DataSourceTransactionManager(hikariDataSource);
    }
}
