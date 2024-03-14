package cc.taketo.separate;

import cc.taketo.Application;
import cc.taketo.entity.Order;
import cc.taketo.entity.User;
import cc.taketo.mapper.OrderMapper;
import cc.taketo.mapper.UserMapper;
import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Zhangp
 * @date 2024/3/10 20:50
 */
@SpringBootTest(classes = Application.class)
public class ShareTableTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    private final DateTimeFormatter dateTemplate = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    @Test
    public void testInsert() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("zhangsan" + i);
            user.setCreateTime(LocalDateTime.parse(LocalDateTime.now().format(dateTemplate), dateTemplate));
            userMapper.insert(user);

            Order order = new Order();
            order.setOrderNo("order" + i);
            order.setUserId(user.getId());
            order.setCreateTime(LocalDateTime.parse(LocalDateTime.now().format(dateTemplate), dateTemplate));
            orderMapper.insert(order);
        }
    }

    @Test
    public void testSelect() {
        List<User> users = userMapper.selectList(null);
        List<Order> orders = orderMapper.selectList(null);
        System.out.println("users = " + users);
        System.out.println("orders = " + orders);
    }

    @Test
    public void testSelectUser() {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, 1768217595927777281L));
        System.out.println("user = " + user);
    }

    @Test
    public void testSelectOrder() {
        // Order表按照UserId分表，使用id查询，需要查两次。
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getId, 1768226973825269762L));
        System.out.println("order = " + order);
    }

    @Test
    public void testSelectByUserId() {
        // Order表按照UserId分表，使用UserId查询，只查一次。
        Order order = orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getUserId, 1768226971673591810L));
        System.out.println("order = " + order);
    }
}
