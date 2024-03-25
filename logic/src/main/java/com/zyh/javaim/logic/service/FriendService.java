package com.zyh.javaim.logic.service;


import com.zyh.javaim.logic.common.context.Context;
import com.zyh.javaim.logic.common.message.Message;
import com.zyh.javaim.logic.common.message.MessageType;
import com.zyh.javaim.logic.common.mq.MessageQueue;
import com.zyh.javaim.logic.dao.entity.Friend;
import com.zyh.javaim.logic.dao.mapper.FriendMapper;
import com.zyh.javaim.logic.dto.req.FriendAckReq;
import com.zyh.javaim.logic.dto.req.FriendAddReq;
import com.zyh.javaim.logic.dto.resp.ResponseOK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendMapper friendMapper;
    private final MessageQueue messageQueue;
    public ResponseOK add(FriendAddReq req) {
        ResponseOK friendAddResp = new ResponseOK();
        Long friendId = req.getUserId();
        Long userId = Context.getRequestId().getUser().getId();
        AddFriend(userId, friendId);

        // 发送好友请求
        messageQueue.Send(new Message()
                .setToUser(friendId)
                .setType(MessageType.AddFriend)
                .setMsg(req.getMsg()));

        return friendAddResp;
    }

    public ResponseOK ack(FriendAckReq req) {
        ResponseOK responseOK = new ResponseOK();
        Long friendId = req.getFriendId();
        Long userId = Context.getRequestId().getUser().getId();
        AddFriend(userId, friendId);
        return responseOK;
    }

    public void AddFriend(Long userId, Long friendId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        try {
            friendMapper.insert(friend);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
