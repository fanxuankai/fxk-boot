package com.fanxuankai.boot.canal.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.canal.elasticsearch.domain.Product;
import com.fanxuankai.boot.canal.elasticsearch.mapper.ProductMapper;
import com.fanxuankai.boot.canal.elasticsearch.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
