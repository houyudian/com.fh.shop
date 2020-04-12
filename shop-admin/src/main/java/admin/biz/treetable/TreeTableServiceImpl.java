package admin.biz.treetable;

import admin.common.ServerResponse;
import admin.common.SystemConstant;
import admin.mapper.ITreeTableMapper;
import admin.mapper.goods.IGoodsCommonMapper;
import admin.param.TreeTableParam;
import admin.po.TreeTable;
import admin.util.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreeTableServiceImpl implements TreeTableService {
    @Autowired
    private ITreeTableMapper treeTableMapper;
    @Autowired
    private IGoodsCommonMapper goodsCommonMapper;

    @Override
    public ServerResponse findAllTreeTable() {
        String c = RedisUtil.get(SystemConstant.TREE_TABLE_LIST_KEY);
        if (StringUtils.isNotEmpty(c)) {
            List<TreeTable> treeTables = JSONObject.parseArray(c, TreeTable.class);
            return ServerResponse.success(treeTables);
        }
        List<TreeTable> treeTableList = treeTableMapper.selectList(null);
        String s = JSONObject.toJSONString(treeTableList);
        RedisUtil.setEx(SystemConstant.TREE_TABLE_LIST_KEY, s, SystemConstant.TREE_TABLE_EXPIRE);

        return ServerResponse.success(treeTableList);
    }

    @Override
    public ServerResponse deleteTreeTable(List<Long> ids) {
        RedisUtil.delete(SystemConstant.TREE_TABLE_LIST_KEY);
        treeTableMapper.deleteBatchIds(ids);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse addTreeTable(TreeTable treeTable) {
        RedisUtil.delete(SystemConstant.TREE_TABLE_LIST_KEY);
        treeTableMapper.insert(treeTable);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findById(Long id) {
        TreeTable treeTable = treeTableMapper.selectById(id);
        return ServerResponse.success(treeTable);
    }

    @Override
    public ServerResponse updateTreeTable(TreeTableParam treeTableParam) {
        TreeTable treeTable = treeTableParam.getTreeTable();
        treeTableMapper.updateById(treeTable);

        //冗余字段处理
        String treeTable1 = treeTable.getTreeTable();
        String oldTreeTable = treeTable.getOldTreeTable();
        if (!treeTable1.equals(oldTreeTable)) {
            goodsCommonMapper.updateTreeTable(oldTreeTable, treeTable1);
        }
        RedisUtil.delete(SystemConstant.TREE_TABLE_LIST_KEY);
        //如果关联到子类的复选框被选中
        if (treeTableParam.getRelateFlag() == SystemConstant.NEED_UPDATE_CHILDS) {
            String ids = treeTableParam.getIds();
            if (StringUtils.isNotEmpty(ids)) {
                TreeTable tree = new TreeTable();
                tree.setTypeName(treeTable.getTypeName());
                tree.setTypeId(treeTable.getTypeId());
                QueryWrapper<TreeTable> treeTableQueryWrapper = new QueryWrapper<>();

                String[] idArr = ids.split(",");
                List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).collect(Collectors.toList());
                treeTableQueryWrapper.in("id", idList);
                treeTableMapper.update(tree, treeTableQueryWrapper);

            }
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findTreeTable(Long id) {
       /* QueryWrapper<TreeTable> treeTableQueryWrapper = new QueryWrapper<>();
        treeTableQueryWrapper.eq("pid", id);
        List<TreeTable> treeTables = treeTableMapper.selectList(treeTableQueryWrapper);*/

        String treeTableKey = RedisUtil.get(SystemConstant.TREE_TABLE_LIST_KEY);
        List<TreeTable> treeTables = new ArrayList<>();
        if (StringUtils.isEmpty(treeTableKey)) {
            treeTables = treeTableMapper.selectList(null);
            String s = JSONObject.toJSONString(treeTables);
            RedisUtil.setEx(SystemConstant.TREE_TABLE_LIST_KEY, s, SystemConstant.TREE_TABLE_EXPIRE);
        } else {
            treeTables = JSONObject.parseArray(treeTableKey, TreeTable.class);
            RedisUtil.expire(SystemConstant.TREE_TABLE_LIST_KEY, SystemConstant.TREE_TABLE_EXPIRE);
        }
        List<TreeTable> collect = treeTables.stream().filter(x -> x.getPid() == id).collect(Collectors.toList());

        return ServerResponse.success(collect);
    }
}