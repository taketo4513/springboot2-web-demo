package cc.taketo.kafka;

import cc.taketo.MessageKafkaApplication;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;

@SpringBootTest(classes = MessageKafkaApplication.class)
public class KafkaTransactionTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTransactionTest.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    public void testTransaction() {
        kafkaTemplate.executeInTransaction((producer) -> {

            // 发送消息
            producer.send("test", "test-key", "test-message");

            // 抛出异常，触发事务回滚
            throw new RuntimeException("test transaction exception");
        });
    }
}
