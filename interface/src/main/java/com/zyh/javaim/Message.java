package com.zyh.javaim;

import java.io.Serializable;


public class Message implements Serializable {
    public Long fromUserId;
    public Long toUserId;
    public String content;
}
