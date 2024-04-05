package cc.taketo.algorithm;

import cc.taketo.util.CRC8Util;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Properties;

public class CRC8StandardTableShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    private static final int SUB_TABLE_NUM = 4; // 分表数量

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        // 对分片键 CRC8取模
        int tableSuffix = CRC8Util.calculateCRC8(CRC8Util.longToBytes(preciseShardingValue.getValue())) % SUB_TABLE_NUM;
        // 获取逻辑表名
        String logicTableName = preciseShardingValue.getLogicTableName();

        return logicTableName.concat("_0").concat(String.valueOf(tableSuffix));
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        return collection;
    }

    @Override
    public String getType() {
        return "CRC8_STANDARD_TABLE_TYPE";
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {
    }
}