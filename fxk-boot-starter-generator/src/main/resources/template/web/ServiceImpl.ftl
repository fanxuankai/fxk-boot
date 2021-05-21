package ${packageName}.service.impl;

import ${packageName}.dao.${className}Dao;
import ${packageName}.dto.${className}Dto;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.model.${className};
import ${packageName}.service.${className}Service;
import ${packageName}.service.mapstruct.${className}Converter;
import ${packageName}.vo.${className}Vo;
import com.fanxuankai.commons.extra.mybatis.base.BaseServiceImpl;
import com.fanxuankai.commons.util.ExcelDownloadUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ${comment} 服务实现类
 *
 * @author ${author}
 * @date ${date}
 */
@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className}, ${className}Dto, ${className}Vo, ${className}QueryCriteria, ${className}Converter, ${className}Dao> implements ${className}Service {
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
        ExcelDownloadUtils.downloadExcel(list, response);
    }
}