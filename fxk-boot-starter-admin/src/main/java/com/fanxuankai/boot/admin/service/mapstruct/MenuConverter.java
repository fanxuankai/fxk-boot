package com.fanxuankai.boot.admin.service.mapstruct;

import com.fanxuankai.boot.admin.dto.MenuDTO;
import com.fanxuankai.boot.admin.model.Menu;
import com.fanxuankai.boot.admin.vo.MenuVO;
import com.fanxuankai.commons.util.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 菜单 MapStruct 对象转换接口
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuConverter extends Converter<Menu, MenuDTO, MenuVO> {
}