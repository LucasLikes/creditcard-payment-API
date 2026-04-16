package com.magalupay.creditcardpaymentapi.messaging.listener;

import com.magalupay.creditcardpaymentapi.dto.PaymentApprovedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventListener {
    private PaymentApprovedEvent lastEvent;

    @RabbitListener(id = "paymentListener", queues = "${app.rabbitmq.queue}")
    public void receivePaymentApprovedEvent(PaymentApprovedEvent event) {
        try {
            this.lastEvent = event;
        log.info("Payment event received: transactionId={}, amount={}", event.getTransactionId(), event.getAmount());
        } catch (Exception e) {
            log.error("Error processing payment event", e);
            throw e;
        }
    }

    public PaymentApprovedEvent getLastEvent() {
        return lastEvent;
    }
}
