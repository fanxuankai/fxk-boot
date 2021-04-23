package ${packageName}.service.impl;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import ${packageName}.dao.${className}Dao;
import ${packageName}.dto.${className}Dto;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.model.${className};
import ${packageName}.service.${className}Service;
import ${packageName}.service.mapstruct.${className}MapStructMapper;
import ${packageName}.vo.${className}Vo;
import com.fanxuankai.commons.web.util.ExcelUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * ${comment} 服务实现类
 *
 * @author ${author}
 * @date ${date}
 */
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl implements ${className}Service {

    private final ${className}MapStructMapper ${changeClassName}MapStructMapper;
    private final ${className}Dao ${changeClassName}Dao;

    @Override
    public PageResult<${className}Vo> page(${className}QueryCriteria criteria, Page page) {
        return ${changeClassName}Dao.page(criteria, page).map(${changeClassName}MapStructMapper::toVo);
    }

    @Override
    public List<${className}Vo> list(${className}QueryCriteria criteria) {
        return ${changeClassName}MapStructMapper.toVo(${changeClassName}Dao.list(criteria));
    }

    @Override
    public ${className}Vo get(${pkCapitalFieldType} ${pkFieldName}) {
        return ${changeClassName}MapStructMapper.toVo(${changeClassName}Dao.getOne(${pkFieldName}));
    }

    @Override
    public void create(${className}Dto dto) {
        ${changeClassName}Dao.save(${changeClassName}MapStructMapper.toEntity(dto));
    }

    @Override
    public void update(${pkCapitalFieldType} ${pkFieldName}, ${className}Dto dto) {
        ${className} exists${className} = ${changeClassName}Dao.getOne(${pkFieldName});
        ${className} ${changeClassName} = ${changeClassName}MapStructMapper.toEntity(dto);
        ${changeClassName}.set${pkCapitalColName}(exists${className}.get${pkCapitalColName}());
        ${changeClassName}Dao.updateById(${changeClassName});
    }

    @Override
    public void deleteAll(${pkCapitalFieldType}[] ids) {
        ${changeClassName}Dao.removeByIds(Arrays.asList(ids));
    }

    @Override
    public void download(List<${className}Vo> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (${className}Vo ${changeClassName} : all) {
            Map<String, Object> map = new LinkedHashMap<>();
        <#list columns as column>
            <#if column.primaryKey == false && column.listShow>
                <#if column.remark != ''>
            map.put("${column.remark}", ${changeClassName}.get${column.capitalFieldName}());
                    <#else>
            map.put(" ${column.fieldName}",  ${changeClassName}.get${column.capitalFieldName}());
                </#if>
            </#if>
        </#list>
            list.add(map);
        }
        ExcelUtils.downloadExcel(list, response);
    }
}