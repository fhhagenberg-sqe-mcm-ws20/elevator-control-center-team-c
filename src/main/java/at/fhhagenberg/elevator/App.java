package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import at.fhhagenberg.elevator.view.ElevatorControlCenterPane;
import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaFX App
 */
public class App extends Application {

    private Building building=new Building();
    private BuildingViewModel buildingViewModel=new BuildingViewModel(building);
    private ElevatorControlCenterPane view;

    @Override
    public void start(Stage stage) {
        Simulator simulator=new Simulator(building);

        //Only for testing layout
        int numberOfFLoors = 10;
        int numberOfElevators = 5;

        List<Floor> floors = simulator.generateFloorModels(numberOfFLoors);
        List<Elevator> elevators = simulator.generateElevatorModels(numberOfElevators, numberOfFLoors);

        building.copyValues(new Building(elevators, floors, 100));

        view = new ElevatorControlCenterPane(buildingViewModel, stage);
        view.initialize();
        var scene = new Scene(view, 1280, 720);

        stage.setScene(scene);
        stage.show();

        simulator.run();
    }

    public static void main(String[] args) {
        launch();
    }
}