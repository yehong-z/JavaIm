package com.zyh.javaim.logic.controller;


import com.zyh.javaim.logic.common.convention.result.Result;
import com.zyh.javaim.logic.common.convention.result.Results;
import com.zyh.javaim.logic.dto.req.FriendAckReq;
import com.zyh.javaim.logic.dto.req.FriendAddReq;
import com.zyh.javaim.logic.dto.resp.ResponseOK;
import com.zyh.javaim.logic.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {
    public final FriendService friendService;
    @PostMapping("/api/friends/add")
    public Result<ResponseOK> push(@RequestBody FriendAddReq requestParam) {
        return Results.success(friendService.add(requestParam));
    }

    @PostMapping("/api/friends/ack")
    public Result<ResponseOK> add(@RequestBody FriendAckReq req) {
        return Results.success(friendService.ack(req));
    }
}
