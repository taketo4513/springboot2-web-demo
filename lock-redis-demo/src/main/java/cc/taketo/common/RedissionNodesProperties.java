package cc.taketo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zhangp
 * @date 2024/5/1 21:57
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissionNodesProperties {

    private List<RedissionProperties> nodes;
}
