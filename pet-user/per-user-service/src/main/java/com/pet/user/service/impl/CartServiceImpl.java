package com.pet.user.service.impl;

import com.pet.shopCar.pojo.ShopCar;
import com.pet.shopCar.pojo.ShopCarItem;
import com.pet.user.service.CartService;
import com.pet.user.service.UserService;
import com.pet.user.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    public static final String cartKeyPrefix = "Cart_";
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取购物车
     *
     * @param userId
     * @return
     */
    @Override
    public ShopCar getShopCar(Integer userId) {
        String cartKey = cartKeyPrefix + userId;
        return (ShopCar) redisUtil.get(cartKey);
    }

    /**
     * 向购物车添加物品
     *
     * @param userId
     * @param productId
     * @param number
     * @return
     */
    @Override
    public int addCartItem(Integer userId, Integer productId, Integer number) {
        String cartKey = cartKeyPrefix + userId;
        boolean hasKey = redisUtil.hasKey(cartKey);
        if (hasKey) {
            ShopCar myShopCar = (ShopCar) redisUtil.get(cartKey);
            Map<Integer, ShopCarItem> carItemMap = myShopCar.getCarItems();
            if (carItemMap.containsKey(productId)) {
                Integer newNum = carItemMap.get(productId).getNumber() + number;
                carItemMap.get(productId).setNumber(newNum);
            } else {
                ShopCarItem cartItem = getShopItem(productId, number);
                carItemMap.put(productId, cartItem);
            }
            redisUtil.set(cartKey, myShopCar, 0);
            return 1;
        } else {
            ShopCar shopCar = new ShopCar();
            shopCar.setUserId(userId);
            ShopCarItem cartItem = getShopItem(productId, number);
            Map<Integer, ShopCarItem> carItemMap = new HashMap<>();
            carItemMap.put(productId, cartItem);
            shopCar.setCarItems(carItemMap);
            redisUtil.set(cartKey, shopCar, 0);
            return 1;
        }
    }

    /**
     * 删除购物车中的物品
     *
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public int deleteCartItem(Integer userId, Integer productId) {
        String cartKey = cartKeyPrefix + userId;
        ShopCar shopCar = (ShopCar) redisUtil.get(cartKey);
        shopCar.getCarItems().remove(productId);
        if (shopCar.getCarItems().isEmpty()) {
            redisUtil.del(cartKey);
        } else {
            redisUtil.set(cartKey, shopCar, 0);
        }
        return 1;
    }

    @Override
    public int clearShopCar(Integer userId) {
        String cartKey = cartKeyPrefix + userId;
        redisUtil.del(cartKey);
        return 1;
    }

    @Override
    public int modifyNumber(Integer userId, Integer productId, Integer number) {
        String cartKey = cartKeyPrefix + userId;
        boolean hasKey = redisUtil.hasKey(cartKey);
        ShopCar myShopCar = (ShopCar) redisUtil.get(cartKey);
        Map<Integer, ShopCarItem> carItemMap = myShopCar.getCarItems();
        if (number <= 0) {
            carItemMap.remove(productId);
        } else {
            carItemMap.get(productId).setNumber(number);
        }
        redisUtil.set(cartKey, myShopCar, 0);
        return 1;
    }


    //获取购物车物品对象
    private ShopCarItem getShopItem(Integer productId, Integer number) {
        //TODO 从数据库中根据货物id查询出相对应的货物信息
        ShopCarItem cartItem = new ShopCarItem();
        cartItem.setPetId(productId);
        cartItem.setPetName("哈巴狗");
        cartItem.setDescription("精品");
        cartItem.setImage("http://dadfadf.cn");
        cartItem.setPrice(new BigDecimal(10000));
        cartItem.setNumber(number);
        return cartItem;
    }
}
