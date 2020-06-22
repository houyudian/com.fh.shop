package com.fh.shop.api.car.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.car.mapper.AreaMapper;
import com.fh.shop.api.car.po.Area;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public ServerResponse findAreaList(Long id) {

        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.eq("fid", id);
        List<Area> areas = areaMapper.selectList(areaQueryWrapper);
        return ServerResponse.success(areas);
    }

    @Override
    public ServerResponse findAreaTree() {
        List<Area> areas = areaMapper.selectList(null);
        List<Area> childs = getChilds(0L, areas);
        buildTree(childs, areas);
        return ServerResponse.success(childs);
    }

    private List<Area> getChilds(Long id, List<Area> areas) {
        List<Area> childs = areas.stream().filter(x -> id.longValue() == x.getFid().longValue()).collect(Collectors.toList());
        return childs;

    }

    public void buildTree(List<Area> nodes, List<Area> areas) {
        if (nodes.size() > 0) {
            for (Area node : nodes) {
                List<Area> childs = getChilds(node.getId(), areas);
                node.setChilds(childs);
                buildTree(childs, areas);
            }
        }
    }

}
