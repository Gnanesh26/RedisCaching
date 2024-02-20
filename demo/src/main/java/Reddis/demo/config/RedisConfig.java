package Reddis.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
   // @Bean: Indicates that the method produces a Spring Bean, which will be managed by the Spring container.
    public JedisConnectionFactory connectionFactory() {
        // Create a new RedisStandaloneConfiguration instance for connecting to a single Redis server
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();

        // Set the hostname of the Redis server (default: localhost)
        configuration.setHostName("localhost");

        // Set the port number of the Redis server (default: 6379)
        configuration.setPort(6379);

        // Create a new JedisConnectionFactory instance with the specified Redis configuration
        return new JedisConnectionFactory(configuration);
    }



    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        // Create a new RedisTemplate instance
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Set the connection factory for the RedisTemplate
        template.setConnectionFactory(connectionFactory());

        // Set the key serializer for converting keys to String
        template.setKeySerializer(new StringRedisSerializer());

        // Set the hash key serializer for converting hash keys to String
        template.setHashKeySerializer(new StringRedisSerializer());

        // Set the hash value serializer for converting hash values to Java objects (JDK Serialization)
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());

        // Set the value serializer for converting values to Java objects (JDK Serialization)
        template.setValueSerializer(new JdkSerializationRedisSerializer());

        // Enable transaction support for the RedisTemplate
        template.setEnableTransactionSupport(true);

        // Perform any additional initialization required for the RedisTemplate
        template.afterPropertiesSet();

        // Return the configured RedisTemplate
        return template;
    }

}
