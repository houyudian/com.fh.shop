package admin.biz.treetable;

import admin.common.ServerResponse;
import admin.param.TreeTableParam;
import admin.po.TreeTable;

import java.util.List;

public interface TreeTableService  {

    ServerResponse findAllTreeTable();

    ServerResponse deleteTreeTable(List<Long> ids);

    ServerResponse addTreeTable(TreeTable treeTable);

    ServerResponse findById(Long id);

    ServerResponse updateTreeTable(TreeTableParam treeTableParam);

    ServerResponse findTreeTable(Long id);
}
