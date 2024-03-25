package com.zyh.javaim.logic.service;


import com.zyh.javaim.logic.common.context.Context;
import com.zyh.javaim.logic.common.message.Message;
import com.zyh.javaim.logic.common.message.MessageType;
import com.zyh.javaim.logic.common.mq.MessageQueue;
import com.zyh.javaim.logic.dto.req.MessageReq;
import com.zyh.javaim.logic.dto.resp.MessageResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageQueue messageQueue;
    public MessageResp push(MessageReq msg) {
        MessageResp messageResp = new MessageResp();
        log.info(Context.getRequestId().toString());

        Message message = new Message()
                .setMsg(msg.getMsg())
                .setType(MessageType.ToUser)
                .setToUser(msg.getToUser());
        boolean res = messageQueue.Send(message);
        if (res) {
            messageResp.setOk("ok");
        } else {
            messageResp.setOk("send error");
        }

        return messageResp;
    }
}
