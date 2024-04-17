package cc.taketo.entity.po;

import cc.taketo.entity.LogInfo;
import lombok.Data;

/**
 * @author zhangp
 * @date 2024/04/08 01:02
 */
@Data
public class LogInfoPO extends LogInfo {

    private String realTableName;
}
