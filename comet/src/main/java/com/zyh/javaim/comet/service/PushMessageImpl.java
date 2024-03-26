package com.zyh.javaim.comet.service;

import com.zyh.javaim.PushMessage;

import com.zyh.javaim.comet.common.message.Message;
import com.zyh.javaim.comet.netty.SendMessage;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
@Slf4j
@RequiredArgsConstructor
public class PushMessageImpl implements PushMessage {
    private final SendMessage sendMessage;
    @Override
    public boolean pushUser(Long userId, String msg) {
        log.info(String.format("push message to %s msg: %s", userId, msg));
        sendMessage.Send(new Message().setMsg(msg).setToUser(userId));
        return true;
    }
}
