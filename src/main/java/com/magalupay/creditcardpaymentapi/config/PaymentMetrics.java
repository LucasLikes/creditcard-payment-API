package com.magalupay.creditcardpaymentapi.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentMetrics {
    private static final Logger logger = LoggerFactory.getLogger(PaymentMetrics.class);

    private final Counter paymentSuccessCounter;
    private final Counter paymentFailureCounter;
    private final Timer paymentProcessingTimer;

    public PaymentMetrics(MeterRegistry meterRegistry) {
        this.paymentSuccessCounter = Counter.builder("payment.success")
            .description("Total successful payments")
            .register(meterRegistry);

        this.paymentFailureCounter = Counter.builder("payment.failure")
            .description("Total failed payments")
            .register(meterRegistry);

        this.paymentProcessingTimer = Timer.builder("payment.processing.time")
            .description("Payment processing duration")
            .register(meterRegistry);
            
        logger.info("PaymentMetrics initialized");
    }

    public void recordSuccessfulPayment() {
        paymentSuccessCounter.increment();
        logger.debug("Payment success recorded");
    }

    public void recordFailedPayment() {
        paymentFailureCounter.increment();
        logger.debug("Payment failure recorded");
    }

    public Timer.Sample startPaymentTimer() {
        return Timer.start();
    }

    public void stopPaymentTimer(Timer.Sample sample) {
        sample.stop(paymentProcessingTimer);
        logger.debug("Payment timer stopped");
    }
}
