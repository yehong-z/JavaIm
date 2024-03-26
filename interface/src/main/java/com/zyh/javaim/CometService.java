package com.zyh.javaim;


public interface CometService {
    boolean pushUser(Long userId, String msg);

    StateResp state(StateReq req);
}
