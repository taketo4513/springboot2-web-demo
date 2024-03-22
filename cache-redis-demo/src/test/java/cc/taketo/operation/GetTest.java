package cc.taketo.operation;

import cc.taketo.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author Zhangp
 * @date 2024/3/21 15:48
 */
@SpringBootTest(classes = Application.class)
public class GetTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void getTest() {
        String value = (String) redisTemplate.opsForValue().get("key");
        System.out.println("value = " + value);
    }
}
