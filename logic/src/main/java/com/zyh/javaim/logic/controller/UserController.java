package com.zyh.javaim.logic.controller;


import com.zyh.javaim.logic.common.convention.result.Result;
import com.zyh.javaim.logic.common.convention.result.Results;
import com.zyh.javaim.logic.dto.req.LoginReq;
import com.zyh.javaim.logic.dto.req.RegisterReq;
import com.zyh.javaim.logic.dto.resp.LoginResp;
import com.zyh.javaim.logic.dto.resp.RegisterResp;
import com.zyh.javaim.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<RegisterResp> register(@RequestBody RegisterReq requestParam) {
        return Results.success(userService.register(requestParam));
    }

    @PostMapping("/login")
    public Result<LoginResp> login(@RequestBody LoginReq requestParam) {
        return Results.success(userService.login(requestParam));
    }

}
