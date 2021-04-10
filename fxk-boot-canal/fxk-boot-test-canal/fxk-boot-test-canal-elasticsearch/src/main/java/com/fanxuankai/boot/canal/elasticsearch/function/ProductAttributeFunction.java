package com.fanxuankai.boot.canal.elasticsearch.function;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.canal.elasticsearch.document.ProductInfo;
import com.fanxuankai.boot.canal.elasticsearch.domain.ProductAttribute;
import com.fanxuankai.boot.canal.elasticsearch.service.AttributeService;
import com.fanxuankai.boot.canal.elasticsearch.service.ProductAttributeService;
import com.fanxuankai.canal.elasticsearch.ManyToOneDocumentFunction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class ProductAttributeFunction implements ManyToOneDocumentFunction<ProductAttribute, ProductInfo> {
    @Resource
    private AttributeService attributeService;
    @Resource
    private ProductAttributeService productAttributeService;

    @Override
    public ProductInfo applyForInsert(ProductAttribute insert) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(insert.getProductId());
        productInfo.setAttributeList(Optional.of(productAttributeService.list(Wrappers.lambdaQuery(ProductAttribute.class)
                .eq(ProductAttribute::getProductId, insert.getProductId()))
                .stream()
                .map(ProductAttribute::getAttributeId)
                .collect(Collectors.toList()))
                .filter(o -> !o.isEmpty())
                .map(ids -> attributeService.listByIds(ids))
                .orElse(Collections.emptyList()));
        return productInfo;
    }

    @Override
    public List<ProductInfo> applyForUpdate(ProductAttribute before, ProductAttribute after) {
        // productId 发生变化
        if (!Objects.equals(before.getProductId(), after.getProductId())) {
            return Arrays.asList(applyForInsert(before), applyForInsert(after));
        }
        // attributeId 发生变化
        if (!Objects.equals(before.getAttributeId(), after.getAttributeId())) {
            return Collections.singletonList(applyForInsert(after));
        }
        // 关联关系未发生变化
        return Collections.emptyList();
    }

    @Override
    public ProductInfo applyForDelete(ProductAttribute delete) {
        return applyForInsert(delete);
    }
}
