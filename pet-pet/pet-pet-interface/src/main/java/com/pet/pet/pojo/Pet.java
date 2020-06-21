package com.pet.pet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("pet")
@Data
public class Pet implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;// 商品Id
    private String name; // 商品名称
    private Integer typeid;// 商品类型Id
    private String image; //商品图片
    private Integer sex; //商品性别 0：雄性 1：雌性
    private String body; //体型
    private String color; // 颜色
    private String length;// 毛长
    private String location;//产地
    private Integer price; //价格
    private String feeddiff; //饲养难度
    private String petcharacter; //宠物特征
    private Integer weight; // 体重
    private  Integer number; // 数量
    private Date releaseTime; //上架时间
    private Date modificationTime; //更新时间
    private Date createTime; //创建时间
    private String wiki;
}
