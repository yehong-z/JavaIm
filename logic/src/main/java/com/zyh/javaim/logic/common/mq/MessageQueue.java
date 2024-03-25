package com.zyh.javaim.logic.common.mq;

import com.zyh.javaim.logic.common.message.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageQueue {
    public boolean Send(Message message) {
        return true;
    }
}
