package com.pet.pet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pet.pet.pojo.Pet;
import com.pet.pet.service.PetService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/selectAllPets")
    public List<Pet> selectAllPets(){
        List<Pet> pets = petService.findAll();
        return pets;
    }

    @RequestMapping("/findByConditions")
    public List<Pet> findByConditions(@RequestBody Map<String,Object> map){
        List<Pet> pets = petService.findByConditions(map);
        return pets;
    }

    @RequestMapping("/findOne")
    public Pet findOne(@RequestBody String name){
        Pet pet = petService.findOne(name);
        return pet;
    }

    @RequestMapping("/find")
    public Pet findIdOne(@RequestBody Integer id){
        Pet pet = petService.findOne(id);
        return pet;
    }
    @GetMapping("/findAll")
    public ResponseEntity<Map<String,Object>> findAll(){
        List<Pet> list = petService.findAll();
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        return ResponseEntity.ok(map);
    }
    //分页查询所有商品信息
    @GetMapping("/findAllPage")
    public ResponseEntity<Map<String,Object>> findAllPage(Integer pageNum, Integer pageSize){
        IPage<Pet> page = petService.findAllPage(pageNum,pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("Allpage",page.getRecords());
        return ResponseEntity.ok(map);
    }
    //分页条件查询商品信息
    @GetMapping("/findByConditionPage")
    public ResponseEntity<Map<String,Object>> findByConditionsPage(Integer pageNum,Integer pageSize,@RequestBody Map<String,Object> map){
        if(map == null && map.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        IPage<Pet> page = petService.findByConditionPage(pageNum, pageSize, map);
        map.clear();
        map.put("ConditionPage",page.getRecords());
        return ResponseEntity.ok(map);
    }
    //存入商品信息
    @GetMapping("vePet")
    public ResponseEntity<Map<String,Object>> savePet(Pet pet){
        pet.setCreateTime(new Date());
        boolean temp = petService.savePet(pet);
        Map<String,Object> map = new HashMap<>();
        map.put("pet",pet);
        if (temp == true){
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.notFound().build();
    }
    //删除商品信息
    @DeleteMapping("/removePet")
    public ResponseEntity<String> removePet(Integer id){
        boolean temp = petService.removePet(id);
        if (temp == true){
            return ResponseEntity.ok("删除成功");
        }
        return ResponseEntity.notFound().build();
    }
    //修改商品信息
    @PostMapping("/updatePet")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "商品编号",name = "id",required = true,dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "性别",name = "sex",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "体型",name = "body",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "毛色",name = "color",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "毛长",name = "length",dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "价格",name = "price",dataType = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "数量",name = "number",dataType = "Integer",paramType = "query")

    })
    public ResponseEntity<Map<String,Object>> updatePet(Integer id,Integer sex,String body,String color,String length,Integer price,Integer number){
        Pet pet = new Pet();
        pet.setId(id);
        pet.setSex(sex);
        pet.setBody(body);
        pet.setColor(color);
        pet.setLength(length);
        pet.setPrice(price);
        pet.setNumber(number);
        petService.updatePet(pet);
        Map<String,Object> map = new HashMap<>();
        map.put("pet",pet);
        return ResponseEntity.ok(map);
    }
}
