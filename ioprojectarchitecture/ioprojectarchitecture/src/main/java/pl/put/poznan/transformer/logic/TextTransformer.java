package pl.put.poznan.transformer.logic;

import java.io.File;

/**
 * TextTransformer - uses decorated Reader to process building data.
 * Demonstrates the use of Decorator pattern for JSON operations.
 */
public class TextTransformer {

    private final String[] transforms;
    private final Reader reader;

    /**
     * Constructor with default Reader (JsonReader with logging)
     * @param transforms Array of transformation names to apply
     */
    public TextTransformer(String[] transforms) {
        this.transforms = transforms;
        // Create decorated reader: Base -> Logging
        this.reader = new LoggingJsonReader(new JsonReader());
    }

    /**
     * Constructor with custom Reader
     * @param transforms Array of transformation names to apply
     * @param reader Custom Reader instance (can be decorated)
     */
    public TextTransformer(String[] transforms, Reader reader) {
        this.transforms = transforms;
        this.reader = reader;
    }

    /**
     * Transform building data from file
     * Uses decorator pattern for reading JSON file
     * @param file JSON file containing building data
     */
    public void transform(File file) {
        try {
            // Use decorated reader to read from file
            BuildingClasses wrapper = reader.readFromFile(file, BuildingClasses.class);

            if (wrapper.building != null) {
                BuildingClasses.Building building = wrapper.building;
                System.out.println("Building: " + building.name + " (id=" + building.id + ")");

                if (building.levels != null) {
                    for (BuildingClasses.Level level : building.levels) {
                        System.out.println("  Level: " + level.name + " (id=" + level.id + ")");
                        if (level.rooms != null) {
                            for (BuildingClasses.Room room : level.rooms) {
                                System.out.println("    Room: " + room.name +
                                        " (id=" + room.id +
                                        ", area=" + room.area +
                                        ", cube=" + room.cube +
                                        ", heating=" + room.heating +
                                        ", light=" + room.light + ")");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Transform building data from JSON string
     * Uses decorator pattern for reading JSON string
     * @param jsonString JSON string containing building data
     */
    public void transformFromString(String jsonString) {
        try {
            // Use decorated reader to read from string
            BuildingClasses wrapper = reader.read(jsonString, BuildingClasses.class);

            if (wrapper.building != null) {
                BuildingClasses.Building building = wrapper.building;
                System.out.println("Building: " + building.name + " (id=" + building.id + ")");

                if (building.levels != null) {
                    for (BuildingClasses.Level level : building.levels) {
                        System.out.println("  Level: " + level.name + " (id=" + level.id + ")");
                        if (level.rooms != null) {
                            for (BuildingClasses.Room room : level.rooms) {
                                System.out.println("    Room: " + room.name +
                                        " (id=" + room.id +
                                        ", area=" + room.area +
                                        ", cube=" + room.cube +
                                        ", heating=" + room.heating +
                                        ", light=" + room.light + ")");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error processing JSON string: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Save building data to file
     * Uses decorator pattern for writing JSON file
     * @param wrapper BuildingClasses object to save
     * @param file Target file
     */
    public void saveToFile(BuildingClasses wrapper, File file) {
        try {
            // Use decorated reader to write to file
            reader.writeToFile(wrapper, file);
            System.out.println("Successfully saved building data to: " + file.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error saving to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Convert building data to JSON string
     * Uses decorator pattern for writing JSON string
     * @param wrapper BuildingClasses object to convert
     * @return JSON string representation
     */
    public String toJsonString(BuildingClasses wrapper) {
        try {
            // Use decorated reader to write to string
            return reader.write(wrapper);

        } catch (Exception e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the reader instance being used
     * @return Current Reader instance
     */
    public Reader getReader() {
        return reader;
    }
}