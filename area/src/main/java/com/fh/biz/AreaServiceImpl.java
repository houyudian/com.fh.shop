package com.fh.biz;

import com.fh.common.ServerResponse;
import com.fh.mapper.AreaMapper;
import com.fh.po.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findArea() {
      List<Area> areaList= areaMapper.findArea();
        return ServerResponse.success(areaList);
    }

    @Override
    public ServerResponse add(Area area) {
        areaMapper.insert(area);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse delete(Long id) {
       areaMapper.delete(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse getId(Long id) {
        List<Area> areaId=  areaMapper.getId(id);
        return ServerResponse.success(areaId);
    }

    @Override
    public ServerResponse update(Area area) {
        areaMapper.updateArea(area);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse batchDelete(String ids) {
        if(StringUtils.isEmpty(ids)){
            String[] split = ids.split(",");
            String idList = Arrays.stream(split).map(x -> Long.parseLong(x)).collect(Collectors.toList()).toString();
       areaMapper.batchDelete(idList);
        }

        return ServerResponse.success();
    }
}
