package pl.put.poznan.transformer.logic;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;

/**
 * Data classes for building structure
 * Used with Decorator Pattern for JSON serialization/deserialization
 */
public class BuildingClasses {

    public interface Visitor {
        void visit(Building building);
        void visit(Level level);
        void visit(Room room);
    }

    public Building building;

    public static class Building {
        public String id;
        public String name;
        public List<Level> levels;

        public void accept(Visitor visitor) {
            visitor.visit(this);
            if (levels != null) {
                for (Level level : levels) {
                    level.accept(visitor);
                }
            }
        }
    }

    public static class Level {
        public String id;
        public String name;
        public List<Room> rooms;

        public void accept(Visitor visitor) {
            visitor.visit(this);
            if (rooms != null) {
                for (Room room : rooms) {
                    room.accept(visitor);
                }
            }
        }
    }

    public static class Room {
        public String id;
        public String name;
        public double area;
        public double cube;
        public double heating;
        public double light;

        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }


    /**
     * Calculates the total volume of a building.
     *
     * @param building The building for which to calculate the volume.
     * @return The total volume of the building.
     */
    public double calculateVolume(Building building) {
        if (building == null || building.levels == null) {
            return 0.0;
        }
        return building.levels.stream().mapToDouble(this::calculateVolume).sum();
    }

    /**
     * Calculates the total volume of a level.
     *
     * @param level The level for which to calculate the volume.
     * @return The total volume of the level.
     */
    public double calculateVolume(Level level) {
        if (level == null || level.rooms == null) {
            return 0.0;
        }
        return level.rooms.stream().mapToDouble(this::calculateVolume).sum();
    }

    /**
     * Calculates the volume of a room.
     *
     * @param room The room for which to calculate the volume.
     * @return The volume of the room.
     */
    public double calculateVolume(Room room) {
        if (room == null) {
            return 0.0;
        }
        return room.cube;
    }

    /**
     * Calculates the average luminosity of a building across all rooms.
     *
     * @param building The building for which to calculate the luminosity.
     * @return The average luminosity of the building.
     */
    public double calculateLuminosity(Building building) {
        if (building == null || building.levels == null) {
            return 0.0;
        }
        return building.levels.stream()
                .filter(level -> level.rooms != null)
                .flatMap(level -> level.rooms.stream())
                .mapToDouble(this::calculateLuminosity)
                .average()
                .orElse(0.0);
    }

    /**
     * Calculates the average luminosity of a level.
     *
     * @param level The level for which to calculate the luminosity.
     * @return The average luminosity of the level.
     */
    public double calculateLuminosity(Level level) {
        if (level == null || level.rooms == null || level.rooms.isEmpty()) {
            return 0.0;
        }
        return level.rooms.stream().mapToDouble(this::calculateLuminosity).average().orElse(0.0);
    }

    /**
     * Calculates the luminosity of a room.
     *
     * @param room The room for which to calculate the luminosity.
     * @return The luminosity of the room.
     */
    public double calculateLuminosity(Room room) {
        if (room == null || room.area == 0) {
            return 0.0;
        }
        return room.light / room.area;
    }

}
