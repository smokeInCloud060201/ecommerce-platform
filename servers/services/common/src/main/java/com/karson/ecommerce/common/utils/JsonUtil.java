package com.karson.ecommerce.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.karson.ecommerce.common.serializers.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Slf4j
public class JsonUtil {
    private static ObjectMapper mapper;
    private static ObjectWriter writer;

    private JsonUtil() {
        initialize();
    }

    private static void initialize() {
        if (mapper != null) {
            return;
        }
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper = new ObjectMapper()
                .registerModule(module)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);

        DefaultPrettyPrinter pp = new DefaultPrettyPrinter()
                .withoutSpacesInObjectEntries()
                .withArrayIndenter(new DefaultPrettyPrinter.NopIndenter())
                .withObjectIndenter(new DefaultPrettyPrinter.NopIndenter());
        writer = new ObjectMapper().writer().with(pp);
    }

    public static String stringify(Object data) {
        initialize();
        try {
            return writer.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("EXCEPTION WHEN PARSE OBJECT TO STRING {}", e.getMessage());
        }
        return null;
    }

    /**
     * Gets object from json string. Object MUST have constructor with no parameter
     *
     * @param <T>   the type parameter
     * @param clazz class to convert
     * @param json  the json
     * @return the object from json string
     * @throws JsonProcessingException the json processing exception
     */
    public static <T> T getObjectFromJsonString(Class<T> clazz, String json) throws JsonProcessingException {
        initialize();
        return mapper.readValue(json, clazz);
    }

    public static <T> T getObjectFromJsonString(TypeReference<T> clazz, String json) {
        initialize();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("EXCEPTION WHEN PARSE STRING TO OBJECT {}", e.getMessage());
        }
        return null;
    }

    private static <T> T convert(Object obj, TypeReference<T> typeReference) {
        initialize();
        return mapper.convertValue(obj, typeReference);
    }

    public static Map<String, Object> convertToMap(Object obj) {
        return convert(obj, new TypeReference<>() {});
    }

    public static Set<String> convertToSet(Object obj) {
        return convert(obj, new TypeReference<Set<String>>() {});
    }
}
