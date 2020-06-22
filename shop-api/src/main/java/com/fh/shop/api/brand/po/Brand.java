package com.fh.shop.api.brand.po;

public class Brand {

    private Long id;
    private String brandName;
    private String isReconmend;
    private int sort;
    private String filePath;
    private String lodFilePath;
    private String logo;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLodFilePath() {
        return lodFilePath;
    }

    public void setLodFilePath(String lodFilePath) {
        this.lodFilePath = lodFilePath;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIsReconmend() {
        return isReconmend;
    }

    public void setIsReconmend(String isReconmend) {
        this.isReconmend = isReconmend;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
