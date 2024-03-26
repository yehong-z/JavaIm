package com.zyh.javaim;


public interface CometService {
    boolean push(Message message);

    StateResp state(StateReq req);
}
