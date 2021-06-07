package ${packageName}.api;

import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.vo.${className}VO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * ${comment} API
 *
 * @author ${author}
 */
@FeignClient("${serviceName}")
public interface ${className}Api {

    /**
     * 查询${comment}所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    @GetMapping("api/${changeClassName}/list")
   	List<${className}VO> list(@RequestBody ${className}QueryCriteria criteria);

    /**
     * 根据 ID 查询${comment}
     *
     * @param ${pkFieldName} ID
     * @return ${className}VO
     */
    @GetMapping("api/${changeClassName}/get/{${pkFieldName}}")
    ${className}VO get(@PathVariable ${pkCapitalFieldType} ${pkFieldName});
}