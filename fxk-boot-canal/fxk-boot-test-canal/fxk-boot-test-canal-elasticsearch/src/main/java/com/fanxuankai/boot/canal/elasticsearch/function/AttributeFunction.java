package com.fanxuankai.boot.canal.elasticsearch.function;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.canal.elasticsearch.document.ProductInfo;
import com.fanxuankai.boot.canal.elasticsearch.domain.Attribute;
import com.fanxuankai.boot.canal.elasticsearch.domain.Product;
import com.fanxuankai.boot.canal.elasticsearch.domain.ProductAttribute;
import com.fanxuankai.boot.canal.elasticsearch.service.AttributeService;
import com.fanxuankai.boot.canal.elasticsearch.service.ProductAttributeService;
import com.fanxuankai.boot.canal.elasticsearch.service.ProductService;
import com.fanxuankai.canal.elasticsearch.ManyToManyDocumentFunction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class AttributeFunction implements ManyToManyDocumentFunction<Attribute, ProductInfo> {
    @Resource
    private AttributeService attributeService;
    @Resource
    private ProductAttributeService productAttributeService;
    @Resource
    private ProductService productService;

    @Override
    public List<ProductInfo> applyForUpdate(Attribute before, Attribute after) {
        return applyForDelete(after);
    }

    @Override
    public List<ProductInfo> applyForDelete(Attribute delete) {
        List<Product> products = Optional.of(
                productAttributeService.list(Wrappers.lambdaQuery(ProductAttribute.class)
                        .eq(ProductAttribute::getAttributeId, delete.getId()))
                        .stream()
                        .map(ProductAttribute::getProductId)
                        .collect(Collectors.toList())
        )
                .filter(o -> !o.isEmpty())
                .map(ids -> productService.listByIds(ids))
                .orElse(Collections.emptyList());
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, List<Long>> attributeIdsByProductId =
                productAttributeService.list(Wrappers.lambdaQuery(ProductAttribute.class)
                        .in(ProductAttribute::getProductId, products.stream()
                                .map(Product::getId)
                                .collect(Collectors.toList())))
                        .stream()
                        .collect(Collectors.groupingBy(ProductAttribute::getProductId,
                                Collectors.mapping(ProductAttribute::getAttributeId, Collectors.toList())));
        Map<Long, Attribute> attributeMap =
                Optional.of(attributeIdsByProductId.values().stream().flatMap(Collection::stream).collect(Collectors.toList()))
                        .filter(o -> !o.isEmpty())
                        .map(ids -> attributeService.listByIds(ids)
                                .stream()
                                .collect(Collectors.toMap(Attribute::getId, o -> o)))
                        .orElse(Collections.emptyMap());
        return products.stream()
                .map(product -> {
                    ProductInfo productInfo = new ProductInfo();
                    productInfo.setId(product.getId());
                    productInfo.setAttributeList(Optional.ofNullable(attributeIdsByProductId.get(product.getId()))
                            .map(ids -> ids.stream().map(attributeMap::get)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList()))
                            .orElse(Collections.emptyList()));
                    return productInfo;
                })
                .collect(Collectors.toList());
    }
}
