package com.zyh.javaim.logic.service;


import com.zyh.javaim.logic.common.context.UserDetail;
import com.zyh.javaim.logic.common.jwt.JwtGenerator;
import com.zyh.javaim.logic.dao.entity.User;
import com.zyh.javaim.logic.dao.mapper.UserMapper;
import com.zyh.javaim.logic.dto.req.LoginReq;
import com.zyh.javaim.logic.dto.req.RegisterReq;
import com.zyh.javaim.logic.dto.resp.LoginResp;
import com.zyh.javaim.logic.dto.resp.RegisterResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    public RegisterResp register(RegisterReq req)  {
        User user = new User();
        user.setUserName(req.getUserName());
        user.setPassword(req.getPassword());
        RegisterResp registerResp = new RegisterResp();
        try {
            userMapper.insert(user);
            LoginReq loginReq = new LoginReq();
            loginReq.setUserName(req.getUserName());
            loginReq.setPassword(req.getPassword());
            var token = login(loginReq);

            registerResp.setToken(token.getToken());
        } catch (Exception e) {
            log.error(e.toString());
        }

        return registerResp;

    }

    public LoginResp login(LoginReq req) {
        User user = userMapper.selectByUserName(req.userName);
        LoginResp resp = new LoginResp();
        if (user == null) {
            return resp;
        }
        if (Objects.equals(user.getPassword(), req.password)) {

            String token = JwtGenerator.GenerateJwtToken(new UserDetail().setUser(user));
            resp.setToken(token);
            return resp;
        }

        return resp;
    }
}
