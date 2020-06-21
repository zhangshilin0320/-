package com.pet.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pet.order.pojo.Order;
import com.pet.order.service.OrderService;
import com.pet.pet.pojo.Pet;
import com.pet.shopCar.pojo.ShopCar;
import com.pet.shopCar.pojo.ShopCarItem;
import com.pet.user.feign.ProductFeignClient;
import com.pet.user.pojo.User;
import com.pet.user.service.CartService;
import com.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String index(String username,Model model){
        if (username != null){
            User user = userService.findOne(username);
            model.addAttribute("username",username);
            model.addAttribute("user",user);
        }
        List<Pet> petList = productFeignClient.selectAllPets();
        model.addAttribute("pets",petList);
        return "index";
    }

//    登录
    @PostMapping("/login")
    public String login(String username, String password,Model model) throws UnsupportedEncodingException {
        User user = userService.findOne(username);
        if (user != null){
            boolean checkPwd = BCrypt.checkpw(password,user.getPassword());
            if (checkPwd){
                model.addAttribute("user",user);
                return "redirect:/api/user/?username=" + URLEncoder.encode(username,"UTF-8");
            }
            model.addAttribute("msg","密码错误");
            return "login";
        }
        model.addAttribute("msg","用户不存在");
        return "login";
    }

//    退出登录
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/api/user/";
    }


//    注册
    @PostMapping("/registry")
    public String registry(String username,String password,String phone,String location,Model model){
        User user = userService.findOne(username);
        if(user == null){
            user = new User(username,password,phone,location);
//        密码加密
            String salt =  BCrypt.gensalt();
            String password1 = BCrypt.hashpw(user.getPassword(),salt);
            user.setPassword(password1);
            userService.insert(user);
            return "login";
        }
        model.addAttribute("msg","该用户名已存在");
        return "register";
    }

//    跳转到个人信息界面
    @GetMapping("/personHtml")
    public String personHtml(Integer userId,Model model){
        User user = userService.findOne(userId);
        model.addAttribute("user",user);
        return "person";
    }

//    分页查询用户
    @GetMapping("/selectUser")
    public ResponseEntity<Map<String,Object>> selectAllUser(Integer pageNum,Integer pageSize){
        IPage<User> userIPage = userService.findAll(pageNum,pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("user",userIPage);
        return ResponseEntity.ok(map);
    }

//    用户更新信息
    @PostMapping("/updateUser")
    public String updateUser(Integer userId,User user,Model model){
        user.setId(userId);
        model.addAttribute("msg","更新成功");
        userService.UpdateUser(user);
        return "redirect:/api/user/personHtml/?userId="+userId;
    }


//    查询宠物
    @RequestMapping("/findPetsByConditions")
    public String findPetsByConditions(Integer typeId,String body,String length,String color,String name,Model model){

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("body",body);
        map.put("length",length);
        map.put("typeId",typeId);
        map.put("color",color);
        List<Pet> pets =  productFeignClient.findByConditions(map);
        model.addAttribute("pets",pets);
        return "search";
    }

//    跳转到宠物商城页面
    @GetMapping("/searchHtml")
    public String searchHtml(Model model){
        List<Pet> petList = productFeignClient.selectAllPets();
        model.addAttribute("pets",petList);
        return "search";
    }

//    跳转到购物页面
    @RequestMapping("/petHtml")
    public String shopPet(String name,String username,Model model){
        Pet pet = productFeignClient.findOne(name);
        List<Pet> petList = productFeignClient.selectAllPets();
        User user = userService.findOne(username);
        List<String> petImageList = new ArrayList<>();
        petImageList.add(pet.getImage());
        model.addAttribute("pets",petList);
        model.addAttribute("pet",pet);
        model.addAttribute("user",user);
        model.addAttribute("petImageList",petImageList);
        return "item";
    }
//    跳转到订单页面
    @GetMapping("/order")
    public String order(String name,String username,Model model){
        Pet pet = productFeignClient.findOne(name);
        User user = userService.findOne(username);
        model.addAttribute("pet",pet);
        model.addAttribute("username",username);
        model.addAttribute("user",user);
        return "dingdan1";
    }

    //    从购物车跳转到订单页面
    @GetMapping("/CarOrder")
    public String CarOrder(String name,String username,Model model){
        Pet pet = productFeignClient.findOne(name);
        User user = userService.findOne(username);
        cartService.deleteCartItem(user.getId(),pet.getId());
        model.addAttribute("pet",pet);
        model.addAttribute("username",username);
        model.addAttribute("user",user);
        return "dingdan1";
    }

    @GetMapping("/pay")
    public String pay(String orderId,String username) throws UnsupportedEncodingException {
        Order order = orderService.selectById(orderId);
        order.setStatus(2);
        orderService.updateOrder(order);
        return "redirect:/api/user/?username=" + URLEncoder.encode(username,"UTF-8");
    }
}
