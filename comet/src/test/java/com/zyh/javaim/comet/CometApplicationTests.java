package com.zyh.javaim.comet;

import com.zyh.javaim.LogicService;

import com.zyh.javaim.Message;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

}
