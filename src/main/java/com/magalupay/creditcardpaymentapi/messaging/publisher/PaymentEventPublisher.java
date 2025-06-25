package com.magalupay.creditcardpaymentapi.messaging.publisher;

import com.magalupay.creditcardpaymentapi.dto.PaymentApprovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routingKey}")
    private String routingKey;

    public void publishPaymentApproved(PaymentApprovedEvent event) {
        log.info("Publicando evento de pagamento aprovado: {}", event);
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
