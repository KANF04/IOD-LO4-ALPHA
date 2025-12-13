package pl.put.poznan.transformer.logic;

import java.io.File;

/**
 * Component interface in the Decorator pattern.
 * Defines operations for reading and writing JSON data.
 */
public interface Reader {

    /**
     * Read JSON string and deserialize to object
     * @param input JSON string
     * @param clazz Target class
     * @return Deserialized object of type T
     * @throws Exception if parsing fails
     */
    <T> T read(String input, Class<T> clazz) throws Exception;

    /**
     * Read JSON from file and deserialize to object
     * @param file JSON file
     * @param clazz Target class
     * @return Deserialized object of type T
     * @throws Exception if file reading or parsing fails
     */
    <T> T readFromFile(File file, Class<T> clazz) throws Exception;

    /**
     * Serialize object to JSON string
     * @param obj Object to serialize
     * @return JSON string
     * @throws Exception if serialization fails
     */
    String write(Object obj) throws Exception;

    /**
     * Serialize object and save to JSON file
     * @param obj Object to serialize
     * @param file Target file
     * @throws Exception if serialization or file writing fails
     */
    void writeToFile(Object obj, File file) throws Exception;
}