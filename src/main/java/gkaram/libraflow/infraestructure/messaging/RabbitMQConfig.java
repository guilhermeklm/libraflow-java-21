package gkaram.libraflow.infraestructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_BOOK_RETURNED = "book.returned";
    public static final String EXCHANGE_BOOKS = "books";
    public static final String ROUTING_KEY_BOOK_RETURNED = "book.returned";


    @Bean
    public Queue bookReturnedQueue() {
        return new Queue(QUEUE_BOOK_RETURNED, true); // durable: sobrevive a restart do broker
    }

    @Bean
    public TopicExchange booksExchange() {
        return new TopicExchange(EXCHANGE_BOOKS);
    }

    @Bean
    public Binding bookReturnedBinding(Queue bookReturnedQueue, TopicExchange booksExchange) {
        return BindingBuilder.bind(bookReturnedQueue).to(booksExchange).with(ROUTING_KEY_BOOK_RETURNED);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
