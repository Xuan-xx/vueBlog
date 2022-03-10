package com.xuan.vueblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuan.vueblog.entity.User;

/**
* @author xx
* @description 针对表【m_user】的数据库操作Service
* @createDate 2022-03-05 11:21:55
*/
public interface UserService extends IService<User> {

    void register(User user);

    User login(String username,String password);

}
