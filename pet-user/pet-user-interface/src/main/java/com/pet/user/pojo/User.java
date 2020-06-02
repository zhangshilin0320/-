package com.pet.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("tb_user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private String userPhone;
    private Date createdTime;

    public User(String userName, String password, String userPhone) {
        this.userName = userName;
        this.password = password;
        this.userPhone = userPhone;
    }
}
