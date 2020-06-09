package com.pet.user.controller;

import com.pet.shopCar.pojo.ShopCar;
import com.pet.shopCar.pojo.ShopCarItem;
import com.pet.user.service.CartService;
import com.pet.user.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/add/{userId}/{productId}/{number}")
    public ModelAndView addProduct(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer number) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/cart/getCart/" + userId);
        cartService.addCartItem(userId, productId, number);
        return modelAndView;
    }

    @GetMapping("/getCart/{userId}")
    public ModelAndView getCart(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("test");
        ShopCar shopCar = cartService.getShopCar(userId);
        if(shopCar==null){
            modelAndView.addObject("shopList","您的购物车为空");
        }else{
            Map<Integer, ShopCarItem> shopList = shopCar.getCarItems();
            modelAndView.addObject("shopList", shopList);
        }
        return modelAndView;
    }

    @GetMapping("/delete/{userId}/{productId}")
    public ModelAndView deleteProduct(@PathVariable Integer userId, @PathVariable Integer productId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/cart/getCart/" + userId);
        cartService.deleteCartItem(userId, productId);
        return modelAndView;
    }

    @GetMapping("/clear/{userId}")
    public ModelAndView clearCart(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/cart/getCart/" + userId);
        cartService.clearShopCar(userId);
        return modelAndView;
    }

    @GetMapping("/modify/{userId}/{productId}/{number}")
    public ModelAndView modifyProduct(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable Integer number) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/cart/getCart/" + userId);
        cartService.modifyNumber(userId, productId, number);
        return modelAndView;
    }
}
