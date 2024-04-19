package com.example.service.impl;

import org.springframework.aop.target.ThreadLocalTargetSourceStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;

    }
    @Override
    public void register(String username, String password) {
        //加密密码
        String md5Password = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username, md5Password);

    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }
    @Override
    public void updatePassword(String newPassword) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePassword(Md5Util.getMD5String(newPassword),id);
    }
}