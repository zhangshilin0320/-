package com.pet.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pet.pet.pojo.Pet;
import com.pet.user.feign.ProductFeignClient;
import com.pet.user.pojo.User;
import com.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    @GetMapping("/")
    public String index(String name,Model model){
        if (name != null){
            User user = userService.findOne(name);
            model.addAttribute("user",user);
        }
        List<Pet> petList = productFeignClient.selectAllPets();
        model.addAttribute("pets",petList);
//        System.out.println(petList);
        return "index";
    }

//    登录
    @PostMapping("/login")
    public String login(String username, String password,Model model) {

        User user = userService.findOne(username);
        String msg;
        if (user != null){
            boolean checkPwd = BCrypt.checkpw(password,user.getPassword());
            if (checkPwd){
                model.addAttribute("user",user);
//                return "redirect:/api/user/?name=" + URLEncoder.encode(username,"UTF-8");
                return "user";
            }
            msg = "密码错误";
            model.addAttribute("msg",msg);
        }
        msg = "用户不存在";
        model.addAttribute("msg",msg);
        return "login";
    }

//    注册
    @GetMapping("/registry/{name}/{password}")
    public ResponseEntity<String> registry(@PathVariable String name,@PathVariable String password){
//        密码加密
        String salt =  BCrypt.gensalt();
        String password1 = BCrypt.hashpw(password,salt);
        User user = new User(name,password1);
        int integer = userService.insert(user);
        if (integer > 0){
            return ResponseEntity.ok("注册成功");
        }
        return ResponseEntity.notFound().build();
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
    @PostMapping("/updateUser/{userId}")
    public String updateUser(@PathVariable Integer userId,User user,Model model){
        user.setId(userId);
        String salt =  BCrypt.gensalt();
        String password1 = BCrypt.hashpw(user.getPassword(),salt);
        user.setPassword(password1);
        System.out.println(user);
        model.addAttribute("msg","更新成功");
        userService.UpdateUser(user);

        return "login";
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
    public String shopPet(String name,Model model){
        Pet pet = productFeignClient.findOne(name);
//        System.out.println(pet);
        model.addAttribute("pet",pet);
        return "pet";
    }

}
