package com.pet.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.user.mapper.UserMapper;
import com.pet.user.pojo.User;
import com.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>  implements UserService {


    @Override
    public User findOne(String username) {
        return this.baseMapper.selectOne(new QueryWrapper<User>().eq("name",username));
    }

    @Override
    public int insert(User user) {
        return this.baseMapper.insert(user);
    }

    @Override
    public IPage<User> findAll(Integer pageNum,Integer pageSize) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        return this.baseMapper.selectPage(page,null);
    }

    @Override
    public void UpdateUser(User user) {
        this.baseMapper.updateById(user);
    }
}
