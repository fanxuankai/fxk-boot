package ${packageName}.service.mapstruct;

import ${packageName}.dto.${className}Dto;
import ${packageName}.model.${className};
import ${packageName}.vo.${className}Vo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * ${comment} MapStruct 对象转换接口
 *
 * @author ${author}
 * @date ${date}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}MapStructMapper {
    /**
     * Dto 转 Entity
     *
     * @param dto /
     * @return /
     */
    ${className} toEntity(${className}Dto dto);

    /**
     * Entity 转 Vo
     *
     * @param entity /
     * @return /
     */
    ${className}Vo toVo(${className} entity);

    /**
     * Dto 集合转 Entity 集合
     *
     * @param dtoList /
     * @return /
     */
    List<${className}> toEntity(List<${className}Dto> dtoList);

    /**
     * Entity 集合转 Vo 集合
     *
     * @param entityList /
     * @return /
     */
    List<${className}Vo> toVo(List<${className}> entityList);
}