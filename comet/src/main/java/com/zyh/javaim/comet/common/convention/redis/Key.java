package com.zyh.javaim.comet.common.convention.redis;

public class Key {
    public static String UserChannelKey(Long userId) {
        return String.format("user_channel:%s", userId);
    }

    public static String UserServerKey(Long userId) {
        return String.format("user_server:%s", userId);
    }
}
