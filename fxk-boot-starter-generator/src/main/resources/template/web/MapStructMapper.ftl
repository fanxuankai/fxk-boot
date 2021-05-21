package ${packageName}.service.mapstruct;

import ${packageName}.dto.${className}DTO;
import ${packageName}.model.${className};
import ${packageName}.vo.${className}VO;
import com.fanxuankai.commons.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * ${comment} MapStruct 对象转换接口
 *
 * @author ${author}
 * @date ${date}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Converter extends Converter<${className}, ${className}DTO, ${className}VO> {
}