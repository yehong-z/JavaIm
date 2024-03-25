package com.zyh.javaim.comet.netty;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NettyStartListener implements ApplicationRunner {
    private final SocketServer socketServer;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.socketServer.start();
    }
}
