package ollamaStar.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.ObjectInputFilter;

@Configuration
public class RedissionConfig
{
    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties){
        Config config = new Config();


        config.useSingleServer().setAddress(String.format("redis://%s:%s",redisProperties.getHost(),redisProperties.getPort()));
        return Redisson.create(config);
    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisProperties redisProperties) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
        lettuceConnectionFactory.start();

        return new StringRedisTemplate(lettuceConnectionFactory);
    }

}
