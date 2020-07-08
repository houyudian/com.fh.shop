package com.fh.mapper;

import com.fh.po.Area;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AreaMapper {
   @Select("select id,areaName,fid from t_area")
   public List<Area> findArea();
@Delete("delete from t_area where id=#{id} ")
   void delete(Long id);
@Insert("insert into t_area(areaName,fid)values(#{areaName},#{id})")
    void insert(Area area);
@Select("select id,name,fid from t_area where id=#{d}")
    List<Area> getId(Long id);
@Update("update t_area set areaName=#{areaName},fid=#{fid} where id=#{id}")
    void updateArea(Area area);
@Delete("<script>DELETE FROM t_area where id in <foreach item='id' collection='list' open='(' separator=',' close=')'>#{id}</foreach></script>")
    void batchDelete(String idList);
}
