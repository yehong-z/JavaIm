package com.zyh.javaim.comet.common.util;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class TimingWheel {
    private static HashedWheelTimer timer = new HashedWheelTimer();
    private static Map<String, Timeout> timeoutMap = new ConcurrentHashMap<>();
    private void registerTimeout(String eventName, long delaySeconds) {
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println(eventName + " executed after " + delaySeconds + " seconds");
                timeoutMap.remove(eventName); // 任务执行后移除对应的Timeout
            }
        };

        Timeout timeout = timer.newTimeout(task, delaySeconds, TimeUnit.SECONDS);
        timeoutMap.put(eventName, timeout);
    }

    private void cancelTimeout(String eventName) {
        Timeout timeout = timeoutMap.get(eventName);
        if (timeout != null) {
            timeout.cancel(); // 取消事件
            System.out.println(eventName + " canceled");
        } else {
            System.out.println("Event not found: " + eventName);
        }
    }
}
