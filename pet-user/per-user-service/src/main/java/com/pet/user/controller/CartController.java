package com.pet.user.controller;

import com.pet.shopCar.pojo.ShopCar;
import com.pet.shopCar.pojo.ShopCarItem;
import com.pet.user.pojo.User;
import com.pet.user.service.CartService;
import com.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/addCart")
    public ModelAndView addProduct(Integer userId, Integer productId, Integer number) {
        System.out.println(userId + "*****" + productId + "--------"+ number);
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/getCart/?userId=" + userId);
        cartService.addCartItem(userId, productId, number);
        return modelAndView;
    }

    @GetMapping("/getCart")
    public String getCart(Integer userId, Model model) {
//        ModelAndView modelAndView = new ModelAndView("cart");
        ShopCar shopCar = cartService.getShopCar(userId);
        User user = userService.findOne(userId);
        if(shopCar==null){
            model.addAttribute("shopList","您的购物车为空");
        }else{
            Map<Integer, ShopCarItem> shopList = shopCar.getCarItems();
            model.addAttribute("shopList", shopList);
        }
        model.addAttribute("user",user);
        return "cart";
    }

    @GetMapping("/deleteCart")
    public ModelAndView deleteProduct(Integer userId, Integer productId) {
        System.out.println("************");
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/getCart/?userId=" + userId);
        cartService.deleteCartItem(userId, productId);
        return modelAndView;
    }

    @GetMapping("/clear/{userId}")
    public ModelAndView clearCart(@PathVariable Integer userId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/getCart/?userId=" + userId);
        cartService.clearShopCar(userId);
        return modelAndView;
    }

    @GetMapping("/modify")
    public ModelAndView modifyProduct(Integer userId,Integer productId,Integer number) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/user/getCart/?userId=" + userId);
        cartService.modifyNumber(userId, productId, number);
        return modelAndView;
    }
}
