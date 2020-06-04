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
    private String name;
    private String sex;
    private String phone;
    private String location;

    public User(String userName, String password) {
        this.name = userName;
        this.password = password;
    }
}
