package com.otzg.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @ClassName RedisConfig
 * @Auther john
 * @Date 2019-03-02  15:51
 * @Version
 **/

@Configuration
public class RedisMqConfig {

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫具体的 的通道
        for(String channel: SyncQue.getChannelMap()){
            container.addMessageListener(listenerAdapter,new PatternTopic(channel));
        }

        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param syncPayQue
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(SyncQue syncPayQue) {
        return new MessageListenerAdapter(syncPayQue);
    }
}
