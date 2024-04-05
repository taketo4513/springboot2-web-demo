package cc.taketo.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnowflakeWorkId implements Serializable, Comparable<SnowflakeWorkId> {

    private static final long serialVersionUID = 1L;

    /**
     * 注册时的时间戳
     */
    private Long timestamp;

    /**
     * 工作节点 0~1023
     */
    private Integer workerId;

    @Override
    public int compareTo(SnowflakeWorkId snowflakeWorkId) {
        long ex = this.timestamp - snowflakeWorkId.getTimestamp();
        return ex > 0 ? 1 : -1;
    }
}
