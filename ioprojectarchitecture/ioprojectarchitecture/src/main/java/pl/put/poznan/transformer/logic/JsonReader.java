package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * Base implementation of Reader interface for JSON operations.
 * This is the concrete component in the Decorator pattern.
 * Provides basic JSON reading, writing, and object creation functionality.
 */
public class JsonReader implements Reader {
    protected ObjectMapper mapper = new ObjectMapper();

    /**
     * Read JSON string and convert to object of specified class
     * @param input JSON string
     * @param clazz Target class type
     * @return Deserialized object
     */
    @Override
    public <T> T read(String input, Class<T> clazz) throws Exception {
        return mapper.readValue(input, clazz);
    }

    /**
     * Read JSON from file and convert to object
     * @param file JSON file
     * @param clazz Target class type
     * @return Deserialized object
     */
    @Override
    public <T> T readFromFile(File file, Class<T> clazz) throws Exception {
        return mapper.readValue(file, clazz);
    }

    /**
     * Write object to JSON string
     * @param obj Object to serialize
     * @return JSON string representation
     */
    @Override
    public String write(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    /**
     * Write object to JSON file
     * @param obj Object to serialize
     * @param file Target file
     */
    @Override
    public void writeToFile(Object obj, File file) throws Exception {
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, obj);
    }
}