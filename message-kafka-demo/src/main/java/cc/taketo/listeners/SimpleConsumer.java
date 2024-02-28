package cc.taketo.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    @KafkaListener(topics = {"test"})
    public void onMessage(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        logger.info("简单消费 => 主题: {}, 分区: {}, 消息: {}, Leader: {}, Offset: {}",
                record.topic(),
                record.partition(),
                record.value(),
                record.leaderEpoch(),
                record.offset());
    }

}
