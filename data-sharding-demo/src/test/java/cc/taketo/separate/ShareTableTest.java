package cc.taketo.separate;

import cc.taketo.Application;
import cc.taketo.entity.User;
import cc.taketo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Zhangp
 * @date 2024/3/10 20:50
 */
@SpringBootTest(classes = Application.class)
public class ShareTableTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("zhangsan" + i);
            userMapper.insert(user);
        }
    }
}
