package com.fh.shop.api.car.param;

import com.fh.shop.api.common.Page;

import java.io.Serializable;
import java.math.BigDecimal;

public class HouseParam extends Page implements Serializable {
    private String name;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
