package pl.put. poznan.transformer.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.put.poznan.transformer. logic.BuildingClasses;
import java.util.ArrayList;
import java.util.List;

/**
 * Visitor implementation for generating area reports
 * Uses Visitor Pattern to traverse building structure
 */
public class AreaReportVisitor implements BuildingClasses.Visitor {

    // --- DTO classes as inner classes ---
    @JsonInclude(JsonInclude. Include.NON_NULL)
    public static class AreaReport {
        public String buildingId;
        public String buildingName;
        public double totalArea;
        public List<LevelReport> levels;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LevelReport {
        public String levelId;
        public String levelName;
        public double totalArea;
        public List<RoomReport> rooms;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RoomReport {
        public String roomId;
        public String roomName;
        public double area;
    }
    // -----------------------------------------

    private AreaReport report;
    private LevelReport currentLevelReport;
    private final BuildingClasses calculator = new BuildingClasses();

    @Override
    public void visit(BuildingClasses.Building building) {
        report = new AreaReport();
        report.buildingId = building.id;
        report.buildingName = building.name;
        report. totalArea = calculator.calculateArea(building);
        report.levels = new ArrayList<>();
    }

    @Override
    public void visit(BuildingClasses. Level level) {
        currentLevelReport = new LevelReport();
        currentLevelReport.levelId = level.id;
        currentLevelReport.levelName = level.name;
        currentLevelReport.totalArea = calculator. calculateArea(level);
        currentLevelReport.rooms = new ArrayList<>();
        if (report != null) {
            report.levels.add(currentLevelReport);
        }
    }

    @Override
    public void visit(BuildingClasses.Room room) {
        if (currentLevelReport != null) {
            RoomReport roomReport = new RoomReport();
            roomReport.roomId = room.id;
            roomReport.roomName = room. name;
            roomReport.area = room.area;
            currentLevelReport.rooms.add(roomReport);
        }
    }

    public AreaReport getReport() {
        return report;
    }
}