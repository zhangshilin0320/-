package com.pet.user.service;

import com.pet.shopCar.pojo.ShopCar;
import com.pet.shopCar.pojo.ShopCarItem;

public interface CartService {
    public ShopCar getShopCar(Integer userId);
    public int addCartItem(Integer userId, Integer productId,Integer number);
    public int deleteCartItem(Integer userId,Integer productId);
    public int clearShopCar(Integer userId);
    public int modifyNumber(Integer userId, Integer productId,Integer number);
}
