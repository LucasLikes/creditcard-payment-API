package com.magalupay.creditcardpaymentapi.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("databaseHealth")
public class DatabaseHealthIndicator implements HealthIndicator {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthIndicator.class);
    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (!connection.isValid(5)) {
                logger.error("Database connection invalid");
                return Health.down().withDetail("reason", "Database connection invalid").build();
            }
            logger.debug("Database health check passed");
            return Health.up().withDetail("database", "PostgreSQL").build();
        } catch (Exception e) {
            logger.error("Database health check failed", e);
            return Health.down().withDetail("error", e.getMessage()).build();
        }
    }
}
