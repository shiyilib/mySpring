package com.sharding.jdbc.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 用什么列进行分表以及分表算法
 */
@Component
public class TableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {


    @Override
    public String doEqualSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        for (String each:availableTargetNames) {
            if(each.endsWith(shardingValue.getValue()%2+"")){
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());
        for (Long value:shardingValue.getValues()) {
            for (String tableName:availableTargetNames) {
                if(tableName.endsWith(value%2+"")){
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> availableTargetNames, ShardingValue<Long> shardingValue) {
        LinkedHashSet<String> result = new LinkedHashSet<>(availableTargetNames.size());
        Range<Long> range = shardingValue.getValueRange();
        for(Long i = range.lowerEndpoint();i<=range.upperEndpoint();i++){
            for(String each:availableTargetNames){
                if(each.endsWith(String.valueOf(i%2))){
                    result.add(each);
                }
            }
        }
        return result;
    }
}
