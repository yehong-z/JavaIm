package com.zyh.javaim;


public interface PushMessage {
    boolean pushUser(Long userId, String msg);
}
