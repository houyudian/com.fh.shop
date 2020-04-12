package admin.param.goods;

import admin.param.Page;

import java.io.Serializable;

public class GoodsSearchParam extends Page implements Serializable {

private String name;
private Long brandId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
