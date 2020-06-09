package com.pet.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pet.order.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
