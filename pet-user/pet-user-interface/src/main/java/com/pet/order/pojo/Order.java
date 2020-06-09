package com.pet.order.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_order")
@Data
public class Order implements Serializable {
    @TableId
    private String orderId; //订单ID
    private Integer totalPay; //订单标价
    private Integer actualPay; // 订单实付价格
    private Integer paymentType; // 支付方式 1:在线支付 2:货到付款
    private Integer postFee; // 邮费
    private Date createTime; //订单创建时间
    private String shippingName; //物流名称
    private String shippingCode; // 物流单号
    private Integer userId; // 用户ID
    private String buyerMessage; // 用户留言
    private String buyerNick; //用户昵称
    private Integer buyerRate; //用户是否评价 0：未评价 1：已评价
    private Integer receiverZip; // 收货人邮编
    private String receiverAddress; // 收货人地址
    private String receiverMobile; // 收货人电话
    private String receiver; //收货人姓名
    private Integer invoiceType; //发票类型(0：无发票 1：普通发票，2：电子发票，3：增值税发票)
    private Integer petId; // 商品Id
    private Integer status; // 订单状态
    private Date paymentTime;// 付款时间
    private Date consignTime; // 发货时间
    private Date endTime; //交易结束时间
    private Date closeTime; //交易关闭时间
    private Date commentTime; //评价时间
    private Integer num; //订单数量
}
