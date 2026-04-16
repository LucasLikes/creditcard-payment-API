package com.magalupay.creditcardpaymentapi.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("rabbitMqHealth")
public class RabbitMQHealthIndicator implements HealthIndicator {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQHealthIndicator.class);
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQHealthIndicator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Health health() {
        try {
            rabbitTemplate.getRabbitConnectionFactory().createConnection().close();
            logger.debug("RabbitMQ health check passed");
            return Health.up().withDetail("service", "RabbitMQ").build();
        } catch (Exception e) {
            logger.error("RabbitMQ health check failed", e);
            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }
}
