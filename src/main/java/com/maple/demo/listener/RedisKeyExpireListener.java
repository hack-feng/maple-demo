//package com.maple.demo.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
///**
// * Redis监听key过期
// *
// * @author 笑小枫
// * @date 2022/07/19
// **/
//@Slf4j
//@Component
//public class RedisKeyExpireListener extends KeyExpirationEventMessageListener {
//
//    public RedisKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String expireKey = message.toString();
//        // 根据过期的key处理对应的业务逻辑
//        log.info(expireKey + "已过期-------------------");
//    }
//}
