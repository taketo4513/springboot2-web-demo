package cc.taketo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Zhangp
 * @date 2024/3/10 18:52
 */
@Data
@TableName("t_user")
public class User {

    private Long id;

    private String username;
}
