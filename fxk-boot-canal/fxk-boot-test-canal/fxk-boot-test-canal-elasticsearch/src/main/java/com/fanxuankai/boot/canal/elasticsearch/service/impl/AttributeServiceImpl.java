package com.fanxuankai.boot.canal.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.canal.elasticsearch.domain.Attribute;
import com.fanxuankai.boot.canal.elasticsearch.mapper.AttributeMapper;
import com.fanxuankai.boot.canal.elasticsearch.service.AttributeService;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class AttributeServiceImpl extends ServiceImpl<AttributeMapper, Attribute> implements AttributeService {

}
