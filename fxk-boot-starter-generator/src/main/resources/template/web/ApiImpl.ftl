package ${packageName}.api;

import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.service.${className}Service;
import ${packageName}.vo.${className}VO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${comment} API 实现类
 *
 * @author ${author}
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
    public List<${className}VO> list(${className}QueryCriteria criteria) {
        return ${changeClassName}Service.list(criteria);
    }

    /**
     * 根据 ID 查询${comment}
     *
     * @param ${pkFieldName} ID
     * @return ${className}VO
     */
    @Override
    public ${className}VO get(${pkCapitalFieldType} ${pkFieldName}) {
        return ${changeClassName}Service.get(${pkFieldName});
    }
}