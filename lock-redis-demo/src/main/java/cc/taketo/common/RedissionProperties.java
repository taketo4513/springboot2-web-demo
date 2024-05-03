package cc.taketo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Zhangp
 * @date 2024/5/1 21:57
 */
@Setter
@Getter
public class RedissionProperties {

    private String host;

    private int port;

    private String password;
}
