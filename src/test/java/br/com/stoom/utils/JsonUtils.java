package br.com.stoom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {}

    private static ObjectMapper mapper() {
        return objectMapper;
    }

    public static String json (final Object obj)  {
        try {
            return mapper().writeValueAsString(obj);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
