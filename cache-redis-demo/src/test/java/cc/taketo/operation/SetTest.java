package cc.taketo.operation;

import cc.taketo.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Zhangp
 * @date 2024/3/21 15:48
 */
@SpringBootTest(classes = Application.class)
public class SetTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void setTest() {
        redisTemplate.opsForValue().set("key", "value");
    }
}
