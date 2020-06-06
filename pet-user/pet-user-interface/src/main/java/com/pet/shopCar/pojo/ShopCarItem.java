package com.pet.shopCar.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

//购物车物品
@Data
public class ShopCarItem implements Serializable {
    //宠物编号
    private Integer petId;
    //宠物名
    private String petName;
    //宠物描述
    private String description;
    //数量
    private Integer number;
    //图片
    private String image;
    //价格
    private BigDecimal price;
}
