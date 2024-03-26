package com.zyh.javaim.logic.common.mq;

import com.zyh.javaim.CometService;
import com.zyh.javaim.logic.common.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageQueue {
    @DubboReference
    private CometService pushMessage;
    public boolean Send(Message message) {
        // TODO: 实现用户ID到ServerID的映射并调用
        log.info(String.format("to user %s msg: %s", message.getToUser(), message.getMsg()));
        pushMessage.pushUser(message.getToUser(), message.getMsg());
        return true;
    }
}
