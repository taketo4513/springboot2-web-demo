package cc.taketo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zhangp
 * @date 2024/3/21 15:46
 */
@SpringBootApplication
public class LockRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(LockRedisApplication.class, args);
    }
}
