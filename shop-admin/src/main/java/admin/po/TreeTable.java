package admin.po;

import com.baomidou.mybatisplus.annotation.TableField;

public class TreeTable {

    private Long id;
    private String typeName;
    private Long typeId;

    private Long pid;
    private String treeTable;
@TableField(exist = false)
private String oldTreeTable;

    public String getOldTreeTable() {
        return oldTreeTable;
    }

    public void setOldTreeTable(String oldTreeTable) {
        this.oldTreeTable = oldTreeTable;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTreeTable() {
        return treeTable;
    }

    public void setTreeTable(String treeTable) {
        this.treeTable = treeTable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
