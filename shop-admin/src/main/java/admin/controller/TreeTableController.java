package admin.controller;

import admin.biz.treetable.TreeTableService;
import admin.common.ServerResponse;
import admin.param.TreeTableParam;
import admin.po.TreeTable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("treeTable")
public class TreeTableController {
    @Resource
    private TreeTableService treeTableService;

    @RequestMapping("toTreeTable")
    public String toTreeTable() {
        return "treetable/treeTable";
    }

    @ResponseBody
    @RequestMapping("fingAllTreeTable")
    public ServerResponse fingAllTreeTable() {

        return treeTableService.findAllTreeTable();
    }

    @ResponseBody
    @RequestMapping("deleteTreeTable")
    public ServerResponse deleteTreeTable(@RequestParam("ids[]") List<Long> ids) {
        return treeTableService.deleteTreeTable(ids);
    }

    @ResponseBody
    @RequestMapping("addTreeTable")
    public ServerResponse addTreeTable(TreeTable treeTable) {
        return treeTableService.addTreeTable(treeTable);
    }

    @ResponseBody
    @RequestMapping("findById")
    public ServerResponse findById(Long id) {
        return treeTableService.findById(id);
    }

    @ResponseBody
    @RequestMapping("updateTreeTable")
    public ServerResponse updateTreeTable(TreeTableParam treeTableParam) {
        return treeTableService.updateTreeTable(treeTableParam);
    }

    @RequestMapping("findTreeTable")
    @ResponseBody
    public ServerResponse findTreeTable(Long id) {
       return treeTableService.findTreeTable(id);
    }

}
