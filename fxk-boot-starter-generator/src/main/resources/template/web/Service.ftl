package ${packageName}.service;

import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import ${packageName}.dto.${className}Dto;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.vo.${className}Vo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ${comment} 服务接口
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${className}Service {

    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param page    分页参数
     * @return PageResult
     */
    PageResult<${className}Vo> page(${className}QueryCriteria criteria, Page page);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    List<${className}Vo> list(${className}QueryCriteria criteria);

    /**
     * 根据 ID 查询
     *
     * @param ${pkFieldName} ID
     * @return ${className}Vo
     */
    ${className}Vo get(${pkCapitalFieldType} ${pkFieldName});

    /**
     * 创建
     *
     * @param dto 数据
     */
    void create(${className}Dto dto);

    /**
     * 修改
     *
     * @param ${pkFieldName} ID
     * @param dto 数据
     */
    void update(${pkCapitalFieldType} ${pkFieldName}, ${className}Dto dto);

    /**
     * 多选删除
     *
     * @param ids ID 列表
     */
    void deleteAll(${pkCapitalFieldType}[] ids);

    /**
     * 导出数据
     *
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<${className}Vo> all, HttpServletResponse response) throws IOException;
}