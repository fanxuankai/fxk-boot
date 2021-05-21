package ${packageName}.controller;

import ${packageName}.dto.${className}DTO;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.service.${className}Service;
import ${packageName}.vo.${className}VO;
import com.fanxuankai.commons.extra.spring.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${comment} 管理
 *
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequestMapping("/${changeClassName}")
public class ${className}Controller extends BaseController<${className}DTO, ${className}VO, ${className}QueryCriteria, ${className}Service> {
}