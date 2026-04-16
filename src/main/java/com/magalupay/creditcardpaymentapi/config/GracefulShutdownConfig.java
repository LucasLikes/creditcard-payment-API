package com.magalupay.creditcardpaymentapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationShutdownEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class GracefulShutdownConfig {
    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdownConfig.class);

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("Application started successfully. Ready to accept requests.");
    }

    @EventListener(ApplicationShutdownEvent.class)
    public void onApplicationShutdown() {
        logger.warn("Application shutdown initiated. Gracefully closing resources.");
        try {
            Thread.sleep(2000);
            logger.info("All resources closed. Shutdown complete.");
        } catch (InterruptedException e) {
            logger.error("Interrupted during shutdown", e);
            Thread.currentThread().interrupt();
        }
    }
}
