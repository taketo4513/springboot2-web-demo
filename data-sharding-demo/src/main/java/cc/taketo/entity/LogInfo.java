package cc.taketo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Zhangp
 * @date 2024/3/22 13:14
 */
@Data
@TableName("t_log_info")
public class LogInfo {

    private Long id;

    private String logInfo;

    private LocalDateTime createTime;
}
