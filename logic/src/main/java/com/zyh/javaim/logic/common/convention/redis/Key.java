package com.zyh.javaim.logic.common.convention.redis;

public class Key {
    public static String UserChannelKey(Long userId) {
        return String.format("user_channel:%s", userId);
    }
}
