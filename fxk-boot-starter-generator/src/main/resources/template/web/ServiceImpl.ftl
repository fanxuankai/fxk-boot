package ${packageName}.service.impl;

<#if integrateEasyExcel>
import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.EasyExcel;
</#if>
import ${packageName}.dao.${className}Dao;
import ${packageName}.dto.${className}DTO;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.model.${className};
import ${packageName}.service.${className}Service;
import ${packageName}.service.mapstruct.${className}Converter;
import ${packageName}.vo.${className}VO;
import com.fanxuankai.commons.extra.mybatis.base.BaseServiceImpl;
<#if integrateEasyExcel>
import com.fanxuankai.commons.util.DateUtils;
<#else>
import com.fanxuankai.commons.util.ExcelDownloadUtils;
</#if>
import org.springframework.stereotype.Service;
<#if integrateEasyExcel>
import org.springframework.web.multipart.MultipartFile;
</#if>

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<#if integrateEasyExcel>
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
<#else>
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
</#if>

/**
 * ${comment} 服务实现类
 *
 * @author ${author}
 */
@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className}, ${className}DTO, ${className}VO, ${className}QueryCriteria, ${className}Converter, ${className}Dao>
        implements ${className}Service {
    @Override
    public void download(List<${className}VO> all, HttpServletResponse response) throws IOException {
    <#if integrateEasyExcel>
        String filename = URLEncoder.encode("${comment}", "UTF-8")
                + "-" + DateUtils.toText(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
        EasyExcel.write(response.getOutputStream(), ${className}VO.class)
                .sheet("导出数据")
                .doWrite(all);
    <#else>
        List<Map<String, Object>> list = new ArrayList<>();
        for (${className}VO ${changeClassName} : all) {
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
    </#if>
    }

    @Override
    public void upload(MultipartFile request) throws IOException {
    <#if integrateEasyExcel>
        List<${className}DTO> dtoList = EasyExcel.read(request.getInputStream(), ${className}DTO.class, null)
                .sheet()
                .doReadSync();
        if (dtoList.isEmpty()) {
            return;
        }
        baseDao.saveBatch(converter.toEntity(dtoList));
    </#if>
    }
}