package com.sharding.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Data
@ConfigurationProperties(prefix = "database0")
@Component
public class DataSource0Config {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String databaseName;

    public DataSource createDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(getUrl());
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        return dataSource;
    }
}
