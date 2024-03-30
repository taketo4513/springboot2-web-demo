package cc.taketo.controller;

import cc.taketo.entity.LogInfo;
import cc.taketo.mapper.LogInfoMapper;
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

/**
 * @author Zhangp
 * @date 2024/3/22 15:40
 */
@RestController
@RequestMapping("/log")
public class LogInfoController {

    @Resource
    private LogInfoMapper logInfoMapper;

    private final DateTimeFormatter dateTemplate = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);

    @GetMapping("/save")
    public String save() {
        for (int i = 0; i < 100; i++) {
            LogInfo logInfo = new LogInfo();
            logInfo.setLogInfo("test" + i);
            logInfo.setCreateTime(LocalDateTime.parse(LocalDateTime.now().format(dateTemplate), dateTemplate));
            logInfoMapper.insert(logInfo);
        }
        return "ok";
    }

    @GetMapping("/get")
    public String get() {
        List<LogInfo> logInfos = logInfoMapper.selectList(null);
        System.out.println(logInfos.size());
        return JSON.toJSONString(logInfos);
    }

    @GetMapping("/between")
    public String between() {
        LambdaQueryWrapper<LogInfo> queryWrapper = new LambdaQueryWrapper<LogInfo>().between(LogInfo::getCreateTime,
                LocalDateTime.parse("2024-03-22 16:35:30", dateTemplate),
                LocalDateTime.parse("2024-03-22 16:35:35", dateTemplate));
        List<LogInfo> logInfos = logInfoMapper.selectList(queryWrapper);
        return JSON.toJSONString(logInfos);
    }
}
