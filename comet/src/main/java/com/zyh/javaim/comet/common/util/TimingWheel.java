package com.zyh.javaim.comet.common.util;

import com.zyh.javaim.Message;
import com.zyh.javaim.MessageType;
import com.zyh.javaim.comet.netty.SendMessage;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class TimingWheel {
    private static HashedWheelTimer timer = new HashedWheelTimer();
    private static Map<String, Timeout> timeoutMap = new ConcurrentHashMap<>();
    public void registerTimeout(String eventName, Message message) {
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                // 告知用户发送失败
                Message failMsg = new Message();
                failMsg.setMsgSeq(message.msgSeq);
                failMsg.setType(MessageType.SendFail);
                // TODO: 调用logic存储离线消息
            }
        };

        Timeout timeout = timer.newTimeout(task, 10, TimeUnit.SECONDS);
        timeoutMap.put(eventName, timeout);
    }

    public String EventKey(Long toUserId, Long MsgSeq) {
        return String.format("%s:%s", toUserId, MsgSeq);
    }
    public void cancelTimeout(String eventName) {
        Timeout timeout = timeoutMap.get(eventName);
        if (timeout != null) {
            timeout.cancel(); // 取消事件
            System.out.println(eventName + " canceled");
        } else {
            System.out.println("Event not found: " + eventName);
        }
    }
}
