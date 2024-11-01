package com.nrapendra.consumer;

import com.nrapendra.consumer.events.EventRepository;
import org.junit.ClassRule;
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
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConsumerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@ContextConfiguration(initializers = { KafkaConsumerServiceTest.Initializer.class })
public class KafkaConsumerServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public KafkaTemplate<String, String> kafkaTemplate;

    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
    .waitingFor(Wait.forListeningPort());

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @DynamicPropertySource
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.kafka.bootstrap-servers=" + kafkaContainer.getBootstrapServers())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void messageAreReceived() throws Exception {
        kafkaTemplate.send("my_topic", messages());
        assertTrue(eventRepository.count() > 0);
    }

    private String messages() {
        return  "{\"id\":\"test_id_1\",\"schema_id\":\"schema_id_test_1\",\"payload\":\"dGVzdF8x\"}" +
                "{\"id\":\"test_id_2\",\"schema_id\":\"schema_id_test_2\",\"payload\":\"dGVzdF8y\"}" +
                "{\"id\":\"test_id_3\",\"schema_id\":\"schema_id_test_3\",\"payload\":\"dGVzdF8z\"}" +
                "{\"id\":\"test_id_4\",\"schema_id\":\"schema_id_test_4\",\"payload\":\"dGVzdF80\"}" +
                "{\"id\":\"test_id_5\",\"schema_id\":\"schema_id_test_5\",\"payload\":\"dGVzdF81\"}\n";
    }
}
