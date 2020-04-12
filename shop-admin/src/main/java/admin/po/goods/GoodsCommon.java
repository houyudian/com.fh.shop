package admin.po.goods;

import java.math.BigDecimal;

public class GoodsCommon {

    private Long id;
    private String attrInfo;
    private String name;
    private BigDecimal price;
    private Long stock;
    private Long brandId;
    private String brandName;
    private String mainImage;
    private Long cate1;
    private Long cate2;
    private Long cate3;
    private String cateName;
    private String specInfo;
    private String isHot;
    private String status;

    private String img;

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrInfo() {
        return attrInfo;
    }

    public void setAttrInfo(String attrInfo) {
        this.attrInfo = attrInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getCate1() {
        return cate1;
    }

    public void setCate1(Long cate1) {
        this.cate1 = cate1;
    }

    public Long getCate2() {
        return cate2;
    }

    public void setCate2(Long cate2) {
        this.cate2 = cate2;
    }

    public Long getCate3() {
        return cate3;
    }

    public void setCate3(Long cate3) {
        this.cate3 = cate3;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }
}
