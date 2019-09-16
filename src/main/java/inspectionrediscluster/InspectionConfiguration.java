package inspectionrediscluster;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

@Data
@Configuration
public class InspectionConfiguration {

    @Value("${redis.cluster.nodes}")
    List<String> nodes;

    @Bean
    RedisConnectionFactory connectionFactory() {
        var pool = new JedisPoolConfig();
        pool.setMaxTotal(100);
        pool.setMaxIdle(10);
        pool.setMinIdle(10);
        pool.setTestOnBorrow(true);
        return new JedisConnectionFactory(new RedisClusterConfiguration(nodes));
    }

    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
