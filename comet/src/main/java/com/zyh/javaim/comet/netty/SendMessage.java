package com.zyh.javaim.comet.netty;


import com.zyh.javaim.comet.common.convention.redis.Key;
import com.zyh.javaim.comet.common.message.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class SendMessage {
    private final RedisTemplate<String, ChannelId> redis;
    public boolean Send(Message msg) {
        // TODO: 异步写
        Long toUser = msg.getToUser();
        String key = Key.UserChannelKey(msg.getToUser());
        ChannelId channelId = redis.opsForValue().get(key);
        if (channelId == null) {
            // 用户离线，写入redis等
            log.info(toUser + "redis中不存在 channelId");
            return false;
        }
        Channel channel = SocketHandler.clients.find(channelId);
        if (channel == null) {
            // 用户离线，写入redis等
            log.info(toUser + "找不到channelId");
            return false;
        }
        channel.writeAndFlush(msg.toString());
        log.info("发送消息 channel:" + channel.id() + "msg:"+ msg);
        return true;
    }
}
