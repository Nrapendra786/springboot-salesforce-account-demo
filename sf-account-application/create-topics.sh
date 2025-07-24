#!/bin/bash
# Wait for Kafka to start
sleep 15

/opt/bitnami/kafka/bin/kafka-topics.sh --create --topic $TEST_TOPIC_NAME --bootstrap-server kafka:9092

echo "topic $TEST_TOPIC_NAME was created"
