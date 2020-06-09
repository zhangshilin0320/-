package com.pet.pet.controller;

import com.pet.pet.pojo.Pet;
import com.pet.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
