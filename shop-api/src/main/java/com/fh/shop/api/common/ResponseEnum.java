package com.fh.shop.api.common;

public enum ResponseEnum {

    CAR_GOODS_NUM_IS_ERROR(5004, "商品数量错误"),
    CAR_GOODS_IS_NULL(5004, "商品信息为空"),
    CAR_GOODS_IS_DOWN(5005, "商品已下架"),
    CAR_GOODS_IS_(5006, "商品信息为空"),
    SECURE_HEADER_IS_MISS(5000, "头信息丢失"),
    SECURE_REQUEST_TIMEOUT(5001, "请求超时"),
    SECURE_REQUEST_IS_SEND(5002, "发送重复请求"),
    SECURE_DATA_IS_CHANGE(5003, "数据被篡改"),
    HANDLER_IS_MISS(6000, "信息丢失"),
    HANDLER_CONTENT_IS_MISS(6001, "信息不完整"),
    DATA_IS_CHANGE(6002, "数据被篡改"),
    LOGIN_IS_TIMEOUT(6002, "登录超时"),
    MEMBER_INFO_IS_ENTRY(802, "会员信息填写完整"),
    MEMBER_NAME_EXIST(803, "会员名已存在"),
    MEMBER_PHONE_EXIST(804, "手机号已存在"),
    MEMBER_MAIL_EXIST(805, "邮箱已存在"),
    MEMBER_NAME_IS_ENTRY(806, "会员名为空"),
    MEMBER_PHONE_IS_ENTRY(807, "手机号名为空"),
    MEMBER_MAIL_IS_ENTRY(808, "邮箱为空"),
    LOGIN_MEMBER_NULL_IS_EMPTY(9000, "会员名或密码为空"),
    LOGIN_MEMBER_NAME_ERROR(9001, "会员名错误"),
    LOGIN_MEMBER_PWD_ERROR(9002, "密码错误"),

    PARAM_IS_EMPTY(500, "值为空"),
    QUERY_PARAM_IS_EMPTY(400, "日期为空"),
    SKU_INFO_IS_EMPTY(100, "不为空"),
    CAR_IDS_IS_null(300, "carIds为空"),
    SPEAC_IS_NULL(200, "SKU相关值为空");
    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
