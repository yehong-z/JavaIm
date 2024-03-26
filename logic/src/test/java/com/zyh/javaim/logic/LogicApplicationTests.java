package com.zyh.javaim.logic;

import com.zyh.javaim.PushMessage;
import com.zyh.javaim.ServiceDemo;


import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogicApplicationTests {
    @DubboReference
    private ServiceDemo serviceDemo;
    @DubboReference
    private PushMessage pushMessage;
    @Test
    void contextLoads() {
//        ReferenceConfig<ServiceDemo> reference = new ReferenceConfig<>();
//        reference.setInterface(ServiceDemo.class);
//        reference.setUrl("dubbo://127.0.0.1:8081/com.zyh.javaim.ServiceDemo");
//        ServiceDemo serviceDemo1 = reference.get();
//        System.out.println(serviceDemo1.sayHello("111"));
        System.out.println(serviceDemo.sayHello("zyh1234"));
    }

    @Test
    void contextLoads1() {
        System.out.println(pushMessage.pushUser(1L, "111"));
    }

}
