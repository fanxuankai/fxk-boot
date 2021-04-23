package ${packageName}.api;

import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.service.${className}Service;
import ${packageName}.vo.${className}Vo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${comment} API 实现类
 *
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequiredArgsConstructor
public class ${className}ApiImpl implements ${className}Api {

	private final ${className}Service ${changeClassName}Service;

    /**
     * 查询${comment}所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    @Override
    public List<${className}Vo> list(${className}QueryCriteria criteria) {
        return ${changeClassName}Service.list(criteria);
    }

    /**
     * 根据 ID 查询${comment}
     *
     * @param ${pkFieldName} ID
     * @return ${className}Vo
     */
    @Override
    public ${className}Vo get(${pkCapitalFieldType} ${pkFieldName}) {
        return ${changeClassName}Service.get(${pkFieldName});
    }
}