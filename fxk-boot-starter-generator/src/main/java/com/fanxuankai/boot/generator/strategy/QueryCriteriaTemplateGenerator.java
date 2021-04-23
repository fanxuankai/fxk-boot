package com.fanxuankai.boot.generator.strategy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.generator.config.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.constants.Constants;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.ColumnData;
import com.fanxuankai.boot.generator.strategy.model.QueryCriteriaTemplateData;
import com.fanxuankai.boot.generator.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.QUERY_CRITERIA)
public class QueryCriteriaTemplateGenerator extends AbstractTemplateGenerator<QueryCriteriaTemplateData> {

    @Override
    protected List<ColumnInfo> filterColumnInfos(List<ColumnInfo> columnInfos) {
        return columnInfos.stream()
                .filter(o -> StrUtil.isNotBlank(o.getQueryType()))
                .collect(Collectors.toList());
    }

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getApiPath();
    }

    @Override
    protected String getRole() {
        return "dto";
    }

    @Override
    protected String getSuffix() {
        return "QueryCriteria";
    }

    @Override
    protected QueryCriteriaTemplateData getTemplateData(GenConfig genConfig, CodeGeneratorProperties properties) {
        QueryCriteriaTemplateData data = super.getTemplateData(genConfig, properties);
        String comma = ",";
        data.setColumns(data.getColumns().stream()
                .flatMap(columnData -> Arrays.stream(columnData.getQueryType().split(comma))
                        .map(s -> {
                            ColumnData o = new ColumnData();
                            BeanUtil.copyProperties(columnData, o);
                            o.setQueryType(s);
                            o.setCapitalQueryType(StringUtils.toCapitalizeCamelCase(s));
                            return o;
                        })).collect(Collectors.toList()));
        data.setHasDate(data.getColumns().stream()
                .anyMatch(o -> Constants.DATE.equals(o.getFieldType())
                        && !Constants.BE_BOOLEAN_COLUMN_TYPE_QUERY_TYPES.contains(o.getQueryType())));
        data.setHasTimestamp(data.getColumns().stream()
                .anyMatch(o -> Constants.TIMESTAMP.equals(o.getFieldType())
                        && !Constants.BE_BOOLEAN_COLUMN_TYPE_QUERY_TYPES.contains(o.getQueryType())));
        data.setHasLong(data.getColumns().stream()
                .anyMatch(o -> Constants.LONG.equals(o.getFieldType())
                        && !Constants.BE_BOOLEAN_COLUMN_TYPE_QUERY_TYPES.contains(o.getQueryType())));
        for (ColumnData column : data.getColumns()) {
            String fieldType = column.getFieldType();
            if (StrUtil.isNotBlank(column.getQueryType())) {
                if (Constants.TIMESTAMP.equals(fieldType)) {
                    data.setQueryHasTimestamp(true);
                }
                if (Constants.DATE.equals(fieldType)) {
                    data.setQueryHasDate(true);
                }
                if (Constants.BIGDECIMAL.equals(fieldType)) {
                    data.setQueryHasBigDecimal(true);
                }
                if ("BETWEEN".equals(column.getQueryType())
                        || "NOT_BETWEEN".equals(column.getQueryType())
                        || "IN".equals(column.getQueryType())
                        || "NOT_IN".equals(column.getQueryType())) {
                    data.setQueryHasList(true);
                }
            }
        }
        return data;
    }
}
