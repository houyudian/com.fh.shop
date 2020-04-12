package com.fh.shop.api.car.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.car.mapper.AreaMapper;
import com.fh.shop.api.car.po.Area;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public ServerResponse findAreaList(Long id) {

        QueryWrapper<Area> areaQueryWrapper=new QueryWrapper<>();
        areaQueryWrapper.eq("fid",id);
        List<Area> areas = areaMapper.selectList(areaQueryWrapper);
        return ServerResponse.success(areas);
    }
}
