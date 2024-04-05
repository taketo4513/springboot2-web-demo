package cc.taketo.controller;

import cc.taketo.entity.Order;
import cc.taketo.entity.User;
import cc.taketo.mapper.OrderMapper;
import cc.taketo.mapper.UserMapper;
import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    private final DateTimeFormatter dateTemplate = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    @GetMapping("/save")
    public String save() {
        for (int i = 0; i < 100; i++) {
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
        return "ok";
    }

    @GetMapping("/list")
    public String list() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users.size());
        return JSON.toJSONString(users);
    }

    @GetMapping("/order")
    public String orderBy() {
        List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>().orderByAsc(User::getCreateTime));
        System.out.println(users.size());
        return JSON.toJSONString(users);
    }

}
