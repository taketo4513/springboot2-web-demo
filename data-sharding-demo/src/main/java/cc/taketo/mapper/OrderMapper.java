package cc.taketo.mapper;

import cc.taketo.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Zhangp
 * @date 2024/3/14 15:28
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> selectOrderList();
}
