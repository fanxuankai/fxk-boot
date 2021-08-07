package com.fanxuankai.boot.web.converter;

import com.fanxuankai.boot.web.autoconfigure.WebProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * @author fanxuankai
 */
public class JsonMessageConverter extends MappingJackson2HttpMessageConverter {
    public JsonMessageConverter(WebProperties webProperties) {
        ObjectMapper mapper = getObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (webProperties.isNullToEmpty()) {
            nullValueSerializer(mapper);
        }
        if (webProperties.isLongToString()) {
            longToString(mapper);
        }
    }

    /**
     * long 类型转字符串
     *
     * @param mapper the mapper
     */
    private void longToString(ObjectMapper mapper) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        mapper.registerModule(simpleModule);
    }

    /**
     * 空值处理
     *
     * @param mapper the mapper
     */
    private void nullValueSerializer(ObjectMapper mapper) {
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                String fieldName = gen.getOutputContext().getCurrentName();
                try {
                    Field field = gen.getCurrentValue().getClass().getDeclaredField(fieldName);
                    Class<?> fieldType = field.getType();
                    if (CharSequence.class.isAssignableFrom(fieldType)) {
                        // 字符串型空值 ""
                        gen.writeString("");
                        return;
                    } else if (Collection.class.isAssignableFrom(fieldType)) {
                        // 列表型空值返回 []
                        gen.writeStartArray();
                        gen.writeEndArray();
                        return;
                    } else if (Map.class.isAssignableFrom(fieldType)) {
                        // map型空值返回{}
                        gen.writeStartObject();
                        gen.writeEndObject();
                        return;
                    }
                } catch (Exception ignored) {
                }
                // 默认返回 null
                gen.writeNull();
            }
        });
    }
}
