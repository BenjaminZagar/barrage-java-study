package com.setronica.eventing.app;

import com.setronica.eventing.configuration.QueueConfiguration;
import com.setronica.eventing.dto.PaymentProviderResponse;
import com.setronica.eventing.persistence.PaymentRecord;
import com.setronica.eventing.persistence.PaymentStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentProviderSimulation {
    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = {"spring-boot"})
    public void paymentProviderSimulation(PaymentRecord message) {
        // generate random response from payment provider
        // payment provider will sometimes return AUTHORIZED and sometimes FAILED payment status
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        PaymentStatus randomStatus;
        if (randomNumber == 0) {
            randomStatus = PaymentStatus.AUTHORIZED;
        } else {
            randomStatus = PaymentStatus.FAILED;
        }
        PaymentProviderResponse newPaymentProviderResponse = new PaymentProviderResponse(message.getId(), randomStatus);
        template.convertAndSend(QueueConfiguration.paymentTopicExchangeName, QueueConfiguration.paymentRoutingKey, newPaymentProviderResponse);
    }
}
