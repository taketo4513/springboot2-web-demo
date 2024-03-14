package cc.taketo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhangp
 * @date 2024/3/10 18:52
 */
@Data
@TableName("t_order")
public class Order {

    private Long id;

    private String orderNo;

    private Long userId;

    private LocalDateTime createTime;
}
