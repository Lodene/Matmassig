packagecom. import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

import rg.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue("my-queue", true);
    }
}


    