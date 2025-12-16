package pl.put.poznan.transformer.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.put.poznan.transformer.logic.BuildingClasses;
import java.util.ArrayList;
import java.util.List;

public class LuminosityReportVisitor implements BuildingClasses.Visitor {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LuminosityReport {
        public String buildingId;
        public String buildingName;
        public double averageLuminosity;
        public List<LevelReport> levels;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LevelReport {
        public String levelId;
        public String levelName;
        public double averageLuminosity;
        public List<RoomReport> rooms;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RoomReport {
        public String roomId;
        public String roomName;
        public double luminosity;
    }

    private LuminosityReport report;
    private LevelReport currentLevelReport;
    private final BuildingClasses calculator = new BuildingClasses();

    @Override
    public void visit(BuildingClasses.Building building) {
        report = new LuminosityReport();
        report.buildingId = building.id;
        report.buildingName = building.name;
        report.averageLuminosity = calculator.calculateLuminosity(building);
        report.levels = new ArrayList<>();
    }

    @Override
    public void visit(BuildingClasses.Level level) {
        currentLevelReport = new LevelReport();
        currentLevelReport.levelId = level.id;
        currentLevelReport.levelName = level.name;
        currentLevelReport.averageLuminosity = calculator.calculateLuminosity(level);
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
            roomReport.roomName = room.name;
            roomReport.luminosity = calculator.calculateLuminosity(room);
            currentLevelReport.rooms.add(roomReport);
        }
    }

    public LuminosityReport getReport() {
        return report;
    }
}
