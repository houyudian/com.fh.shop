package admin.biz.type;

import admin.common.DataTableResult;
import admin.common.ServerResponse;
import admin.common.SystemConstant;
import admin.mapper.*;
import admin.param.TypeParam;
import admin.po.*;
import admin.util.RedisUtil;
import admin.vo.AttrVo;
import admin.vo.SpecVo;
import admin.vo.TypeRelateVo;
import admin.vo.TypeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private ITypeMapper typeMapper;
    @Autowired
    private IBrandMapper brandMapper;
    @Autowired
    private ISpeacMapper speacMapper;
    @Autowired
    private ITypeSpeacMapper typeSpeacMapper;
    @Autowired
    private ITypeBrandMapper typeBrandMapper;
    @Autowired
    private ITypeAttrMapper typeAttrMapper;
    @Autowired
    private ITypeAttrValueMapper typeAttrValueMapper;
    @Autowired
    private ITypeSpeacValueMapper iTypeSpeacValueMapper;
@Autowired
    private ITreeTableMapper treeTableMapper;
    @Override
    public DataTableResult findType(TypeParam typeParam) {
        Long count = typeMapper.findCount(typeParam);

        List<Type> typeList = typeMapper.findType(typeParam);
        return new DataTableResult(typeParam.getDraw(), count, count, typeList);
    }

    @Override
    public ServerResponse deleteType(Long id) {
        typeMapper.deleteById(id);

        QueryWrapper<TypeSpeac> typeSpeacQueryWrapper = new QueryWrapper<>();
        typeSpeacQueryWrapper.eq("typeId", id);
        typeSpeacMapper.delete(typeSpeacQueryWrapper);

        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", id);
        typeBrandMapper.delete(typeBrandQueryWrapper);

        QueryWrapper<TypeAttr> typeAttrQueryWrapper = new QueryWrapper<>();
        typeAttrQueryWrapper.eq("typeId", id);
        typeAttrMapper.delete(typeAttrQueryWrapper);

        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", id);
        typeAttrValueMapper.delete(attrValueQueryWrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse addType(TypeParam typeParam) {
        String speacIds = typeParam.getSpeacIds();
        String brandIds = typeParam.getBrandIds();
        Type type = new Type();
        type.setTypeName(typeParam.getTypeName());
        typeMapper.insert(type);

        Long id = type.getId();
        if (StringUtils.isNotEmpty(speacIds)) {
            String[] speacArr = speacIds.split(",");
            for (String speacId : speacArr) {
                TypeSpeac typeSpeac = new TypeSpeac();
                typeSpeac.setSpeacId(Long.valueOf(speacId));
                typeSpeac.setTypeId(id);
                typeSpeacMapper.insert(typeSpeac);
            }
        }
        if (StringUtils.isNotEmpty(brandIds)) {
            String[] brandIdArr = brandIds.split(",");

            for (String brandId : brandIdArr) {
                TypeBrand typeBrand = new TypeBrand();
                typeBrand.setTypeId(id);
                typeBrand.setBrandId(Long.valueOf(brandId));
                typeBrandMapper.insert(typeBrand);
            }
        }

        String attrNames = typeParam.getAttrNames();
        String attrValues = typeParam.getAttrValues();
        if (StringUtils.isNotEmpty(attrNames) && StringUtils.isNotEmpty(attrValues)) {

            String[] attrNameArr = attrNames.split(",");
            String[] attrValueArr = attrValues.split(";");

            for (int i = 0; i < attrNameArr.length; i++) {
                String attrValueInfo = attrValueArr[i];

                TypeAttr attr = new TypeAttr();

                attr.setTypeId(id);
                attr.setAttrName(attrNameArr[i]);
                attr.setAttrValue(attrValueInfo);
                typeAttrMapper.insert(attr);

                Long attrId = attr.getId();
                String[] attrValue = attrValueInfo.split(",");
                for (int j = 0; j < attrValue.length; j++) {
                    AttrValue attrV = new AttrValue();
                    attrV.setAttrId(attrId);
                    attrV.setAttrValue(attrValue[j]);
                    attrV.setTypeId(id);
                    typeAttrValueMapper.insert(attrV);
                }
            }

        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse getByTypeId(Long id) {

        TypeVo typeVo = new TypeVo();
        Type type = typeMapper.selectById(id);
        QueryWrapper<TypeSpeac> typeSpeacQueryWrapper = new QueryWrapper<>();
        typeSpeacQueryWrapper.eq("typeId", id);
        List<TypeSpeac> typeSpeacs = typeSpeacMapper.selectList(typeSpeacQueryWrapper);

        List<Long> speacList = new ArrayList<>();
        for (TypeSpeac typeSpeac : typeSpeacs) {
            speacList.add(typeSpeac.getSpeacId());
        }

        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", id);
        List<TypeBrand> typeBrands = typeBrandMapper.selectList(typeBrandQueryWrapper);
        List<Long> brandList = new ArrayList<>();
        for (TypeBrand brand : typeBrands) {
            brandList.add(brand.getBrandId());
        }

        QueryWrapper<TypeAttr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", id);
        List<TypeAttr> typeAttrs = typeAttrMapper.selectList(attrQueryWrapper);
        typeVo.setType(type);
        typeVo.setAttrList(typeAttrs);
        typeVo.setBrandList(brandList);
        typeVo.setSpeacList(speacList);
        return ServerResponse.success(typeVo);
    }

    @Override
    public ServerResponse updateType(TypeParam typeParam) {

        Long typeId = typeParam.getTypeId();
        Type type = new Type();
        type.setId(typeId);
        String typeName = typeParam.getTypeName();
        type.setTypeName(typeName);
        typeMapper.updateById(type);

        TreeTable treeTable = new TreeTable();
        treeTable.setTypeName(typeName);
        QueryWrapper<TreeTable> queryWrapper = new QueryWrapper();
        queryWrapper.eq("typeId",typeId);
        treeTableMapper.update(treeTable,queryWrapper);
        RedisUtil.delete(SystemConstant.TREE_TABLE_LIST_KEY);

        QueryWrapper<TypeSpeac> typeSpeacQueryWrapper = new QueryWrapper<>();
        typeSpeacQueryWrapper.eq("typeId", typeId);
        typeSpeacMapper.delete(typeSpeacQueryWrapper);
        String speacIds = typeParam.getSpeacIds();
        if (StringUtils.isNotEmpty(speacIds)) {
            String[] speacId = speacIds.split(",");
            for (String s : speacId) {
                TypeSpeac typeSpeac = new TypeSpeac();
                typeSpeac.setTypeId(typeId);
                typeSpeac.setSpeacId(Long.valueOf(s));
                typeSpeacMapper.insert(typeSpeac);
            }
        }

        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", typeId);
        typeBrandMapper.delete(typeBrandQueryWrapper);
        String brandIds = typeParam.getBrandIds();
        if (StringUtils.isNotEmpty(brandIds)) {
            String[] brands = brandIds.split(",");
            for (String b : brands) {
                TypeBrand typeBrand = new TypeBrand();
                typeBrand.setTypeId(typeId);
                typeBrand.setBrandId(Long.valueOf(b));
                typeBrandMapper.insert(typeBrand);
            }
        }

        QueryWrapper<TypeAttr> typeAttrQueryWrapper = new QueryWrapper<>();
        typeAttrQueryWrapper.eq("typeId", typeId);
        typeAttrMapper.delete(typeAttrQueryWrapper);

        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", typeId);
        typeAttrValueMapper.delete(attrValueQueryWrapper);

        String attrNames = typeParam.getAttrNames();
        String attrValues = typeParam.getAttrValues();
        if (StringUtils.isNotEmpty(attrNames) && StringUtils.isNotEmpty(attrValues)) {

            String[] attrName = attrNames.split(",");
            String[] attrValue = attrValues.split(";");
            for (int i = 0; i < attrName.length; i++) {
                String attrArr = attrName[i];
                String attrValueArr = attrValue[i];
                TypeAttr typeAttr = new TypeAttr();
                typeAttr.setTypeId(typeId);
                typeAttr.setAttrValue(attrValueArr);
                typeAttr.setAttrName(attrArr);
                typeAttrMapper.insert(typeAttr);
                Long attrId = typeAttr.getId();
                String[] attrValueList = attrValueArr.split(",");

                for (int j = 0; j < attrValue.length; j++) {
                    AttrValue attrV = new AttrValue();
                    attrV.setTypeId(typeId);
                    attrV.setAttrValue(attrValueList[j]);
                    attrV.setAttrId(attrId);
                    typeAttrValueMapper.insert(attrV);
                }
            }
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse findAllType() {
        List<Type> types = typeMapper.selectList(null);
        return ServerResponse.success(types);
    }

    @Override
    public ServerResponse findTypeRelate(Long typeId) {

        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", typeId);
        List<TypeBrand> typeBrands = typeBrandMapper.selectList(typeBrandQueryWrapper);
        List<Long> brandIdList = typeBrands.stream().map(x -> x.getBrandId()).collect(Collectors.toList());
        List<Brand> brandList = brandMapper.selectBatchIds(brandIdList);

        List<AttrVo> attrVoList = new ArrayList<>();
        QueryWrapper<TypeAttr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", typeId);
        List<TypeAttr> typeAttrs = typeAttrMapper.selectList(attrQueryWrapper);
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", typeId);
        List<AttrValue> attrValueList = typeAttrValueMapper.selectList(attrValueQueryWrapper);
        typeAttrs.forEach(x -> {
            AttrVo attrVo = new AttrVo();
            attrVo.setAttr(x);
            List<AttrValue> collect = attrValueList.stream().filter(y -> y.getAttrId() == x.getId()).collect(Collectors.toList());
            attrVo.setAttrValue(collect);
            attrVoList.add(attrVo);
        });

        List<SpecVo> specVoList = new ArrayList<>();

        QueryWrapper<TypeSpeac> typeSpeacQueryWrapper = new QueryWrapper<>();
        typeSpeacQueryWrapper.eq("typeId", typeId);
        List<TypeSpeac> typeSpeacs = typeSpeacMapper.selectList(typeSpeacQueryWrapper);
        List<Long> speacIdList = typeSpeacs.stream().map(x -> x.getSpeacId()).collect(Collectors.toList());
       // List<Speac> speacList = speacMapper.selectBatchIds(speacIdList);
       QueryWrapper<Speac> speacQueryWrapper=new QueryWrapper<>();
       speacQueryWrapper.in("id",speacIdList);
        speacQueryWrapper.orderByAsc("sort");
        List<Speac> speacList = speacMapper.selectList(speacQueryWrapper);

        QueryWrapper<SpeacValue> speacValueQueryWrapper = new QueryWrapper<>();
        speacValueQueryWrapper.in("speacId", speacIdList);
        speacValueQueryWrapper.orderByAsc("sort");
        List<SpeacValue> speacValues = iTypeSpeacValueMapper.selectList(speacValueQueryWrapper);
        speacList.forEach(x -> {
            SpecVo specVo = new SpecVo();
            specVo.setSpeac(x);
            specVo.setSpeacValueList(speacValues.stream().filter(y -> y.getSpeacId() == x.getId()).collect(Collectors.toList()));
            specVoList.add(specVo);
        });

        TypeRelateVo typeRelateVo = new TypeRelateVo();
        typeRelateVo.setBrandList(brandList);
        typeRelateVo.setAttrVoList(attrVoList);
        typeRelateVo.setSpeacVoList(specVoList);
        return ServerResponse.success(typeRelateVo);
    }

}
