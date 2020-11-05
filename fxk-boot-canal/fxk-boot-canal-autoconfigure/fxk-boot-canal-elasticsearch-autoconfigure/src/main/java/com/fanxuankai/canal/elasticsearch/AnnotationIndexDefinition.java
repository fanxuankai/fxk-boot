package com.fanxuankai.canal.elasticsearch;

import com.fanxuankai.canal.elasticsearch.annotation.Index;
import com.fanxuankai.spring.util.ApplicationContexts;

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
        return ApplicationContexts.getBean(index.documentFunctionClass());
    }
}
