package com.pet.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.user.mapper.UserMapper;
import com.pet.user.pojo.User;
import com.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findOne(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("name",username));
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
