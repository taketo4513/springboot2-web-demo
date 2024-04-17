package cc.taketo.mapper;

import cc.taketo.entity.LogInfo;
import cc.taketo.entity.po.LogInfoPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Zhangp
 * @date 2024/3/22 13:15
 */
@Repository
public interface LogInfoMapper extends BaseMapper<LogInfo> {

    int bathInsert(@Param("list") List<LogInfo> list);

    void bathUpdate(@Param("list") List<LogInfoPO> list);
}
