package com.xuan.vueblog.controller;

import cn.hutool.core.map.MapUtil;
import com.xuan.vueblog.common.dto.LoginDto;
import com.xuan.vueblog.common.lang.Result;
import com.xuan.vueblog.entity.User;
import com.xuan.vueblog.service.UserService;
import com.xuan.vueblog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @CrossOrigin
    @PostMapping("/register")
    public Result register(@RequestBody User user) {

        userService.register(user);
        return Result.success("register success!");
    }

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        User user = userService.login(loginDto.getUsername(), loginDto.getPassword());
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("password", user.getPassword())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map());
    }

    @CrossOrigin
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {

        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
