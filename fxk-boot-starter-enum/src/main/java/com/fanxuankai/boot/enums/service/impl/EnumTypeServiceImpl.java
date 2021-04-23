package com.fanxuankai.boot.enums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.mapper.EnumTypeMapper;
import com.fanxuankai.boot.enums.service.EnumTypeService;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class EnumTypeServiceImpl extends ServiceImpl<EnumTypeMapper, EnumType>
        implements EnumTypeService {

}
