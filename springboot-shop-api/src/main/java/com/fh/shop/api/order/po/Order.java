package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Order {
    @TableId(type = IdType.INPUT)
    private String id;
    private Long memberId;
    private int payType;
    private Long addressId;
    private String phone;
    private String area;
    private String addressName;
    private Date createTime;

    private Integer status;

    private Date payTime;

    private BigDecimal totalPrice;

    private Long totalCount;

}
