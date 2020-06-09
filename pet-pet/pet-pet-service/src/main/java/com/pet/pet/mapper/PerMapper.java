package com.pet.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pet.pet.pojo.Pet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PerMapper extends BaseMapper<Pet> {
}
