package com.pet.user.controller;

import com.pet.user.pojo.User;
import com.pet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;
//    登录
    @GetMapping("/login/{username}/{password}")
    public ResponseEntity<Map<Object,Object>> login(@PathVariable String username, @PathVariable String password){
        User user = userService.findOne(username);
        if (user != null){
            boolean checkPwd = BCrypt.checkpw(password,user.getPassword());
            Map<Object, Object> map = new HashMap<>();
            if (checkPwd){
                map.put("user",user);
                return ResponseEntity.ok(map);
            }
            map.put("msg","密码错误");
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.notFound().build();
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
}
