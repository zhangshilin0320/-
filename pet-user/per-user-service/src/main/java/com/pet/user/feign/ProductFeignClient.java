package com.pet.user.feign;

import com.pet.pet.pojo.Pet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "pet-service")
public interface ProductFeignClient {
    // 对应调用服务的Controller中的方法
    @GetMapping(value = "/selectAllPets")
    List<Pet> selectAllPets();
//
    @PostMapping("/findByConditions")
    List<Pet> findByConditions(@RequestBody Map<String,Object> map);

//
    @RequestMapping("/findOne")
    Pet findOne(String name);
}
