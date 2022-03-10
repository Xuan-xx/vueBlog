package com.xuan.vueblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuan.vueblog.entity.User;
import com.xuan.vueblog.mapper.UserMapper;
import com.xuan.vueblog.service.UserService;
import com.xuan.vueblog.util.SaltUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;

/**
* @author xx
* @description 针对表【m_user】的数据库操作Service实现
* @createDate 2022-03-05 11:21:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userDao;

    @Override
    public void register(User user) {

        //首先判断是否有重复用户名或邮箱
        User dbUserName = userDao.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getUsername, user.getUsername()));

        if (!ObjectUtils.isEmpty(dbUserName)){
            throw new RuntimeException("用户名已被使用");
        }

        User dbUserEmail = userDao.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getEmail, user.getEmail()));

        if (!ObjectUtils.isEmpty(dbUserEmail)){
            throw new RuntimeException("邮箱已被使用");
        }

        //生成一个8位的salt
        user.setSalt(SaltUtils.getSalt(8));
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getSalt(), 1024);
        user.setPassword(md5Hash.toHex());
        user.setCreated(Calendar.getInstance().getTime());
        user.setStatus(1);
        userDao.insert(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername,username));
        if (!ObjectUtils.isEmpty(user)) {

            if (user.getPassword().equals(new Md5Hash(password, user.getSalt(), 1024).toHex())) {
                return user;
            } else {
                throw new AuthenticationException("密码错误！");
            }

        } else {
            throw new UnknownAccountException("用户不存在");
        }
    }
}




