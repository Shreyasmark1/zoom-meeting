package com.example.zoommeeting.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.*;

public final class JacksonUtil {

    // extract String data in a JSON object
    public static String getText(JsonNode node, String key) {

        if (StringUtil.isEmpty(key) || node == null || node.get(key) == null) {
            return null;
        }

        return node.get(key).asText(null);
    }

    // extract Integer data in a JSON object
    public static int getInt(JsonNode node, String key) {

        if (StringUtil.isEmpty(key) || node == null || node.get(key) == null) {
            return 0;
        }

        return node.get(key).asInt(0);
    }

    // extract Boolean data in a JSON object
    public static boolean getBoolean(JsonNode node, String key) {

        if (StringUtil.isEmpty(key) || node == null || node.get(key) == null) {
            return false;
        }

        return node.get(key).asBoolean(false);
    }

    // extract Double data in a JSON object
    public static double getDouble(JsonNode node, String key) {

        if (StringUtil.isEmpty(key) || node == null || node.get(key) == null) {
            return 0;
        }

        return node.get(key).asDouble(0);
    }

    // extract JSONArray data in a JSON object
    public static List<JsonNode> getArray(JsonNode node, String key) {

        List<JsonNode> jsonNodeList = new ArrayList<>();

        if (StringUtil.isEmpty(key) || node == null || node.get(key) == null || !node.get(key).isArray()) {
            return jsonNodeList;
        }

        for (JsonNode jsonNode : node.get(key)) {
            jsonNodeList.add(jsonNode);
        }

        return jsonNodeList;
    }

    // convert json array string to string array
    public static List<String> jsonStringArrayToStringList(String stringJsonArray) {

        if (StringUtil.isEmpty(stringJsonArray)) return Collections.emptyList();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(stringJsonArray, typeFactory.constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException ex) {
            return Collections.emptyList();
        }
    }

    // convert json array integer to integer array
    public static List<Integer> jsonIntegerArrayToIntegerList(String stringJsonArray) {

        if (StringUtil.isEmpty(stringJsonArray)) return Collections.emptyList();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            return objectMapper.readValue(stringJsonArray, typeFactory.constructCollectionType(List.class, Integer.class));
        } catch (JsonProcessingException ex) {
            return Collections.emptyList();
        }
    }

    Map<String, Object> jsonObject;

    public JacksonUtil(JsonObjectBuilder jsonObjectBuilder) {
        this.jsonObject = jsonObjectBuilder.jsonObject;
    }

    public static class JsonObjectBuilder {

        Map<String, Object> jsonObject = new HashMap<>();

        public static JsonObjectBuilder getInstance() {
            return new JsonObjectBuilder();
        }

        private JsonObjectBuilder() {
        }

        public JsonObjectBuilder addItem(String key, Object value) {
            this.jsonObject.put(key, value);
            return this;
        }

        public JsonObjectBuilder setJsonObject(Map<String, Object> jsonObject) {
            this.jsonObject = jsonObject;
            return this;
        }

        public JacksonUtil build() {
            return new JacksonUtil(this);
        }
    }

    public String getJsonObjectString() {

        String json = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(this.jsonObject);
        } catch (JsonProcessingException ex) {
            System.err.println(ex.getMessage());
        } finally {
            this.jsonObject.clear();
        }

        return json;
    }

    public static String objectToJSONString(Object json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(json);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> List<T> stringToObjectJsonArray(String jsonStr, Class<T> tClass) {

        if (StringUtil.isEmpty(jsonStr)) {
            return Collections.emptyList();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, tClass);
            return objectMapper.readValue(jsonStr, typeReference);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static Object stringToJsonObject(String jsonStr) {
        return stringToJsonObject(jsonStr, Object.class);
    }

    public static <T> T stringToJsonObject(String jsonStr, Class<T> tClass) {

        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(jsonStr, tClass);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static <T> T snakeCaseStringToCamelCaseObject(String jsonStr, Class<T> tClass){
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.readValue(jsonStr, tClass);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    public static <T> String camelCaseObjectToSnakeCaseString(T object){

        if(true) throw new RuntimeException("camelCaseObjectToSnakeCaseString function in JacksonUtil is not tested");

        if(object == null) return null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            return mapper.writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }
}
