package com.pet.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String password;
    private String username;
    private String sex;
    private String phone;
    private String location;

    public User( String username,String password, String phone,String location) {
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.location = location;
    }
}
