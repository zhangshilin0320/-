package com.pet.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.order.mapper.OrderMapper;
import com.pet.order.pojo.Order;
import com.pet.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


//    创建订单
    @Override
    public void createOrder(Order order) {

        this.baseMapper.insert(order);
    }

//    更新订单
    @Override
    public void updateOrder(Order order) {
        this.baseMapper.updateById(order);
    }


//    分页条件(订单号，创建时间，用户id，状态，商品名称)查询所有订单
    @Override
    public IPage<Order> selectListByPage(Integer pageNum, Integer pageSize, Map<String, Object> map) {
        Page<Order> page = new Page<>(pageNum,pageSize);
        return this.baseMapper.selectPage(page,queryWrapper(map));

    }

//删除订单
    @Override
    public Integer deleteOrder(Order order) {
        return this.baseMapper.deleteById(order);
    }

    @Override
    public List<Order> SelectOrder(Map<String,Object> map) {
        return this.baseMapper.selectList(queryWrapper(map));
    }

    @Override
    public Order selectById(String orderId) {
        return this.baseMapper.selectById(orderId);
    }

    private QueryWrapper<Order> queryWrapper(Map<String, Object> queryMap){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(queryMap != null){
//            根据订单Id查询
            if (!StringUtils.isEmpty(queryMap.get("orderId"))){
                queryWrapper.like("order_id",queryMap.get("orderId"));
            }
//            根据创建时间查询
            if (!StringUtils.isEmpty(queryMap.get("createTime"))){
                queryWrapper.eq("create_time",queryMap.get("createTime"));
            }
//            根据用户Id查询
            if (!StringUtils.isEmpty(queryMap.get("userId"))){
                queryWrapper.eq("user_id",queryMap.get("userId"));
            }
//            根据状态查询
            if (!StringUtils.isEmpty(queryMap.get("status"))){
                queryWrapper.eq("status",queryMap.get("status"));
            }
//            根据商品Id查询
            if (!StringUtils.isEmpty(queryMap.get("petId"))){
                queryWrapper.eq("pet_id",queryMap.get("petId"));
            }
//            根据是否评价查询
            if (!StringUtils.isEmpty(queryMap.get("buyerRate"))){
                queryWrapper.eq("buyer_rate",queryMap.get("buyerRate"));
            }
        }
        return queryWrapper;
    }


}
