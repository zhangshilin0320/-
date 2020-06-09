package com.pet.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.pet.mapper.PerMapper;
import com.pet.pet.pojo.Pet;
import com.pet.pet.service.PetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PetServiceImpl extends ServiceImpl<PerMapper, Pet> implements PetService {

    @Override
    public List<Pet> findAll() {
        return this.baseMapper.selectList(null);
    }

    @Override
    public List<Pet> findByConditions(Map<String,Object> map) {
        return this.baseMapper.selectList(queryWrapper(map));
    }

    @Override
    public Pet findOne(String name) {
        return this.baseMapper.selectOne(new QueryWrapper<Pet>().eq("name",name));
    }

    //    条件查询的方法
    private QueryWrapper<Pet> queryWrapper(Map<String, Object> queryMap){
        QueryWrapper<Pet> queryWrapper = new QueryWrapper<>();
        if(queryMap != null){
            if (!StringUtils.isEmpty(queryMap.get("typeId"))){
                queryWrapper.like("typeid",queryMap.get("typeId"));
            }
            if (!StringUtils.isEmpty(queryMap.get("name"))){
                queryWrapper.like("name",queryMap.get("name"));
            }
            if (!StringUtils.isEmpty(queryMap.get("body"))){
                queryWrapper.eq("body",queryMap.get("body"));
            }
            if (!StringUtils.isEmpty(queryMap.get("length"))){
                queryWrapper.eq("length",queryMap.get("length"));
            }
            if (!StringUtils.isEmpty(queryMap.get("color"))){
                queryWrapper.like("color","color");
            }
        }
        return queryWrapper;
    }
}
