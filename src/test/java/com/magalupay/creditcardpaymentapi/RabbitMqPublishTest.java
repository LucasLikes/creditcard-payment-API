package com.magalupay.creditcardpaymentapi;

import com.magalupay.creditcardpaymentapi.dto.PaymentApprovedEvent;
import com.magalupay.creditcardpaymentapi.messaging.listener.PaymentEventListener;
import com.magalupay.creditcardpaymentapi.messaging.publisher.PaymentEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.awaitility.Awaitility.await;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@SpringBootTest(properties = {
        "app.rabbitmq.exchange=payment.exchange",
        "app.rabbitmq.queue=payment.queue",
        "app.rabbitmq.routingKey=payment.approved"
})
@RabbitListenerTest
public class RabbitMqPublishTest {

    @Autowired
    private PaymentEventPublisher paymentEventPublisher;

    @Autowired
    private PaymentEventListener paymentEventListener;

    @Test
    public void testPublishPaymentApproved() {
        PaymentApprovedEvent event = PaymentApprovedEvent.builder()
            .transactionId("txn123")
            .amount(new BigDecimal("123.45"))
            .timestamp(Instant.now().toString())
            .build();

        paymentEventPublisher.publishPaymentApproved(event);

        await().atMost(2, TimeUnit.SECONDS).until(() -> paymentEventListener.getLastEvent() != null);

        PaymentApprovedEvent receivedEvent = paymentEventListener.getLastEvent();

        assertNotNull(receivedEvent, "Evento não foi recebido pelo consumidor");
        assertEquals(event.getTransactionId(), receivedEvent.getTransactionId());
        assertEquals(event.getAmount(), receivedEvent.getAmount());
        assertEquals(event.getTimestamp(), receivedEvent.getTimestamp());
    }
}


