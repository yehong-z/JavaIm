package com.zyh.javaim.logic.dto.req;

import lombok.Data;

@Data
public class MessageReq {
    private String OP;

    private Long toUser;
    private String msg;
}
