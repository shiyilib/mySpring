package com.sharding.jdbc.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;
import com.sharding.jdbc.config.DataSource1Config;
import com.sharding.jdbc.config.DataSource0Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 用什么列进行分库以及分库算法
 * 一般是用具体值对2取余判断入哪个库 这里采用判断值是否大于20
 */
@Component
public class DatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {

    @Autowired
    private DataSource1Config dataSource1Config;
    @Autowired
    private DataSource0Config dataSource0Config;

    private static final Long compareValue = 20L;

    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        if(shardingValue.getValue() <= compareValue){
            return dataSource0Config.getDatabaseName();
        }
        return dataSource1Config.getDatabaseName();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        LinkedHashSet<String> result = new LinkedHashSet<>(availableTargetNames.size());
        for (Long value : shardingValue.getValues()) {
            if (value <= 20L) {
                result.add(dataSource0Config.getDatabaseName());
            } else {
                result.add(dataSource1Config.getDatabaseName());
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for (Long value = range.lowerEndpoint(); value <= range.upperEndpoint(); value++) {
            if (value <= 20L) {
                result.add(dataSource0Config.getDatabaseName());
            } else {
                result.add(dataSource1Config.getDatabaseName());
            }
        }
        return result;
    }
}
