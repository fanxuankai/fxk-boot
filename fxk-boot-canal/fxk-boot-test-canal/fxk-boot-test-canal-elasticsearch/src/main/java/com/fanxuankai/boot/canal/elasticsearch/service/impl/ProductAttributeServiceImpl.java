package com.fanxuankai.boot.canal.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.canal.elasticsearch.domain.ProductAttribute;
import com.fanxuankai.boot.canal.elasticsearch.mapper.ProductAttributeMapper;
import com.fanxuankai.boot.canal.elasticsearch.service.ProductAttributeService;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper,
        ProductAttribute> implements ProductAttributeService {

}
