package cc.taketo.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


/**
 * @author zhangp
 * @date 2024/04/07 22:49
 */
public class ShardingUtil {

    private static final int SUB_TABLE_NUM = 8; // 分表数量


    public static String getShardingTableNameByComplex(String logic, LocalDateTime createTime, Object shardKey) {
        if (!StringUtils.isEmpty(logic) && !ObjectUtils.isEmpty(createTime) && !ObjectUtils.isEmpty(shardKey)) {
            int currentMonth = createTime.getMonthValue();
            String key = String.valueOf(shardKey);
            String templateMonth = String.format("%02d", currentMonth);
            // 对分片键 CRC8取模
            int tableSuffix = CRC8Util.calculateCRC8(key.getBytes()) % SUB_TABLE_NUM;
            return logic.concat("_").concat(templateMonth).concat("_0").concat(String.valueOf(tableSuffix));
        }

        return null;
    }

    public static String getShardingTableNameByStandard(String logic, Object shardKey) {
        if (!StringUtils.isEmpty(logic) && !ObjectUtils.isEmpty(shardKey)) {
            String key = String.valueOf(shardKey);
            // 对分片键 CRC8取模
            int tableSuffix = CRC8Util.calculateCRC8(key.getBytes()) % SUB_TABLE_NUM;

            return logic.concat("_0").concat(String.valueOf(tableSuffix));
        }

        return null;
    }
}
