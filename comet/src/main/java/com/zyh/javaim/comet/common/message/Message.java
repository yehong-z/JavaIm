package com.zyh.javaim.comet.common.message;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Message {
    Long toUser;
    int type;
    String msg;
}
