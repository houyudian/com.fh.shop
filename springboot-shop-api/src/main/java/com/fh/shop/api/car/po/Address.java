package com.fh.shop.api.car.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Address {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;
    @TableField("area")
    private String area;
    @TableField("phone")
    private String phone;
    @TableField("mail")
    private String mail;
    @TableField("alias")
    private String alias;
    @TableField("memberId")
    private Long memberId;


}
