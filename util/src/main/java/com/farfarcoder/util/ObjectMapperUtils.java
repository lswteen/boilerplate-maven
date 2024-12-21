package com.farfarcoder.util;

import com.farfarcoder.util.exception.ObjectMapperUtilsException;
import com.farfarcoder.util.jackson.HTMLCharacterEscapes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static com.farfarcoder.domain.meta.Strings.EMPTY;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ObjectMapperUtils {
    private static final ObjectMapper defaultObjectMapper;
    private static final ObjectMapper securityObjectMapper;

    static {
        defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        securityObjectMapper = new ObjectMapper();
        securityObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        securityObjectMapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
    }

    public static ObjectMapper defaultMapper() {
        return defaultObjectMapper;
    }

    public static ObjectMapper xssMapper() {
        return securityObjectMapper;
    }

    public static <T> T readValue(String fromValue, Class<T> toValueType) {
        try {
            return defaultObjectMapper.readValue(fromValue, toValueType);
        } catch (JsonProcessingException e) {
            log.error("readValue failed with JsonProcessingException.");
            throw new ObjectMapperUtilsException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("readValue failed case IllegalArgument. value : {}, type : {}", fromValue, toValueType);
            throw new ObjectMapperUtilsException(e.getMessage());
        }
    }

    public static <T> T readValue(String fromValue, TypeReference<T> typeReference) {
        try {
            return defaultObjectMapper.readValue(fromValue, typeReference);
        } catch (JsonProcessingException e) {
            log.error("readValue failed with JsonProcessingException.");
            throw new ObjectMapperUtilsException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("readValue failed case IllegalArgument. value : {}, type : {}", fromValue, typeReference.getType());
            throw new ObjectMapperUtilsException(e.getMessage());
        }
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        try {
            return defaultObjectMapper.convertValue(fromValue, toValueType);
        } catch (IllegalArgumentException e) {
            log.error("convertValue failed with IllegalArgumentException.");
            throw new ObjectMapperUtilsException(e.getMessage());
        }
    }

    public static String toJson(Object value) {
        try {
            return defaultObjectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("toJson failed with JsonProcessingException.\n {}", e.getMessage());
            return EMPTY;
        }
    }
}