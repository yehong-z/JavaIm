package com.zyh.javaim.logic.controller;


import com.zyh.javaim.logic.common.convention.result.Result;
import com.zyh.javaim.logic.common.convention.result.Results;
import com.zyh.javaim.logic.dto.req.MessageReq;
import com.zyh.javaim.logic.dto.resp.MessageResp;
import com.zyh.javaim.logic.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    @PostMapping("/api/send")
    public Result<MessageResp> push(@RequestBody MessageReq requestParam) {
        return Results.success(messageService.push(requestParam));
    }
}
