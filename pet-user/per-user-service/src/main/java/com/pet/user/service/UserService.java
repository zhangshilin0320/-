package com.pet.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.user.pojo.User;



public interface UserService extends IService<User> {
    User findOne(String username);
    int insert(User user);
    IPage<User> findAll(Integer pageNum,Integer pageSize);
    void UpdateUser(User user);
    User findOne(Integer userId);
}
