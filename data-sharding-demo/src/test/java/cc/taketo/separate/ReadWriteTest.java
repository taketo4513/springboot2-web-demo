package cc.taketo.separate;

import cc.taketo.Application;
import cc.taketo.entity.User;
import cc.taketo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zhangp
 * @date 2024/3/10 18:58
 */
@SpringBootTest(classes = Application.class)
public class ReadWriteTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("zhangsan");

        userMapper.insert(user);
    }

    @Test
    public void testSelect(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    @Transactional
    public void testTrans(){
        User user = new User();
        user.setUsername("lisi");

        userMapper.insert(user);

        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
