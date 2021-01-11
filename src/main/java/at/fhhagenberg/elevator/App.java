package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
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
import sqelevator.IElevator;

import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private Building building=new Building();
    public RMIElevatorAdapter simulator = new RMIElevatorAdapter("rmi://localhost/ElevatorSim");
    private BuildingViewModel buildingViewModel=new BuildingViewModel(building, simulator);
    private ElevatorControlCenterPane view;

    @Override
    public void start(Stage stage) {

        try {
            Runnable runnable = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    while(true) {
                        Platform.runLater(() -> {
                            if (!simulator.isConnected()) {
                                view.setSystemStatus(SystemStatus.CONNECTING);
                            } else {
                                simulator.updateBuilding(building);
                                view.setSystemStatus(SystemStatus.CONNECTED);
                            }
                        });
                        Thread.sleep(250);
                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.start();

            view = new ElevatorControlCenterPane(buildingViewModel, stage);
            view.initialize();
        } catch (Exception e ){
            e.printStackTrace();
        }

        var scene = new Scene(view, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public void injectMock(IElevator mock){
        this.simulator.controller = mock;
        this.simulator.converter = new InterfaceToModelConverter(mock);
    }

    public static void main(String[] args) {
        launch();
    }
}