package cc.taketo.algorithm;

import cc.taketo.util.CRC8Util;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.time.LocalDateTime;
import java.util.Collection;

public class CRC8DatabaseShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        int year = LocalDateTime.now().getYear();
        String prefix = preciseShardingValue.getDataNodeInfo().getPrefix();
        return prefix.concat(String.valueOf(year));
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        return collection;
    }

    @Override
    public void init() {}

    @Override
    public String getType() {
        return "CRC8_DATABASE_TYPE";
    }
}