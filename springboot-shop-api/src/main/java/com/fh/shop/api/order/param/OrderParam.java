package com.fh.shop.api.order.param;

import java.io.Serializable;

public class OrderParam implements Serializable {
        private Long AddressId;

        private Integer payType;

        private Long memberId;

    public Long getAddressId() {
        return AddressId;
    }

    public void setAddressId(Long addressId) {
        AddressId = addressId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
