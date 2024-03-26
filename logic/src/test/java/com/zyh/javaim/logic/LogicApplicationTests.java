package com.zyh.javaim.logic;

import com.zyh.javaim.CometService;
import com.zyh.javaim.Message;
import com.zyh.javaim.StateReq;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogicApplicationTests {

    @DubboReference
    private CometService pushMessage;
    @Test
    void contextLoads() {
//        ReferenceConfig<ServiceDemo> reference = new ReferenceConfig<>();
//        reference.setInterface(ServiceDemo.class);
//        reference.setUrl("dubbo://127.0.0.1:8081/com.zyh.javaim.ServiceDemo");
//        ServiceDemo serviceDemo1 = reference.get();
//        System.out.println(serviceDemo1.sayHello("111"));

    }

    @Test
    void contextLoads1() {
        System.out.println(pushMessage.push(new Message()));
    }

    @Test
    void contextLoads2() {
        System.out.println(pushMessage.state(new StateReq()).online);
    }

}
