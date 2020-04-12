package admin.param;

import admin.po.Category;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class CategoryParam implements Serializable {

    private Category category;
    private String ids;
    @TableField(exist = false)
    private Integer relateFlag;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getRelateFlag() {
        return relateFlag;
    }

    public void setRelateFlag(Integer relateFlag) {
        this.relateFlag = relateFlag;
    }
}
