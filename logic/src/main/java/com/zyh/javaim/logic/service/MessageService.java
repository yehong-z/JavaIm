package com.zyh.javaim.logic.service;


import com.zyh.javaim.*;
import com.zyh.javaim.logic.common.context.Context;
import com.zyh.javaim.logic.common.id.Id;
import com.zyh.javaim.logic.common.mq.MessageQueue;
import com.zyh.javaim.logic.dao.entity.OfflineMessage;
import com.zyh.javaim.logic.dao.entity.Seq;
import com.zyh.javaim.logic.dao.entity.SeqExample;
import com.zyh.javaim.logic.dao.mapper.OfflineMessageMapper;
import com.zyh.javaim.logic.dao.mapper.SeqMapper;
import com.zyh.javaim.logic.dto.req.MessageReq;
import com.zyh.javaim.logic.dto.resp.MessageResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageQueue messageQueue;
    private final OfflineMessageMapper offlineMessageMapper;
    private final SeqMapper seqMapper;
    @DubboReference
    private CometService cometService;
    public MessageResp push(MessageReq msg) {
        MessageResp messageResp = new MessageResp();
        log.info(Context.getRequestId().toString());

        Long fromUserId = Context.getRequestId().getUser().getId();
        Long seq = getSeq(fromUserId);
        Message message = new Message()
                .setContent(msg.getMsg())
                .setType(MessageType.ToUser)
                .setToUserId(msg.getToUser())
                .setFromUserId(fromUserId)
                .setMsgSeq(seq);
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
            offlineMessage.setMsgId(message.getMsgSeq());
            offlineMessageMapper.insert(offlineMessage);
        }

        return messageResp;
    }

    public Long getSeq(Long userId) {
        //TODO: redis存seq mysql每次seq+10000
        SeqExample seqExample = new SeqExample();
        seqExample.createCriteria().andSeqIdEqualTo(userId);
        try {
            List<Seq> seqs = seqMapper.selectByExample(seqExample);
            if (seqs.size() > 0) {
                Seq seq = seqs.get(0);
                seq.setMaxSeq(seq.getMaxSeq()+1);
                seqMapper.updateByPrimaryKey(seq);
                return seq.getMaxSeq();
            } else {
                Seq seq1 = new Seq();
                seq1.setSeqId(userId);
                seq1.setMaxSeq(0L);
                seqMapper.insert(seq1);
                return 0L;
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return 0L;
    }
}
