package com.fanxuankai.boot.admin.service.mapstruct;

import com.fanxuankai.boot.admin.dto.UserDTO;
import com.fanxuankai.boot.admin.model.User;
import com.fanxuankai.boot.admin.vo.UserVO;
import com.fanxuankai.commons.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 用户 MapStruct 对象转换接口
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter extends Converter<User, UserDTO, UserVO> {
}