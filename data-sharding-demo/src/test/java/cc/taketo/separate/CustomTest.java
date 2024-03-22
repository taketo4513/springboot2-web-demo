package cc.taketo.separate;

import cc.taketo.Application;
import cc.taketo.entity.LogInfo;
import cc.taketo.mapper.LogInfoMapper;
import cn.hutool.core.date.DatePattern;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Zhangp
 * @date 2024/3/22 13:16
 */
@SpringBootTest(classes = Application.class)
public class CustomTest {

    @Resource
    private LogInfoMapper logInfoMapper;

    private final DateTimeFormatter dateTemplate = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    @Test
    public void test(){
        LogInfo logInfo = new LogInfo();
        logInfo.setLogInfo("test");
        logInfo.setCreateTime(LocalDateTime.parse(LocalDateTime.now().format(dateTemplate), dateTemplate));

        logInfoMapper.insert(logInfo);
    }
}
