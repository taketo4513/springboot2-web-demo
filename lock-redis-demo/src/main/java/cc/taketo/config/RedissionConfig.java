package cc.taketo.config;

import cc.taketo.common.RedissionNodesProperties;
import cc.taketo.common.RedissionProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zhangp
 * @date 2024/5/1 21:53
 */
@Configuration
public class RedissionConfig {
    @Resource
    private RedissionNodesProperties redissionNodesProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        List<RedissionProperties> nodes = redissionNodesProperties.getNodes();
        nodes.forEach(node -> {
            singleServerConfig.setAddress("redis://" + node.getHost() + ":" + node.getPort())
                    .setPassword(node.getPassword());
        });
        return Redisson.create(config);
    }
}
