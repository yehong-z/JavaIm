package com.zyh.javaim.comet.service;

import com.zyh.javaim.CometService;

import com.zyh.javaim.StateReq;
import com.zyh.javaim.StateResp;
import com.zyh.javaim.comet.common.convention.redis.Key;
import com.zyh.javaim.comet.common.message.Message;
import com.zyh.javaim.comet.netty.SendMessage;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;

@DubboService
@Slf4j
@RequiredArgsConstructor
public class CometServiceImpl implements CometService {
    private final SendMessage sendMessage;
    private final RedisTemplate<String, Integer> redisTemplate;
    @Override
    public boolean pushUser(Long userId, String msg) {
        log.info(String.format("push message to %s msg: %s", userId, msg));
        sendMessage.Send(new Message().setMsg(msg).setToUser(userId));
        return true;
    }

    @Override
    public StateResp state(StateReq req) {
        StateResp stateResp = new StateResp();
        try {
            Integer serverId = redisTemplate.opsForValue().get(Key.UserServerKey(req.userId));
            if (serverId != null) {
                stateResp.online = true;
                stateResp.serverId = serverId;
                return stateResp;
            }
        } catch (Exception e) {
            log.error(e.toString());
            stateResp.online = false;
        }

        return stateResp;
    }


}
