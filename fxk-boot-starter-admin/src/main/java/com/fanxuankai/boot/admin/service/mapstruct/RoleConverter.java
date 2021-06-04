package com.fanxuankai.boot.admin.service.mapstruct;

import com.fanxuankai.boot.admin.dto.RoleDTO;
import com.fanxuankai.boot.admin.model.Role;
import com.fanxuankai.boot.admin.vo.RoleVO;
import com.fanxuankai.commons.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 角色 MapStruct 对象转换接口
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleConverter extends Converter<Role, RoleDTO, RoleVO> {
}