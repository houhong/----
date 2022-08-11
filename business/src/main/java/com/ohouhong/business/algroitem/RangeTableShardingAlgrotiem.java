package com.ohouhong.business.algroitem;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * @program: algorithm-work
 * @description:  范围查询算法
 * @author: houhong
 * @create: 2022-08-11 20:27
 **/
@Component
public class RangeTableShardingAlgrotiem implements RangeShardingAlgorithm<Long> {



    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {


        //select * from course where cid between 1 and 100;
        //100
        Long upperVal = rangeShardingValue.getValueRange().upperEndpoint();
        //1
        Long lowerVal = rangeShardingValue.getValueRange().lowerEndpoint();

        String logicTableName = rangeShardingValue.getLogicTableName();

        return Arrays.asList(logicTableName+"_1",logicTableName+"_2");
    }
}