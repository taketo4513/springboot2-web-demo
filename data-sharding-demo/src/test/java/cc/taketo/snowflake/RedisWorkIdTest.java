package cc.taketo.snowflake;

import cc.taketo.Application;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest(classes = Application.class)
public class RedisWorkIdTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final String PREFIX_SNOW = "snowflake:work_id";

    private final Integer DATA_SIZE = 1024;

    @Test
    public void test() {
        SnowflakeWorkId snowflakeWorkId = calculateDataIdAndWorkId(redisTemplate);
        System.out.println("snowflakeWorkId = " + snowflakeWorkId);
    }

    public SnowflakeWorkId calculateDataIdAndWorkId(RedisTemplate redisTemplate) {
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
