package com.zyh.javaim.comet.service;

import com.zyh.javaim.ServiceDemo;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ServiceDemoImpl implements ServiceDemo {
    @Override
    public String sayHello(String name) {
        return String.format("Hello %s", name);
    }
}
