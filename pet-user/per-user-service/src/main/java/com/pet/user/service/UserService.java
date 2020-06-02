package com.pet.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.user.pojo.User;


public interface UserService extends IService<User> {
    User findOne(String username);
    int insert(User user);
}
