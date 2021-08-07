package com.fanxuankai.boot.canal.elasticsearch;

import cn.hutool.extra.spring.SpringUtil;
import com.fanxuankai.canal.elasticsearch.AbstractIndexDefinition;
import com.fanxuankai.canal.elasticsearch.DocumentFunction;
import com.fanxuankai.canal.elasticsearch.annotation.Index;

/**
 * @author fanxuankai
 */
public class AnnotationIndexDefinition extends AbstractIndexDefinition {

    public AnnotationIndexDefinition(Class<?> domainClass, Index index) {
        super(domainClass, index);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DocumentFunction<Object, Object> getDocumentFunction() {
        return SpringUtil.getBean(index.documentFunctionClass());
    }
}
