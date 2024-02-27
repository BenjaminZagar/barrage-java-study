package com.setronica.eventing.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    public static final String topicExchangeName = "spring-boot-exchange";

    public static final String paymentTopicExchangeName = "payment-exchange";

    public static final String queueName = "spring-boot";

    public static final String paymentQueueName = "payment_queue";

    public static final String routingKey = "foo.bar.#";

    public static final String paymentRoutingKey = "foo.bar.payment";

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean public Queue paymentQueue() { return new Queue(paymentQueueName); }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange(paymentTopicExchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean public Binding paymentBinding(Queue paymentQueue, TopicExchange paymentExchange) { return BindingBuilder.bind(paymentQueue).to(paymentExchange).with(paymentRoutingKey); }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
