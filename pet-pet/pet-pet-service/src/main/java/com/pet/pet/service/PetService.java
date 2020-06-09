package com.pet.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.pet.pojo.Pet;

import java.util.List;
import java.util.Map;

public interface PetService extends IService<Pet> {

    List<Pet> findAll();
    List<Pet> findByConditions(Map<String,Object> map);
    Pet findOne(String name);
}
