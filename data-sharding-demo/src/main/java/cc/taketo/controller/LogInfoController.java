package cc.taketo.controller;

import cc.taketo.entity.LogInfo;
import cc.taketo.entity.po.LogInfoPO;
import cc.taketo.mapper.LogInfoMapper;
import cc.taketo.util.ShardingUtil;
import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        List<LogInfo> logInfoLit = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            LogInfo logInfo = new LogInfo();
            logInfo.setLogInfo("test" + i);
            logInfo.setCreateTime(LocalDateTime.parse(LocalDateTime.now().format(dateTemplate), dateTemplate));
            logInfoLit.add(logInfo);
        }
        logInfoMapper.bathInsert(logInfoLit);

        return "ok";
    }

    @GetMapping("/update")
    public String update() {
        List<LogInfo> logInfoList = logInfoMapper.selectList(null);
        List<LogInfoPO> logInfoPOList = new ArrayList<>();
        logInfoList.forEach(logInfo -> {
            LogInfoPO logInfoPO = new LogInfoPO();
            BeanUtils.copyProperties(logInfo, logInfoPO);
            logInfoPO.setRealTableName(ShardingUtil.getShardingTableNameByComplex(
                    "t_log_info",
                    logInfo.getCreateTime(),
                    logInfo.getId()
            ));
            logInfoPOList.add(logInfoPO);
        });
        logInfoMapper.bathUpdate(logInfoPOList);
        return "ok";
    }

    @GetMapping("/list")
    public String list() {
        List<LogInfo> logInfos = logInfoMapper.selectList(null);
        System.out.println(logInfos.size());
        return JSON.toJSONString(logInfos);
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable Long id) {
        List<LogInfo> logInfos = logInfoMapper.selectList(new LambdaQueryWrapper<LogInfo>().eq(LogInfo::getId, id));
        System.out.println(logInfos.size());
        return JSON.toJSONString(logInfos);
    }

    @GetMapping("/order")
    public String orderBy() {
        List<LogInfo> logInfos = logInfoMapper.selectList(new LambdaQueryWrapper<LogInfo>().orderByAsc(LogInfo::getCreateTime));
        System.out.println(logInfos.size());
        return JSON.toJSONString(logInfos);
    }

    @GetMapping("/between")
    public String between() {
        LambdaQueryWrapper<LogInfo> queryWrapper = new LambdaQueryWrapper<LogInfo>().between(LogInfo::getCreateTime,
                LocalDateTime.parse("2023-03-22 16:35:30", dateTemplate),
                LocalDateTime.parse("2025-03-22 16:35:35", dateTemplate));
        List<LogInfo> logInfos = logInfoMapper.selectList(queryWrapper);
        return JSON.toJSONString(logInfos);
    }
}
