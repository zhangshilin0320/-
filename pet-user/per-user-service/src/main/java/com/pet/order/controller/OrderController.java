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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping
@Api("订单接口")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

//    生成订单
    @GetMapping("/createOrder")
    public String createOrder(Order order,String username, Model model ) throws UnsupportedEncodingException {
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
//            设置用户昵称
        order.setBuyerNick(username);
//        评价
        order.setBuyerRate(0);
//        发票类型
        order.setInvoiceType(1);
        order.setStatus(1);
        System.out.println(order);
        orderService.createOrder(order);
        return "redirect:/api/user/payHtml/?orderId=" +orderId +"&username=" +URLEncoder.encode(username,"UTF-8");
    }
    @GetMapping("/payHtml")
    public String payHtml(String orderId,String username,Model model){
        Order order = orderService.selectById(orderId);
        User user = userService.findOne(username);
        model.addAttribute("order",order);
        model.addAttribute("user",user);
        return "pay";
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
        Map<String,Object> map = new HashMap<>();
        if (orderService.updateOrder(order) == 1){
            map.put("msg","更新成功");
        }
        map.put("order",order);
        map.put("msg","更新失败");
        return ResponseEntity.ok(map);
    }

//    更新订单状态
    @GetMapping("/updateStatus")
    public String updateStatus(String orderId,Integer status,Integer userId){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(status);
        orderService.updateOrder(order);
        return "redirect:/api/user/selectOrders/?userId=" + userId;
    }

//    删除订单
    @GetMapping("/deleteOrder")
    public String deleteOrder(String orderId,Integer userId){
        Order order = new Order();
        order.setOrderId(orderId);
        orderService.deleteOrder(order);
        return "redirect:/api/user/selectOrders/?userId=" + userId;
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
        map.put("order",iPage);
        return ResponseEntity.ok(map);
    }

//    根据用户Id和订单状态查询信息
    @GetMapping("/selectOrders")
    public String selectOrders(Integer userId,Integer status,Integer buyerRate,Model model){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("buyerRate",buyerRate);
        map.put("status",status);
        List<Order> orderList = orderService.SelectOrder(map);
        User user = userService.findOne(userId);
        model.addAttribute("orderList",orderList);
        model.addAttribute("user",user);
        if (status != null && status == 1){
            return "pay-Order";
        }else if (status != null && status == 2){
            return "send-Order";
        }else if (status != null && status == 3){
            return "receive-Order";
        }else if (buyerRate != null && buyerRate == 0){
            return "evaluate-Order";
        }else {
            return "all-Orders";
        }
    }
}
