package com;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class FeedConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedConsumerApplication.class, args);
    }

    @Bean
    public ConnectionFactory connectionFactory(@Value("${amqp.internal.message.host}") String host,
                                               @Value("${amqp.internal.message.port}") int port,
                                               @Value("${amqp.internal.message.virtualhost}") String virtualhost,
                                               @Value("${amqp.internal.message.host.username}") String userName,
                                               @Value("${amqp.internal.message.host.password}") String password,
                                               @Value("${amqp.internal.connection.timeout}") int connectionTimeout){

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
        cachingConnectionFactory.setVirtualHost(virtualhost);
        cachingConnectionFactory.setUsername(userName);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setPublisherReturns(Boolean.TRUE);
        cachingConnectionFactory.setPublisherConfirms(Boolean.TRUE);
        cachingConnectionFactory.setConnectionTimeout(connectionTimeout);

        return cachingConnectionFactory;
    }

    @Bean
    public RetryTemplate retryTemplate(@Value("${amqp.max.retry.count}") int maxAttempts,
                                       @Value("${amqp.initial.interval.milliseconds}") int initalInterval,
                                       @Value("${amqp.initial.interval.multiplier}") int multiplier,
                                       @Value("${amqp.max.interval.milliseconds}") int maxInterval)
    {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);

        RetryTemplate retryTemplate = new RetryTemplate();

        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initalInterval);
        backOffPolicy.setMultiplier(multiplier);
        backOffPolicy.setMaxInterval(maxInterval);

        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(@Value("${amqp.exchange.name}") String exchangeName, RetryTemplate retryTemplate, ConnectionFactory connectionFactory) throws Exception {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(Boolean.TRUE);

        rabbitTemplate.setRetryTemplate(retryTemplate);

        return rabbitTemplate;
    }
}
