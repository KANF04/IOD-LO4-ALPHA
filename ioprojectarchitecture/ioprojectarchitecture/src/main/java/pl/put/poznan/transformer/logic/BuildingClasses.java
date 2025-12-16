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



    /**
     * Calculates the total area of a building.
     *
     * @param building The building for which to calculate the area.
     * @return The total area of the building.
     */
    public double calculateArea(Building building) {
        if (building == null || building.levels == null) {
            return 0.0;
        }
        return building.levels.stream().mapToDouble(this::calculateArea).sum();
    }

    /**
     * Calculates the total area of a level.
     *
     * @param level The level for which to calculate the area.
     * @return The total area of the level.
     */
    public double calculateArea(Level level) {
        if (level == null || level.rooms == null) {
            return 0.0;
        }
        return level.rooms.stream().mapToDouble(this::calculateArea).sum();
    }

    /**
     * Calculates the area of a room.
     *
     * @param room The room for which to calculate the area.
     * @return The area of the room.
     */
    public double calculateArea(Room room) {
        if (room == null) {
            return 0.0;
        }
        return room.area;
    }



    /**
     * DTO class for detailed area report
     */
    public static class AreaReport {
        public String buildingId;
        public String buildingName;
        public double totalArea;
        public List<LevelAreaReport> levels;
    }

    public static class LevelAreaReport {
        public String levelId;
        public String levelName;
        public double totalArea;
        public List<RoomAreaReport> rooms;
    }

    public static class RoomAreaReport {
        public String roomId;
        public String roomName;
        public double area;
    }

    /**
     * Generates a detailed area report for a building containing
     * building name, id, total area and all levels with their rooms.
     *
     * @param building The building for which to generate the report.
     * @return AreaReport object containing detailed information about areas.
     */
    public AreaReport generateAreaReport(Building building) {
        if (building == null) {
            return null;
        }

        AreaReport report = new AreaReport();
        report.buildingId = building.id;
        report.buildingName = building. name;
        report.totalArea = calculateArea(building);
        report.levels = new java.util.ArrayList<>();

        if (building.levels != null) {
            for (Level level : building.levels) {
                LevelAreaReport levelReport = new LevelAreaReport();
                levelReport.levelId = level.id;
                levelReport.levelName = level. name;
                levelReport.totalArea = calculateArea(level);
                levelReport.rooms = new java.util.ArrayList<>();

                if (level.rooms != null) {
                    for (Room room : level.rooms) {
                        RoomAreaReport roomReport = new RoomAreaReport();
                        roomReport.roomId = room.id;
                        roomReport.roomName = room.name;
                        roomReport.area = room.area;
                        levelReport.rooms.add(roomReport);
                    }
                }

                report.levels.add(levelReport);
            }
        }

        return report;
    }
}
