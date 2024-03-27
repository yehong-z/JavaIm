package com.zyh.javaim.comet;

import com.alibaba.fastjson2.JSON;
import com.zyh.javaim.LogicService;
import com.zyh.javaim.Message;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class CometApplicationTests {

    @DubboReference
    LogicService logicService;
    @Test
    void contextLoads() {
        var res = logicService.getOfflineMessage(2L);
        for (Message message:res) {
            System.out.println(message.content);
        }
    }

    @Test
    void TestTime() {
        Message obj = new Message();
        obj.setType(1).setMsgSeq(1L).setFromUserId(1L);
        String json = JSON.toJSONString(obj);
        System.out.println(json);

        // JSON字符串转换为Java对象
        Message newObj = JSON.parseObject(json, Message.class);
        System.out.println(newObj.toString());
    }

}
