package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
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

    private Building building=new Building();
    private RMIElevatorAdapter simulator = new RMIElevatorAdapter();
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
                        Thread.sleep(500);
                        Platform.runLater(() -> {
                            simulator.updateBuilding(building);
                            if (!simulator.isConnected())
                                view.logError("Lost connection");
                            else
                                view.logError("Connected");
                        });
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

    public static void main(String[] args) {
        launch();
    }
}