package com.fh.shop.api.car.po;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private Long id;
    private String areaName;
    private Long fid;

    public Long getId() {
        return id;
    }

    @TableField(exist = false)
    private List<Area> childs = new ArrayList<>();

    public List<Area> getChilds() {
        return childs;
    }

    public void setChilds(List<Area> childs) {
        this.childs = childs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }
}
