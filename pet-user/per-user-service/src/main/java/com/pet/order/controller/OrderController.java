package com.pet.order.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pet.order.pojo.Order;
import com.pet.order.service.OrderService;
import com.pet.user.pojo.User;
import com.pet.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
@Api("订单接口")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

//    生成订单
    @GetMapping("/createOrder")
    public ResponseEntity<Map<String,Object>> createOrder(Order order,String username ){
//       生成订单Id;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String orderId = dateFormat.format(new Date());
//        设置订单Id
        order.setOrderId(orderId);
//        设置支付方式
        order.setPaymentType(1);
//        设置邮费
        order.setPostFee(0);
//        订单创建时间
        order.setCreateTime(new Date());
//        用户Id
        User user = userService.findOne(username);
        order.setUserId(user.getId());
        order.setBuyerNick(username);
//        评价
        order.setBuyerRate(0);
//        发票类型
        order.setInvoiceType(1);
        order.setStatus(1);
//        System.out.println(order);
        orderService.createOrder(order);
        Map<String,Object> map = new HashMap<>();
        map.put("order",order);
        return ResponseEntity.ok(map);
    }

//    更新订单
    @PutMapping("/updateOrder")
    @ApiOperation(value = "更新订单", notes ="根据订单号更新订单物流名称，物流单号，收货地址，收货人电话" )
    @ApiImplicitParams({
            @ApiImplicitParam(value = "订单号",name = "orderId",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "物流名称",name = "shippingName",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "物流单号",name = "shippingCode",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "收货地址",name = "receiverAddress",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "收货人电话",name = "receiverMobile",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "订单状态",name = "status",dataType = "Integer",paramType = "query")
    })
    public ResponseEntity<Map<String,Object>> updateOrder(String orderId, String shippingName, String shippingCode, String receiverAddress, String receiverMobile,Integer status){
        Order order = new Order();
        order.setOrderId(orderId);
//        设置物流名称
        order.setShippingName(shippingName);
//        设置物流号
        order.setShippingCode(shippingCode);
//        设置接收地址
        order.setReceiverAddress(receiverAddress);
//        设置接收电话
        order.setReceiverMobile(receiverMobile);
//        更新订单状态
        order.setStatus(status);

        orderService.updateOrder(order);

        Map<String,Object> map = new HashMap<>();
        map.put("order",order);
        return ResponseEntity.ok(map);
    }

//    更新订单状态
    @GetMapping("/updateStatus/{orderId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId",value = "订单Id", required = true,paramType = "path",dataType = "String"),
            @ApiImplicitParam(name = "status",value = "订单状态",required = true,paramType = "query",dataType = "String")
    })
    public String updateStatus(@PathVariable String orderId,Integer status){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(status);
        orderService.updateOrder(order);
        return "";
    }

//    删除订单
    @DeleteMapping("/deleteOrder/{orderId}")
    @ApiOperation(value = "删除订单", notes ="根据订单号删除订单" )
    @ApiImplicitParam(name = "orderId",value = "订单Id", required = true,paramType = "path",dataType = "String")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId){
        Order order = new Order();
        order.setOrderId(orderId);
        Integer integer1 = orderService.deleteOrder(order);
        if (integer1 == 1 ){
            return ResponseEntity.ok("删除成功");
        }
        return ResponseEntity.notFound().build();
    }

//    查询订单
    @GetMapping("/selectOrder")
    @ApiOperation(value = "分页查询订单", notes ="根据订单号(orderId)，创建时间(createTime)，用户id(username)，状态(status)，商品名称(petId)条件查询订单" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页数", required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示条数", required = true,paramType = "query",dataType = "Integer"),
            @ApiImplicitParam(name = "map",value = "条件", required = false,paramType = "body",dataType = "Map")
    })
    public ResponseEntity<Map<String,Object>> selectOrder(Integer pageNum,Integer pageSize,@RequestBody Map<String,Object> map){
        if (map.get("username") != null) {
            Object userName = map.get("username");
            User user = userService.findOne(userName.toString());
            map.put("userId", user.getId());
        }
        IPage<Order> iPage = orderService.selectListByPage(pageNum,pageSize,map);
        map.clear();
        map.put("order",iPage.getRecords());
        return ResponseEntity.ok(map);
    }
}
