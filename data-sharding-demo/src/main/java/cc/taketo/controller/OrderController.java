package cc.taketo.controller;

import cc.taketo.entity.Order;
import cc.taketo.mapper.OrderMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderMapper orderMapper;


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
