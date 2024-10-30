package com.nrapendra.consumer;

import com.nrapendra.consumer.events.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static com.nrapendra.utils.AppUtil.TOPIC_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConsumerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
                "spring.kafka.consumer.enable-auto-commit=false"
        }
)
@ActiveProfiles("test")
@ContextConfiguration(initializers = {KafkaConsumerServiceTest.Initializer.class})
@Slf4j
public class KafkaConsumerServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    @ClassRule
    public static KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.8.0"))
                    .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true")
                    .withEnv("KAFKA_CREATE_TOPICS", TOPIC_NAME); // Ensure TOPIC_NAME is defined

    @BeforeAll
    public static void before() {
        try {
            kafkaContainer.start();
        } catch (ContainerLaunchException e) {
            log.error("Container logs: {}", kafkaContainer.getLogs());
            throw e; // Rethrow the exception after logging
        }
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.kafka.bootstrap-servers=" + kafkaContainer.getBootstrapServers())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void messageAreReceived() throws Exception {
        kafkaTemplate.send(TOPIC_NAME, messages());
        assertTrue(eventRepository.count() > 0);
    }

    private String messages() {
        return "{\"id\":\"test_id_1\",\"schema_id\":\"schema_id_test_1\",\"payload\":\"dGVzdF8x\"}" +
                "{\"id\":\"test_id_2\",\"schema_id\":\"schema_id_test_2\",\"payload\":\"dGVzdF8y\"}" +
                "{\"id\":\"test_id_3\",\"schema_id\":\"schema_id_test_3\",\"payload\":\"dGVzdF8z\"}" +
                "{\"id\":\"test_id_4\",\"schema_id\":\"schema_id_test_4\",\"payload\":\"dGVzdF80\"}" +
                "{\"id\":\"test_id_5\",\"schema_id\":\"schema_id_test_5\",\"payload\":\"dGVzdF81\"}\n";
    }
}