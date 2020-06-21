package com.pet.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.pet.pojo.Pet;

import java.util.List;
import java.util.Map;

public interface PetService extends IService<Pet> {

    List<Pet> findAll();
    //分页查询所有商品
    IPage<Pet> findAllPage(Integer pageNum, Integer pageSize);
    List<Pet> findByConditions(Map<String,Object> map);
    //分页条件查询
    IPage<Pet> findByConditionPage(Integer pageNum,Integer pageSize,Map<String,Object> map);
    Pet findOne(String name);
    Pet findOne(Integer id);
    //存入商品信息
    boolean savePet(Pet pet);
    //删除商品信息
    boolean removePet(Integer id);
    //修改商品信息
    void updatePet(Pet pet);
}
