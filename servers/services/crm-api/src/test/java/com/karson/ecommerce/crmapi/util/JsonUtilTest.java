package com.karson.ecommerce.crmapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.karson.ecommerce.common.utils.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonUtilTest {
    @Test
    void stringifyValidObjectReturnsJsonString() {
        // Arrange
        TestObject testObject = new TestObject("value1", 42);

        // Act
        String jsonString = JsonUtil.stringify(testObject);

        // Assert
        assertNotNull(jsonString);
        assertTrue(jsonString.contains("value1"));
        assertTrue(jsonString.contains("42"));
    }

    @Test
    void getObjectFromJsonStringInvalidJsonReturnsNull() {
        // Arrange
        String invalidJson = "invalidJson";

        // Act
        Assertions.assertThrows(JsonProcessingException.class, () -> JsonUtil.getObjectFromJsonString(TestObject.class, invalidJson));
    }
    @Test
    void getObjectFromJsonStringTypeReferenceInvalidJsonReturnsNull() {
        // Arrange
        String invalidJson = "invalidJson";

        // Act
        TestObject testObject = JsonUtil.getObjectFromJsonString(new TypeReference<>() {}, invalidJson);

        // Assert
        assertNull(testObject);
    }

    @Test
    void convertToMapValidObjectReturnsMap() {
        // Arrange
        TestObject testObject = new TestObject("value1", 42);

        // Act
        var map = JsonUtil.convertToMap(testObject);

        // Assert
        assertNotNull(map);
        assertTrue(map.containsKey("stringValue"));
        assertTrue(map.containsKey("intValue"));
        assertEquals("value1", map.get("stringValue"));
        assertEquals(42, map.get("intValue"));
    }

    private static class TestObject {
        private String stringValue;
        private int intValue;

        public TestObject(String stringValue, int intValue) {
            this.stringValue = stringValue;
            this.intValue = intValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public int getIntValue() {
            return intValue;
        }
    }
}
