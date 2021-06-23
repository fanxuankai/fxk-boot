package com.fanxuankai.boot.generator.service.mapstruct;

import com.fanxuankai.boot.generator.dto.UserDTO;
import com.fanxuankai.boot.generator.model.User;
import com.fanxuankai.boot.generator.vo.UserVO;
import com.fanxuankai.commons.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 用户 MapStruct 对象转换接口
 *
 * @author fanxuankai
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter extends Converter<User, UserDTO, UserVO> {
}