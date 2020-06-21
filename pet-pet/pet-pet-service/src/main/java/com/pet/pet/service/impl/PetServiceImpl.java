package com.pet.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public IPage<Pet> findAllPage(Integer pageNum, Integer pageSize) {
        IPage<Pet> page = new Page<>(pageNum,pageSize);
        return this.baseMapper.selectPage(page,null);
    }

    @Override
    public List<Pet> findByConditions(Map<String,Object> map) {
        return this.baseMapper.selectList(queryWrapper(map));
    }

    @Override
    public IPage<Pet> findByConditionPage(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        IPage<Pet> page = new Page<>(pageNum,pageSize);
        return this.baseMapper.selectPage(page,queryWrapper(map));
    }

    @Override
    public Pet findOne(String name) {
        return this.baseMapper.selectOne(new QueryWrapper<Pet>().eq("name",name));
    }

    @Override
    public Pet findOne(Integer id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public boolean savePet(Pet pet) {
        Pet pet1 = findOne(pet.getId());
        if (pet1 == null){
            this.baseMapper.insert(pet);
            return true;
        }
        return false;
    }

    @Override
    public boolean removePet(Integer id) {
        Pet pet = findOne(id);
        if (pet != null){
            this.baseMapper.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void updatePet(Pet pet) {

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
            if(!StringUtils.isEmpty(queryMap.get("location"))){
                queryWrapper.eq("location",queryMap.get("location"));
            }
            if (!StringUtils.isEmpty(queryMap.get("color"))){
                queryWrapper.like("color","color");
            }
        }
        return queryWrapper;
    }
}
