package com.pet.shopCar.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ShopCar implements Serializable {
    private Integer userId;
    private Map<Integer,ShopCarItem> carItems;
}
