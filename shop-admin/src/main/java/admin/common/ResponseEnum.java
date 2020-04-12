package admin.common;

public enum ResponseEnum {
    SKU_INFO_IS_EMPTY(100, "不为空"),
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
