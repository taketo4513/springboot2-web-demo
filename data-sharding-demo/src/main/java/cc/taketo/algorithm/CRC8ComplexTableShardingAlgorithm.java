package cc.taketo.algorithm;

import cc.taketo.util.CRC8Util;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

public class CRC8ComplexTableShardingAlgorithm implements ComplexKeysShardingAlgorithm {

    private Properties props;

    private final int SUB_TABLE_NUM = 4; // 分表数量

    private final String CREATE_TIME = "create_time";

    @Override
    public Collection<String> doSharding(Collection collection, ComplexKeysShardingValue complexKeysShardingValue) {
        // 获取分片键
        Map<String, Collection> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();

        // 精确分片
        if (!CollectionUtils.isEmpty(columnNameAndShardingValuesMap)) {
            // 获取分片键的值
            Collection<LocalDateTime> createTimeList = columnNameAndShardingValuesMap.get(CREATE_TIME);
            // HASH_MOD分片键
            Collection shardKeyList = null;

            if (CollectionUtils.isEmpty(createTimeList) || columnNameAndShardingValuesMap.size() > 1) {
                // 移除时间分片键
                columnNameAndShardingValuesMap.remove(CREATE_TIME);
                // 获取HASH_MOD分片键
                shardKeyList = columnNameAndShardingValuesMap.entrySet().iterator().next().getValue();
            }

            if (!CollectionUtils.isEmpty(createTimeList) && !CollectionUtils.isEmpty(shardKeyList)) {
                Collection<String> tableNames = getTableNameByCRC8(complexKeysShardingValue, createTimeList, shardKeyList);
                return CollectionUtils.isEmpty(tableNames) ? collection : tableNames;
            }

            if (!CollectionUtils.isEmpty(createTimeList)) {
                Collection<String> tableNames = getTableNamesByCreatTimeList(complexKeysShardingValue, createTimeList);
                return CollectionUtils.isEmpty(tableNames) ? collection : tableNames;
            }

            if (!CollectionUtils.isEmpty(shardKeyList)) {
                Collection<String> tableNames = getTableNamesByShardKeyList(complexKeysShardingValue, shardKeyList);
                return CollectionUtils.isEmpty(tableNames) ? collection : tableNames;
            }
        }
        // 全表扫描
        return collection;
    }

    private Collection<String> getTableNameByCRC8(ComplexKeysShardingValue complexKeysShardingValue, Collection<LocalDateTime> createTimeCollection, Collection shardKeyCollection) {
        // 获取逻辑表名
        String logicTableName = complexKeysShardingValue.getLogicTableName();

        List<String> tableNames = new ArrayList<>();

        if (createTimeCollection instanceof List && shardKeyCollection instanceof List) {
            // 转为List
            List<LocalDateTime> createTimeList = (List<LocalDateTime>) createTimeCollection;
            List<Object> shardKeyList = (List<Object>) shardKeyCollection;

            // 获取分表键值
            if (createTimeList.size() == shardKeyList.size()) {
                for (int i = 0; i < createTimeList.size(); i++) {
                    // 获取月份
                    int currentMonth = createTimeList.get(i).getMonthValue();
                    String shardKey = String.valueOf(shardKeyList.get(i));
                    String templateMonth = String.format("%02d", currentMonth);
                    // 对分片键 CRC8取模
                    int tableSuffix = CRC8Util.calculateCRC8(shardKey.getBytes()) % SUB_TABLE_NUM;
                    String tableName = logicTableName.concat("_").concat(templateMonth).concat("_0").concat(String.valueOf(tableSuffix));
                    tableNames.add(tableName);
                }
            }
        }

        return tableNames;
    }

    private Collection<String> getTableNamesByCreatTimeList(ComplexKeysShardingValue complexKeysShardingValue, Collection<LocalDateTime> createTimeList) {
        // 获取逻辑表名
        String logicTableName = complexKeysShardingValue.getLogicTableName();

        List<String> tableNames = new ArrayList<>();

        createTimeList.forEach(createTime -> {
            // 计算分表索引
            int currentMonth = createTime.getMonthValue();
            String templateMonth = String.format("%02d", currentMonth);
            for (int i = 0; i < SUB_TABLE_NUM; i++) {
                int tableSuffix = i;
                tableNames.add(logicTableName.concat("_").concat(templateMonth).concat("_0").concat(String.valueOf(tableSuffix)));
            }
        });

        return tableNames;
    }

    private Collection<String> getTableNamesByShardKeyList(ComplexKeysShardingValue complexKeysShardingValue, Collection shardKeyList) {
        // 获取逻辑表名
        String logicTableName = complexKeysShardingValue.getLogicTableName();

        List<String> tableNames = new ArrayList<>();

        shardKeyList.forEach(shardKey -> {
            // 对分片键 CRC8取模
            int tableSuffix = CRC8Util.calculateCRC8(String.valueOf(shardKey).getBytes()) % SUB_TABLE_NUM;
            for (int i = 1; i < 12; i++) {
                String templateMonth = String.format("%02d", i);
                tableNames.add(logicTableName.concat("_").concat(templateMonth).concat("_0").concat(String.valueOf(tableSuffix)));
            }
        });

        return tableNames;
    }

    @Override
    public String getType() {
        return "CRC8_COMPLEX_TABLE_TYPE";
    }

    @Override
    public Properties getProps() {
        return this.props;
    }

    @Override
    public void init(Properties properties) {
        this.props = properties;
    }

}