package com.zyh.javaim;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Getter
@Setter
@Accessors(chain = true)
public class Message implements Serializable {
    public Long msgSeq;
    public int type;
    public Long fromUserId;
    public Long toUserId;
    public String content;
}
