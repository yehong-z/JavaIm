package com.zyh.javaim.comet.common.context;

public class Context {
    private static final ThreadLocal<UserDetail> requestId = new ThreadLocal<>();

    public static UserDetail getRequestId() {
        return requestId.get();
    }

    public static void setRequestId(UserDetail userDetail) {
        requestId.set(userDetail);
    }

    public static void clear() {
        requestId.remove();
    }
}
