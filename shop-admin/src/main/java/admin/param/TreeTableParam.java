package admin.param;

import admin.po.TreeTable;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class TreeTableParam implements Serializable {
    private TreeTable treeTable;
    private String ids;
    @TableField(exist = false)
    private Integer relateFlag;

    public TreeTable getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(TreeTable treeTable) {
        this.treeTable = treeTable;
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
