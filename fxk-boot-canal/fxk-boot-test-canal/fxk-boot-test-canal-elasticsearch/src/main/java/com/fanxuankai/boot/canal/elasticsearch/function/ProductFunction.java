package com.fanxuankai.boot.canal.elasticsearch.function;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.canal.elasticsearch.document.ProductInfo;
import com.fanxuankai.boot.canal.elasticsearch.domain.Product;
import com.fanxuankai.boot.canal.elasticsearch.domain.ProductAttribute;
import com.fanxuankai.boot.canal.elasticsearch.service.AttributeService;
import com.fanxuankai.boot.canal.elasticsearch.service.ProductAttributeService;
import com.fanxuankai.canal.elasticsearch.MasterDocumentFunction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class ProductFunction implements MasterDocumentFunction<Product, ProductInfo> {
    @Resource
    private AttributeService attributeService;
    @Resource
    private ProductAttributeService productAttributeService;

    @Override
    public ProductInfo applyForInsert(Product insert) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(insert.getId());
        productInfo.setCode(insert.getCode());
        productInfo.setName(insert.getName());
        productInfo.setAttributeList(Optional.of(
                productAttributeService.list(Wrappers.lambdaQuery(ProductAttribute.class)
                        .eq(ProductAttribute::getProductId, insert.getId()))
                        .stream()
                        .map(ProductAttribute::getAttributeId)
                        .collect(Collectors.toList()))
                .filter(o -> !o.isEmpty())
                .map(ids -> attributeService.listByIds(ids))
                .orElse(Collections.emptyList()));
        return productInfo;
    }

    @Override
    public ProductInfo applyForUpdate(Product before, Product after) {
        return BeanUtil.copyProperties(after, ProductInfo.class);
    }

    @Override
    public String applyForDelete(Product delete) {
        return delete.getId().toString();
    }
}
