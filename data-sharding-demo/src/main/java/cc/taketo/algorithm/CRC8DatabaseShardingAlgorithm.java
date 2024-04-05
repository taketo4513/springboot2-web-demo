package cc.taketo.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Properties;

public class CRC8DatabaseShardingAlgorithm implements StandardShardingAlgorithm<LocalDateTime> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<LocalDateTime> preciseShardingValue) {
        int year = preciseShardingValue.getValue().getYear();
        String prefix = preciseShardingValue.getDataNodeInfo().getPrefix();
        return prefix.concat(String.valueOf(year));
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<LocalDateTime> rangeShardingValue) {
        return collection;
    }

    @Override
    public String getType() {
        return "CRC8_STANDARD_DATABASE_TYPE";
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }
}