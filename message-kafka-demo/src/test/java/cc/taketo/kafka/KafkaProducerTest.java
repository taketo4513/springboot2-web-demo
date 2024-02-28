package cc.taketo.kafka;

import cc.taketo.Application;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = Application.class)
public class KafkaProducerTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerTest.class);

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 同步发送
     */
    @Test
    public void syncSend() throws ExecutionException, InterruptedException {
        SendResult<String, Object> test = kafkaTemplate.send("test", "Hello Kafka!").get();
        logger.info("Sync Send Result: {}", test.toString());
    }

    /**
     * 普通异步发送
     */
    @Test
    public void asyncSend() {
        kafkaTemplate.send("test", "Hello Kafka!");
    }

    /**
     * 带回调函数的异步发送（实现方式一）
     */
    @Test
    public void asyncSendWithCallback() {
        String callbackMessage = "Hello Kafka with Callback!";

        kafkaTemplate.send("test", callbackMessage).addCallback(success -> {
            // 消息发送到的topic
            String topic = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();

            logger.info("发送消息成功 => 主题: {}, 分区: {}, Offset: {}", topic, partition, offset);
        }, failure -> {
            logger.info("发送消息失败 => 失败原因: {}", failure.getMessage());
        });
    }

    /**
     * 带回调函数的异步发送（实现方式二）
     */
    @Test
    public void asyncSendWithCallbackOverride() {
        String callbackMessage = "Hello Kafka with Callback!";
        kafkaTemplate.send("test", callbackMessage).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult result) {
                logger.info("发送消息成功 => 主题: {}, 分区: {}, Offset: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("发送消息失败 => 失败原因: {}", ex.getMessage());
            }
        });
    }
}
