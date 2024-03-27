package com.zyh.javaim.comet.netty;

import com.zyh.javaim.Message;
import com.zyh.javaim.comet.common.convention.redis.Key;
import com.zyh.javaim.comet.common.util.TimingWheel;
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
    private final TimingWheel timingWheel;
    public boolean Send(Message msg) {
        // TODO: 异步写
        Long toUser = msg.getToUserId();
        String key = Key.UserChannelKey(toUser);
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

        // TODO: 加入定时任务队列，后台检查没收到ACK重新发送消息
        channel.writeAndFlush(msg.toString());
        timingWheel.registerTimeout(
                timingWheel.EventKey(msg.getToUserId(), msg.getMsgSeq())
                , msg);
        log.info("发送消息 channel:" + channel.id() + "msg:"+ msg);
        return true;
    }
}
