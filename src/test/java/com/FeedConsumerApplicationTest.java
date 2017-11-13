package com;

import com.factory.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.retry.support.RetryTemplate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class FeedConsumerApplicationTest {

    FeedConsumerApplication feedConsumerApplication;

    String host = "localhost";
    int port = 8081;
    String virtualhost = "virtualhost";
    String userName = "username";
    String password = "password";
    int connectionTimeout = 10000;

    String exchangeName = "myExchange";
    @Mock
    RetryTemplate retryTemplate;
    @Mock
    ConnectionFactory connectionFactory;


    @Before
    public void setUp() throws Exception {
        feedConsumerApplication = new FeedConsumerApplication();
    }

    @Test
    public void connectionFactory_builtWithExpectedValues() throws Exception {

        ConnectionFactory connectionFactory = feedConsumerApplication.connectionFactory(host, port, virtualhost, userName, password, connectionTimeout);

        assertNotNull(connectionFactory);
        assertTrue(connectionFactory instanceof CachingConnectionFactory);

        assertThat(connectionFactory.getHost(), is(host));
        assertThat(connectionFactory.getPort(), is(port));
        assertThat(connectionFactory.getUsername(), is(userName));
        assertThat(connectionFactory.getVirtualHost(), is(virtualhost));
    }

    @Test
    public void rabbitTemplate_builtWithExpectedValues() throws Exception {
        RabbitTemplate rabbitTemplate = feedConsumerApplication.rabbitTemplate(exchangeName, retryTemplate, connectionFactory);

        assertThat(rabbitTemplate.getExchange(), is(exchangeName));
        assertTrue(rabbitTemplate.getMessageConverter() instanceof Jackson2JsonMessageConverter);
        assertThat(rabbitTemplate.getConnectionFactory(), is(connectionFactory));


    }

}