package com.magalupay.creditcardpaymentapi.messaging.listener;

import com.magalupay.creditcardpaymentapi.dto.PaymentApprovedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {
    private PaymentApprovedEvent lastEvent;

    @RabbitListener(id = "paymentListener", queues = "${app.rabbitmq.queue}")
    public void receivePaymentApprovedEvent(PaymentApprovedEvent event) {
        this.lastEvent = event;
        System.out.println("Evento recebido: " + event);
    }

    public PaymentApprovedEvent getLastEvent() {
        return lastEvent;
    }
}
