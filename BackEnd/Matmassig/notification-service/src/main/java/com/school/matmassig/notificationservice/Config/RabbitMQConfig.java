packagecom. import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;import rg.sprigframeork.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue esbQueue() {
        return new Queue("esb-queue", true); // File durable
    }
}


    