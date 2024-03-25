package com.zyh.javaim.comet.common.convention.result;

// 响应构造器
public class Results {
    public static <T> Result<T> success() {
        return new Result<T>().setCode(Result.SUCCESS_CODE);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>().setData(data).setCode(Result.SUCCESS_CODE);
    }
}
