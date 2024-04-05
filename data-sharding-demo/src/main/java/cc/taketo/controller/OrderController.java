package cc.taketo.controller;

import cc.taketo.entity.Order;
import cc.taketo.mapper.OrderMapper;
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
@RequestMapping("/order")
public class OrderController {

    private final DateTimeFormatter dateTemplate = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    @Resource
    private OrderMapper orderMapper;

    @GetMapping("/save")
    public String save() {
        Order order = new Order();
        order.setOrderNo("test");
        order.setUserId(1776260277071785985L);
        order.setCreateTime(LocalDateTime.parse(LocalDateTime.now().format(dateTemplate), dateTemplate));
        orderMapper.insert(order);
        return "ok";
    }

    @GetMapping("/list")
    public String list() {
        List<Order> orders = orderMapper.selectList(null);
        System.out.println(orders.size());
        return JSON.toJSONString(orders);
    }

    @GetMapping("/order")
    public String orderBy() {
        List<Order> orders = orderMapper.selectList(new LambdaQueryWrapper<Order>().orderByAsc(Order::getCreateTime));
        System.out.println(orders.size());
        return JSON.toJSONString(orders);
    }
}
