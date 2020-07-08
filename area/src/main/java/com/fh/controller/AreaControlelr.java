package com.fh.controller;

import com.fh.biz.AreaService;
import com.fh.common.ServerResponse;
import com.fh.po.Area;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/area")
public class AreaControlelr {
    @Resource
    private AreaService areaService;
    @GetMapping
    public ServerResponse find(){
        return areaService.findArea();
    }

@PostMapping
    public ServerResponse add(Area area){
        return areaService.add(area);
    }
    @DeleteMapping("delete/{id}")
    public ServerResponse delete(@PathVariable("id") Long id){
        return areaService.delete(id);
    }
    @GetMapping("getId/{id}")
    public ServerResponse getId(@PathVariable Long id){
        return areaService.getId(id);
    }
    @PutMapping
    public ServerResponse update(@RequestBody Area area){
        return areaService.update(area);
    }
    @DeleteMapping("/{ids}")
    public ServerResponse batchDelete(String ids){
        return areaService.batchDelete(ids);
    }

}
