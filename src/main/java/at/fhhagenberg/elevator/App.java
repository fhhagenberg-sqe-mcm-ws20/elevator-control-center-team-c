package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.view.ElevatorControlCenterPane;
import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import sqelevator.IElevator;

/**
 * JavaFX App
 */
public class App extends Application {

    private Building building = new Building();
    private RMIElevatorAdapter adapter;
    private BuildingViewModel buildingViewModel;
    private ElevatorControlCenterPane view;
    private boolean errorState = false;
    private AutomaticStateController automaticStateController;

    public App() {
        adapter = new RMIElevatorAdapter("rmi://localhost/ElevatorSim");
        buildingViewModel = new BuildingViewModel(building, adapter);
        automaticStateController = new AutomaticStateController(adapter, building);
    }

    public App(IElevator interfaceMock) {
        adapter = new RMIElevatorAdapter(interfaceMock);
        buildingViewModel = new BuildingViewModel(building, adapter);
        automaticStateController = new AutomaticStateController(adapter, building);
    }

    @Override
    public void start(Stage stage) {


        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (!errorState) {
                    if (Boolean.FALSE.equals(adapter.isConnected())) {
                        Platform.runLater(() -> view.setSystemStatus(SystemStatus.CONNECTING));
                    } else {


                        Platform.runLater(() -> {
                            adapter.updateBuilding(building);
                            view.setSystemStatus(SystemStatus.CONNECTED);
                        });
                    }
                    Thread.sleep(25);
                }
            }
        };

        Runnable automaticSystemRunnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (!errorState) {
                    automaticStateController.updateState();
                    Thread.sleep(1000);
                }
            }
        };


        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        Thread thread1 = new Thread(automaticSystemRunnable);
        thread1.setDaemon(true);
        thread1.start();

        view = new

                ElevatorControlCenterPane(buildingViewModel, stage);
        view.initialize();


        var scene = new Scene(view, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}