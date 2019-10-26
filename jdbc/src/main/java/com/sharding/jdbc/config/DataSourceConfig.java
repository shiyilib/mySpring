package com.sharding.jdbc.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.keygen.DefaultKeyGenerator;
import com.dangdang.ddframe.rdb.sharding.keygen.KeyGenerator;
import com.sharding.jdbc.algorithm.DatabaseShardingAlgorithm;
import com.sharding.jdbc.algorithm.TableShardingAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private DataSource0Config dataSource0Config;

    @Autowired
    private DataSource1Config dataSource1Config;

    @Autowired
    private DatabaseShardingAlgorithm databaseShardingAlgorithm;

    @Autowired
    private TableShardingAlgorithm tableShardingAlgorithm;

    @Bean
    public DataSource getDataSource() throws SQLException{
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException{
        //分库设置
        Map<String,DataSource> dataSourceMap = new HashMap<>(2);
        //添加2个数据库
        dataSourceMap.put(dataSource0Config.getDatabaseName(),dataSource0Config.createDataSource());
        dataSourceMap.put(dataSource1Config.getDatabaseName(),dataSource1Config.createDataSource());
        //设置默认数据库
        DataSourceRule dataSourceRule  = new DataSourceRule(dataSourceMap, dataSource0Config.getDatabaseName());
        TableRule tableRule = TableRule.builder("user_auth").actualTables(Arrays.asList("user_auth_0", "user_auth_1")).dataSourceRule(dataSourceRule).build();
        //分库分表策略
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(Arrays.asList(tableRule))
                .databaseShardingStrategy(new DatabaseShardingStrategy("user_id", databaseShardingAlgorithm))
                .tableShardingStrategy(new TableShardingStrategy("user_id", tableShardingAlgorithm))
                .build();
       return ShardingDataSourceFactory.createDataSource(shardingRule);
    }

    @Bean
    public KeyGenerator keyGenerator(){
        return new DefaultKeyGenerator();
    }

}
