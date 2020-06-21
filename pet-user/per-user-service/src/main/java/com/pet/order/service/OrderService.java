package com.pet.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.order.pojo.Order;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {

    //    创建订单
    void createOrder(Order order);

    //    更新订单信息
    void updateOrder(Order order);

//    分页查询所有订单 pageNum:当前页数  pageSize:每页条数
    IPage<Order> selectListByPage(Integer pageNum, Integer pageSize, Map<String,Object> map);

//    根据订单Id删除订单
    Integer deleteOrder(Order order);

//    根据用户Id和订单信息查询订单信息
    List<Order> SelectOrder(Map<String,Object> map);

    Order selectById(String orderId);
}
