package com.zyh.javaim.logic.service;


import com.zyh.javaim.CometService;
import com.zyh.javaim.StateReq;
import com.zyh.javaim.StateResp;
import com.zyh.javaim.logic.common.context.Context;
import com.zyh.javaim.logic.common.id.Id;
import com.zyh.javaim.logic.common.message.Message;
import com.zyh.javaim.logic.common.message.MessageType;
import com.zyh.javaim.logic.common.mq.MessageQueue;
import com.zyh.javaim.logic.dao.entity.OfflineMessage;
import com.zyh.javaim.logic.dao.mapper.OfflineMessageMapper;
import com.zyh.javaim.logic.dto.req.MessageReq;
import com.zyh.javaim.logic.dto.resp.MessageResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageQueue messageQueue;
    private final OfflineMessageMapper offlineMessageMapper;
    private final Id id;
    @DubboReference
    private CometService cometService;
    public MessageResp push(MessageReq msg) {
        MessageResp messageResp = new MessageResp();
        log.info(Context.getRequestId().toString());

        Message message = new Message()
                .setMsg(msg.getMsg())
                .setType(MessageType.ToUser)
                .setToUser(msg.getToUser());
        // 先查询状态再发送到对应服务器
        StateReq stateReq = new StateReq();
        stateReq.userId = msg.getToUser();
        StateResp stateResp = cometService.state(stateReq);
        if (stateResp.online) {
            boolean res = messageQueue.Send(message);
            if (res) {
                messageResp.setOk("ok");
            } else {
                messageResp.setOk("send error");
            }
        } else {
            OfflineMessage offlineMessage = new OfflineMessage();
            offlineMessage.setContent(msg.getMsg());
            offlineMessage.setToUserId(msg.getToUser());
            offlineMessage.setMsgType(MessageType.ToUser);
            offlineMessage.setMsgId(id.generateId());
            offlineMessageMapper.insert(offlineMessage);
        }

        return messageResp;
    }
}
