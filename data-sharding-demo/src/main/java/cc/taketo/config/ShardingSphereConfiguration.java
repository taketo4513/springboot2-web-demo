package cc.taketo.config;

import cc.taketo.algorithm.SnowflakeWorkId;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

//@Configuration
public class ShardingSphereConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ShardingSphereConfiguration.class);

    private final String PREFIX_SNOW = "snowflake:work_id";

    private final Integer DATA_SIZE = 1024;

    private final String PROP_STRATEGY = "strategy";

    private final String PROP_ALG_CLASS_NAME = "algorithm-class-name";

    private final String PROP_WORK_ID = "worker-id";

    private final String PROP_MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = "max-tolerate-time-difference-milliseconds";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return ShardingSphereDataSourceFactory.createDataSource(
                getModeConfig(),
                getDataSourceMap(),
                getCollectionRuleConfiguration(),
                getProperties());
    }

    /**
     * 构建运行模式
     *
     * @return
     */
    private ModeConfiguration getModeConfig() {
        // 内存模式
//        return new ModeConfiguration("Memory", null, true);

        // 单机模式
        Properties properties = new Properties();
        properties.put("path", ".shardingsphere/");

        return new ModeConfiguration("Standalone",
                new StandalonePersistRepositoryConfiguration(
                        "File",
                        properties
                ),
                true);
    }

    /**
     * 构建数据源
     *
     * @return
     */
    private Map<String, DataSource> getDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置第 1 个数据源
        HikariDataSource dataSource2023 = new HikariDataSource();
        dataSource2023.setDriverClassName("org.postgresql.Driver");
        dataSource2023.setJdbcUrl("jdbc:postgresql://10.0.10.123:5432/postgres?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
        dataSource2023.setUsername("user");
        dataSource2023.setPassword("1q2w3e!Q@W#E");
        dataSourceMap.put("ds2023", dataSource2023);

        // 配置第 2 个数据源
        HikariDataSource dataSource2024 = new HikariDataSource();
        dataSource2024.setDriverClassName("org.postgresql.Driver");
        dataSource2024.setJdbcUrl("jdbc:postgresql://10.0.10.123:5432/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
        dataSource2024.setUsername("user");
        dataSource2024.setPassword("1q2w3e!Q@W#E");
        dataSourceMap.put("ds2024", dataSource2024);

        return dataSourceMap;
    }

    /**
     * 获取分库全部规则配置
     *
     * @return
     */
    private Collection<RuleConfiguration> getCollectionRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfiguration = getShardingRuleConfiguration();
        return Collections.singletonList(shardingRuleConfiguration);
    }

    /**
     * 构建参数
     *
     * @return
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("sql-show", "true");
        return properties;
    }

    /**
     * 创建分片规则配置
     *
     * @return
     */
    private ShardingRuleConfiguration getShardingRuleConfiguration() {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        // 设置逻辑表信息
        shardingRuleConfiguration.getTables().add(getUserTableRuleConfiguration());
        shardingRuleConfiguration.getTables().add(getOrderTableRuleConfiguration());
        shardingRuleConfiguration.getTables().add(getLogTableRuleConfiguration());
        // 设置绑定表
//        shardingRuleConfiguration.getBindingTableGroups().add("t_order,t_user");
        // 设置默认分库策略
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("create_time", "alg-database_crc8"));
        // 分布式主键算法，雪花算法
        Map<String, ShardingSphereAlgorithmConfiguration> keyGenerators = new HashMap<>(1);
        keyGenerators.put("key-gen-snowflake", getKeyGenSnowflakeAlgorithmConfiguration());
        shardingRuleConfiguration.setKeyGenerators(keyGenerators);
        // 分片算法
        Map<String, ShardingSphereAlgorithmConfiguration> shardingAlgorithm = new HashMap<>(3);
        // 分库算法
        shardingAlgorithm.put("alg-database_crc8", getDatabaseCrc8AlgorithmConfiguration());
        // 分表算法
        shardingAlgorithm.put("alg-standard-table_crc8", getStandardTableCrc8AlgorithmConfiguration());
        shardingAlgorithm.put("alg-complex-table_crc8", getComplexTableCrc8AlgorithmConfiguration());
        shardingRuleConfiguration.setShardingAlgorithms(shardingAlgorithm);

        return shardingRuleConfiguration;
    }

    /**
     * 逻辑表配置 t_user
     *
     * @return
     */
    private ShardingTableRuleConfiguration getUserTableRuleConfiguration() {
        ShardingTableRuleConfiguration shardingTableRuleConfiguration = new ShardingTableRuleConfiguration(
                "t_user",
                "ds202$->{3..4}.t_user_0$->{0..3}");
        shardingTableRuleConfiguration.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "key-gen-snowflake"));
        shardingTableRuleConfiguration.setTableShardingStrategy(new StandardShardingStrategyConfiguration("id", "alg-standard-table_crc8"));
        return shardingTableRuleConfiguration;
    }

    /**
     * 逻辑表配置 t_order
     *
     * @return
     */
    private ShardingTableRuleConfiguration getOrderTableRuleConfiguration() {
        ShardingTableRuleConfiguration shardingTableRuleConfiguration = new ShardingTableRuleConfiguration(
                "t_order",
                "ds202$->{3..4}.t_order_0$->{0..3}");
        shardingTableRuleConfiguration.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "key-gen-snowflake"));
        shardingTableRuleConfiguration.setTableShardingStrategy(new StandardShardingStrategyConfiguration("id", "alg-standard-table_crc8"));
        return shardingTableRuleConfiguration;
    }

    /**
     * 逻辑表配置 t_log_info
     *
     * @return
     */
    private ShardingTableRuleConfiguration getLogTableRuleConfiguration() {
        ShardingTableRuleConfiguration shardingTableRuleConfiguration = new ShardingTableRuleConfiguration(
                "t_log_info",
                "ds202$->{3..4}.t_log_info_0$->{1..9}_0$->{0..7},ds202$->{3..4}.t_log_info_$->{10..12}_0$->{0..7}");
        shardingTableRuleConfiguration.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "key-gen-snowflake"));
        shardingTableRuleConfiguration.setTableShardingStrategy(new ComplexShardingStrategyConfiguration("id,create_time", "alg-complex-table_crc8"));
        return shardingTableRuleConfiguration;
    }

    /**
     * 分布式主键算法 key-gen-snowflake
     *
     * @return
     */
    private ShardingSphereAlgorithmConfiguration getKeyGenSnowflakeAlgorithmConfiguration() {
        Properties properties = new Properties();
        Integer workerId = null;
        try {
            SnowflakeWorkId snowflakeWorkId = calculateWorkId(redisTemplate);
            workerId = snowflakeWorkId.getWorkerId();
            logger.info("成功获取雪花算法工作机器ID, workerId: {}", workerId);
        } catch (Exception e) {
            logger.error("雪花算法工作ID获取失败, 请检查Redis是否正常运行！");
            throw new RuntimeException();
        }
        properties.put(PROP_WORK_ID, workerId);
        properties.put(PROP_MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS, 5);
        return new ShardingSphereAlgorithmConfiguration("SNOWFLAKE", properties);
    }

    /**
     * 分库算法 alg-database_crc8
     *
     * @return
     */
    private ShardingSphereAlgorithmConfiguration getDatabaseCrc8AlgorithmConfiguration() {
        Properties properties = new Properties();
        properties.put(PROP_STRATEGY, "STANDARD");
        properties.put(PROP_ALG_CLASS_NAME, "cc.taketo.algorithm.CRC8DatabaseShardingAlgorithm");
        return new ShardingSphereAlgorithmConfiguration("CRC8_STANDARD_DATABASE_TYPE", properties);
    }

    /**
     * 分表算法 alg-standard-table_crc8
     *
     * @return
     */
    private ShardingSphereAlgorithmConfiguration getStandardTableCrc8AlgorithmConfiguration() {
        Properties properties = new Properties();
        properties.put(PROP_STRATEGY, "STANDARD");
        properties.put(PROP_ALG_CLASS_NAME, "cc.taketo.algorithm.CRC8StandardTableShardingAlgorithm");
        return new ShardingSphereAlgorithmConfiguration("CRC8_STANDARD_TABLE_TYPE", properties);
    }

    /**
     * 分表算法 alg-complex-table_crc8
     *
     * @return
     */
    private ShardingSphereAlgorithmConfiguration getComplexTableCrc8AlgorithmConfiguration() {
        Properties properties = new Properties();
        properties.put(PROP_STRATEGY, "COMPLEX");
        properties.put(PROP_ALG_CLASS_NAME, "cc.taketo.algorithm.CRC8ComplexTableShardingAlgorithm");
        return new ShardingSphereAlgorithmConfiguration("CRC8_COMPLEX_TABLE_TYPE", properties);
    }

    /**
     * 获取WorkId
     *
     * @param redisTemplate
     * @return
     */
    private SnowflakeWorkId calculateWorkId(RedisTemplate redisTemplate) {
        String cache = (String) redisTemplate.opsForValue().get(PREFIX_SNOW);

        if (StrUtil.isEmpty(cache)) {
            // 初始化
            SnowflakeWorkId snowflakeWorkId = new SnowflakeWorkId(System.currentTimeMillis(), 0);
            List<SnowflakeWorkId> workIdlist = new ArrayList<>(1);
            workIdlist.add(snowflakeWorkId);

            // 放入缓存
            redisTemplate.opsForValue().set(PREFIX_SNOW, JSON.toJSONString(workIdlist));

            return snowflakeWorkId;
        } else {
            List<SnowflakeWorkId> workIdlist = JSON.parseArray(cache, SnowflakeWorkId.class);
            // 排序，保证list中的数据，根据时间戳从小到大排布
            Collections.sort(workIdlist);

            // 节点数据还没用完
            if (workIdlist.size() < DATA_SIZE) {
                // 当前最后一个节点
                SnowflakeWorkId snowflakeWorkId = workIdlist.get(workIdlist.size() - 1);

                // 计算下一个节点
                SnowflakeWorkId nextNode = new SnowflakeWorkId(System.currentTimeMillis(), snowflakeWorkId.getWorkerId() + 1);
                workIdlist.add(nextNode);

                // 写入缓存
                redisTemplate.opsForValue().set(PREFIX_SNOW, JSON.toJSONString(workIdlist));

                return nextNode;
            } else {
                // 计算出目前时间戳最小的那个
                SnowflakeWorkId snowflakeWorkId = workIdlist.get(0);
                // 更新时间戳
                snowflakeWorkId.setTimestamp(System.currentTimeMillis());
                // 排序
                Collections.sort(workIdlist);
                // 写入缓存
                redisTemplate.opsForValue().set(PREFIX_SNOW, JSON.toJSONString(workIdlist));

                return snowflakeWorkId;
            }
        }
    }

}
